package vip.housir.demo.api;

import vip.housir.hrpc.core.HrpcService;

/**
 * @author housirvip
 */
public interface UserService extends HrpcService {

    /**
     * 实现用户登录功能
     *
     * @param username 用户名
     * @param password 密码
     * @return 用户令牌
     */
    String login(String username, String password);
}
