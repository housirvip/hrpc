package vip.housir.demo.server;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import vip.housir.demo.api.UserService;
import vip.housir.hrpc.annotation.HrpcComponentScan;
import vip.housir.hrpc.core.HrpcService;
import vip.housir.hrpc.ioc.HrpcApplicationContext;
import vip.housir.hrpc.ioc.HrpcContext;
import vip.housir.hrpc.provider.publisher.PlainNioPublisher;
import vip.housir.hrpc.provider.publisher.Publisher;

/**
 * @author housirvip
 */
@HrpcComponentScan(path = "vip.housir.demo.server")
public class HrpcServer {

    private static final Logger logger = LoggerFactory.getLogger(HrpcServer.class);

    public static void main(String[] args) {
        HrpcContext hrpcContext = new HrpcApplicationContext(HrpcServer.class);

//        Publisher publisher = new SocketPublisher();
        Publisher publisher = new PlainNioPublisher();
        publisher.register(UserService.class.getName(), (HrpcService) hrpcContext.getBean("userService"));

        publisher.start(7788);
    }
}
