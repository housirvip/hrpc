package vip.housir.hrpc.server.publisher;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import vip.housir.hrpc.core.HrpcService;
import vip.housir.hrpc.server.processor.ProcessorThread;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Map;
import java.util.concurrent.*;

/**
 * @author housirvip
 */
public class SocketPublisher implements Publisher {

    private static final Logger logger = LoggerFactory.getLogger(SocketPublisher.class);

    private final ExecutorService executor;

    public SocketPublisher() {
        executor = new ThreadPoolExecutor(5, 10, 60L, TimeUnit.SECONDS, new LinkedBlockingQueue<>());
    }

    @Override
    public void publish(int port, Map<String, HrpcService> services) {
        ServerSocket serverSocket = null;
        try {
            serverSocket = new ServerSocket(port);

            while (true) {
                Socket socket = serverSocket.accept();
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
                e.printStackTrace();
            }
        }
    }

    @Override
    public void shutdown() {
        executor.shutdown();
    }
}
