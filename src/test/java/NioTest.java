import org.junit.jupiter.api.Test;
import vip.housir.demo.api.UserService;
import vip.housir.demo.server.service.UserServiceImpl;
import vip.housir.hrpc.provider.publisher.PlainNioPublisher;
import vip.housir.hrpc.provider.publisher.Publisher;

public class NioTest {

    @Test
    void testServer() {
        Publisher publisher = new PlainNioPublisher();
        publisher.register(UserService.class.getName(), new UserServiceImpl());
        publisher.publish(7788);
    }
}
