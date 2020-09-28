package vip.housir.hrpc.consumer.transport;

import vip.housir.hrpc.core.HrpcRequest;
import vip.housir.hrpc.core.HrpcResponse;

/**
 * @author housirvip
 */
public interface Transport {

    /**
     * 远程调用，发送请求
     *
     * @param request HrpcRequest
     * @return HrpcResponse
     */
    HrpcResponse send(HrpcRequest request);
}
