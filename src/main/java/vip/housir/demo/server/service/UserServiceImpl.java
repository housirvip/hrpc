package vip.housir.demo.server.service;

import vip.housir.demo.api.UserService;

/**
 * @author housirvip
 */
public class UserServiceImpl implements UserService {

    @Override
    public String login(String username, String password) {
        return "这是server端返回的结果：" + username + "@" + password;
    }
}
