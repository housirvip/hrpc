package vip.housir.demo.server;

import vip.housir.demo.api.UserService;
import vip.housir.demo.server.service.UserServiceImpl;
import vip.housir.hrpc.provider.publisher.PlainNioPublisher;
import vip.housir.hrpc.provider.publisher.Publisher;

/**
 * @author housirvip
 */
public class HrpcServer {

    public static void main(String[] args) {

//        Publisher publisher = new SocketPublisher();
        Publisher publisher = new PlainNioPublisher();
        publisher.register(UserService.class.getName(), new UserServiceImpl());

        publisher.start(7788);
    }
}
