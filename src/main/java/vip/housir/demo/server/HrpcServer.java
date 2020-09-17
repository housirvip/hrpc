package vip.housir.demo.server;

import vip.housir.demo.api.UserService;
import vip.housir.demo.server.service.UserServiceImpl;
import vip.housir.hrpc.server.publisher.Publisher;
import vip.housir.hrpc.server.publisher.SocketPublisher;

/**
 * @author housirvip
 */
public class HrpcServer {

    public static void main(String[] args) {

        Publisher publisher = new SocketPublisher();
        publisher.register(UserService.class.getName(), new UserServiceImpl());

        publisher.publish(7788);
    }
}
