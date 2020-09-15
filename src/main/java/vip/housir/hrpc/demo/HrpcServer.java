package vip.housir.hrpc.demo;

import vip.housir.hrpc.demo.service.UserService;
import vip.housir.hrpc.core.HrpcService;
import vip.housir.hrpc.server.publisher.SocketPublisher;
import vip.housir.hrpc.demo.service.impl.UserServiceImpl;

import java.util.HashMap;
import java.util.Map;

/**
 * @author housirvip
 */
public class HrpcServer {

    public static void main(String[] args) {

        Map<String, HrpcService> services = new HashMap<>(8);
        services.put(UserService.class.getName(), new UserServiceImpl());

        SocketPublisher processor = new SocketPublisher();
        processor.publish(7788, services);
    }
}
