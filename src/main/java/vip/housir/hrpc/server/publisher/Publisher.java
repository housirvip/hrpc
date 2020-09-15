package vip.housir.hrpc.server.publisher;

import vip.housir.hrpc.core.HrpcService;

import java.util.Map;

/**
 * @author housirvip
 */
public interface Publisher {

    /**
     * 发布服务
     *
     * @param port     端口号
     * @param services 发布的服务
     */
    void publish(int port, Map<String, HrpcService> services);

    /**
     * 停止服务发布
     */
    void shutdown();
}
