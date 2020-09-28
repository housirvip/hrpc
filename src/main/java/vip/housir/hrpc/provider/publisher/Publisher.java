package vip.housir.hrpc.provider.publisher;

import vip.housir.hrpc.core.HrpcService;

/**
 * @author housirvip
 */
public interface Publisher {

    /**
     * 发布服务
     *
     * @param port 端口号
     */
    void publish(int port);

    /**
     * 停止服务发布
     */
    void shutdown();

    /**
     * 发布的服务，注册
     *
     * @param serviceName 服务名称
     * @param service     服务实现
     */
    void register(String serviceName, HrpcService service);

    /**
     * 服务启动
     *
     * @param port 端口号
     */
    void start(int port);
}
