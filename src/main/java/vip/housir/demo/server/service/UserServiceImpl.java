package vip.housir.demo.server.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import vip.housir.demo.api.UserService;
import vip.housir.demo.server.component.UserHelper;
import vip.housir.hrpc.annotation.HrpcAutowire;
import vip.housir.hrpc.annotation.HrpcService;

/**
 * @author housirvip
 */
@HrpcService(name = "userService")
public class UserServiceImpl implements UserService {

    private static final Logger logger = LoggerFactory.getLogger(UserServiceImpl.class);

    @HrpcAutowire
    private UserHelper userHelper;

    @Override
    public String login(String username, String password) {
        userHelper.passwordCheck(password);

        return "这是server端返回的结果：" + username + "@" + password;
    }
}
