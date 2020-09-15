package vip.housir.hrpc.demo;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import vip.housir.hrpc.demo.service.UserService;
import vip.housir.hrpc.client.proxy.RequestProxyFactory;
import vip.housir.hrpc.client.transport.SocketTransport;


/**
 * @author housirvip
 */
public class HrpcClient {

    private static final Logger logger = LoggerFactory.getLogger(HrpcClient.class);

    public static void main(String[] args) {
        SocketTransport transport = new SocketTransport("localhost", 7788);
        UserService userService = RequestProxyFactory.createProxy(UserService.class, transport);

        for (int i = 0; i < 1000; i++) {
            String token = userService.login("housir", "vip");
            logger.info(token);
        }
    }
}
