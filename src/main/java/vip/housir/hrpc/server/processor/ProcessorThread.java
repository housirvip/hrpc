package vip.housir.hrpc.server.processor;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import vip.housir.hrpc.core.HrpcRequest;
import vip.housir.hrpc.core.HrpcResponse;
import vip.housir.hrpc.core.HrpcService;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.lang.reflect.Method;
import java.net.Socket;
import java.util.Map;

/**
 * @author housirvip
 */
public class ProcessorThread implements Runnable {

    private static final Logger logger = LoggerFactory.getLogger(ProcessorThread.class);

    private final Socket socket;

    private final Map<String, HrpcService> services;

    public ProcessorThread(Socket socket, Map<String, HrpcService> services) {
        this.socket = socket;
        this.services = services;
    }

    @Override
    public void run() {

        ObjectInputStream inputStream = null;
        ObjectOutputStream outputStream = null;
        try {
            inputStream = new ObjectInputStream(socket.getInputStream());
            outputStream = new ObjectOutputStream(socket.getOutputStream());

            HrpcRequest request = (HrpcRequest) inputStream.readObject();
            logger.debug(request.toString());

            Class<?> clazz = Class.forName(request.getClassName());
            Method method = clazz.getMethod(request.getMethodName(), request.getTypes());
            HrpcService service = services.get(request.getClassName());
            Object body = method.invoke(service, request.getArgs());

            HrpcResponse response = new HrpcResponse();
            response.setBody(body);
            response.setStatusCode(200);
            logger.debug(response.toString());

            outputStream.writeObject(response);
            outputStream.flush();

        } catch (Exception e) {
            logger.error(e.getMessage(), e);
        } finally {
            try {
                if (inputStream != null) {
                    inputStream.close();
                }
                if (outputStream != null) {
                    outputStream.close();
                }
                socket.close();
            } catch (IOException e) {
                logger.error(e.getMessage(), e);
            }
        }
    }
}
