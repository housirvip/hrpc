package vip.housir.hrpc.provider.publisher;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import vip.housir.hrpc.core.HrpcService;
import vip.housir.hrpc.provider.processor.ProcessorThread;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.*;

/**
 * @author housirvip
 */
public class SocketPublisher implements Publisher {

    private static final Logger logger = LoggerFactory.getLogger(SocketPublisher.class);

    private final ExecutorService executor;

    private final Map<String, HrpcService> services;

    private boolean shutdown = false;

    public SocketPublisher() {
        services = new HashMap<>(16);

        executor = new ThreadPoolExecutor(5, 10, 60L, TimeUnit.SECONDS,
                new LinkedBlockingQueue<>(), Executors.defaultThreadFactory(), new ThreadPoolExecutor.AbortPolicy());
    }

    @Override
    public void publish(int port) {
        ServerSocket serverSocket = null;
        try {
            serverSocket = new ServerSocket(port);

            while (!shutdown) {
                Socket socket = serverSocket.accept();
                // 将新的连接请求加入线程池
                executor.execute(new ProcessorThread(socket, services));
            }
        } catch (IOException e) {
            logger.error(e.getMessage(), e);
        } finally {
            try {
                if (serverSocket != null) {
                    serverSocket.close();
                }
            } catch (IOException e) {
                logger.error(e.getMessage(), e);
            }
        }
    }

    @Override
    public void shutdown() {
        shutdown = true;
        executor.shutdown();
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
