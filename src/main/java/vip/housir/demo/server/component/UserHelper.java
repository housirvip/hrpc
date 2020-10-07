package vip.housir.demo.server.component;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import vip.housir.hrpc.annotation.HrpcComponent;

/**
 * @author housirvip
 */
@HrpcComponent
public class UserHelper {

    private static final Logger logger = LoggerFactory.getLogger(UserHelper.class);

    public void passwordCheck(String password) {
        logger.debug("password: " + password);
    }
}
