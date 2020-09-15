package vip.housir.hrpc.client.transport;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import vip.housir.hrpc.core.HrpcRequest;
import vip.housir.hrpc.core.HrpcResponse;

import java.io.*;
import java.net.Socket;

/**
 * @author housirvip
 */
public class SocketTransport implements Transport {

    private static final Logger logger = LoggerFactory.getLogger(SocketTransport.class);

    private final String host;

    private final int port;

    public SocketTransport(String host, int port) {
        this.host = host;
        this.port = port;
    }

    @Override
    public HrpcResponse send(HrpcRequest request) {

        Socket socket = null;
        ObjectOutputStream outputStream = null;
        ObjectInputStream inputStream = null;
        HrpcResponse response = null;
        try {
            socket = new Socket(host, port);
            outputStream = new ObjectOutputStream(socket.getOutputStream());
            outputStream.writeObject(request);
            outputStream.flush();
            inputStream = new ObjectInputStream(socket.getInputStream());
            response = (HrpcResponse) inputStream.readObject();
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
                if (socket != null) {
                    socket.close();
                }
            } catch (IOException e) {
                logger.error(e.getMessage(), e);
            }
        }
        return response;
    }
}
