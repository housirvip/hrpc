package vip.housir.hrpc.demo.service.impl;

import vip.housir.hrpc.demo.service.UserService;

/**
 * @author housirvip
 */
public class UserServiceImpl implements UserService {

    @Override
    public String login(String username, String password) {
        return "这是server端返回的结果：" + username + "@" + password;
    }
}
