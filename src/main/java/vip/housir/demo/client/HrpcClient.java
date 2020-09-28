package vip.housir.demo.client;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import vip.housir.demo.api.UserService;
import vip.housir.hrpc.consumer.proxy.RequestProxyFactory;
import vip.housir.hrpc.consumer.transport.JsonTransport;
import vip.housir.hrpc.consumer.transport.Transport;


/**
 * @author housirvip
 */
public class HrpcClient {

    private static final Logger logger = LoggerFactory.getLogger(HrpcClient.class);

    public static void main(String[] args) {
//        Transport transport = new SocketTransport("localhost", 7788);
        Transport transport = new JsonTransport("localhost", 7788);

        UserService userService = RequestProxyFactory.createProxy(UserService.class, transport);

        for (int i = 1; i <= 1000; i++) {
            String token = userService.login("housir", "vip" + i);
            logger.info(token);
        }
    }
}
