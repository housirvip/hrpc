package vip.housir.hrpc.client.proxy;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import vip.housir.hrpc.core.HrpcRequest;
import vip.housir.hrpc.core.HrpcResponse;
import vip.housir.hrpc.client.transport.Transport;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;

/**
 * @author housirvip
 */
public class RequestInvocationHandler implements InvocationHandler {

    private static final Logger logger = LoggerFactory.getLogger(RequestInvocationHandler.class);

    private final Transport transport;

    public RequestInvocationHandler(Transport transport) {
        this.transport = transport;
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {

        HrpcRequest request = new HrpcRequest();
        request.setMethodName(method.getName());
        request.setClassName(method.getDeclaringClass().getName());
        request.setArgs(args);
        request.setTypes(method.getParameterTypes());

        logger.debug(request.toString());
        HrpcResponse response = transport.send(request);
        logger.debug(response.toString());

        return response.getBody();
    }
}
