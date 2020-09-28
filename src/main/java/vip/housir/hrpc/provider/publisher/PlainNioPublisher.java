package vip.housir.hrpc.provider.publisher;

import com.alibaba.fastjson.JSON;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import vip.housir.hrpc.core.HrpcRequest;
import vip.housir.hrpc.core.HrpcResponse;
import vip.housir.hrpc.core.HrpcService;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

/**
 * @author housirvip
 */
public class PlainNioPublisher implements Publisher {

    private static final Logger logger = LoggerFactory.getLogger(SocketPublisher.class);

    private static final int BUFFER_SIZE = 4096;
    private static final int SEL_TIMEOUT = 100;

    private boolean shutdown = false;

    private final Map<String, HrpcService> services;

    private Selector selector;

    public PlainNioPublisher() {
        this.services = new HashMap<>(16);
    }

    private void acceptHandler(SelectionKey key) throws IOException {
        ServerSocketChannel channel = (ServerSocketChannel) key.channel();
        SocketChannel client = channel.accept();
        client.configureBlocking(false);

        ByteBuffer buffer = ByteBuffer.allocate(BUFFER_SIZE);
        client.register(selector, SelectionKey.OP_READ, buffer);

        logger.debug("--------------------------------------");
        logger.debug("new client: " + client.getRemoteAddress());
        logger.debug("--------------------------------------");
    }

    private void readHandler(SelectionKey key) throws IOException, ClassNotFoundException, NoSuchMethodException, InvocationTargetException, IllegalAccessException {
        SocketChannel channel = (SocketChannel) key.channel();
        ByteBuffer buffer = (ByteBuffer) key.attachment();

        // 开始读取
        int read = 0;
        while ((read = channel.read(buffer)) > 0) {
            buffer.flip();
            byte[] data = new byte[buffer.limit()];
            buffer.get(data);
            buffer.clear();

            HrpcRequest request = JSON.parseObject(data, HrpcRequest.class);
            logger.debug(request.toString());

            // 反射调用
            Class<?> clazz = Class.forName(request.getClassName());
            Method method = clazz.getMethod(request.getMethodName(), request.getTypes());

            // 通过名称查找已注册的服务方法
            HrpcService service = services.get(request.getClassName());
            Object body = method.invoke(service, request.getArgs());

            HrpcResponse response = new HrpcResponse();
            response.setBody(body);
            response.setStatusCode(200);
            logger.debug(response.toString());

            buffer.put(JSON.toJSONBytes(response));
            buffer.flip();
            while (buffer.hasRemaining()) {
                channel.write(buffer);
            }
            buffer.clear();
        }
        if (read == -1) {
            logger.debug("client disconnected: " + channel.getRemoteAddress());
            channel.close();
        }
    }

    @Override
    public void publish(int port) {

        try {
            // 打开channel开始监听
            ServerSocketChannel channel = ServerSocketChannel.open();
            channel.bind(new InetSocketAddress("localhost", port));
            // 设置非阻塞
            channel.configureBlocking(false);

            selector = Selector.open();
            channel.register(selector, SelectionKey.OP_ACCEPT);

            while (!shutdown) {
                while (selector.select(SEL_TIMEOUT) > 0) {
                    Set<SelectionKey> keys = selector.selectedKeys();
                    Iterator<SelectionKey> iterator = keys.iterator();
                    while (iterator.hasNext()) {
                        SelectionKey key = iterator.next();
                        iterator.remove();

                        if (key.isAcceptable()) {
                            acceptHandler(key);
                        } else if (key.isReadable()) {
                            readHandler(key);
                        }
                    }
                }
            }
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
        }
    }

    @Override
    public void shutdown() {
        shutdown = true;
        try {
            selector.close();
        } catch (IOException e) {
            logger.error(e.getMessage(), e);
        }
    }

    @Override
    public void register(String serviceName, HrpcService service) {
        services.put(serviceName, service);
    }

    @Override
    public void start(int port) {
        logger.info("Server is listening... port: " + port);
        // shutdown hook，接到关闭信号或者程序执行完成时
        Runtime.getRuntime().addShutdownHook(new Thread(() -> {
            logger.info("Server is shutting down...");
            shutdown();
        }));
        publish(port);
    }
}
