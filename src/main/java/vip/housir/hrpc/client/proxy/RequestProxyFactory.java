package vip.housir.hrpc.client.proxy;

import vip.housir.hrpc.client.transport.SocketTransport;

import java.lang.reflect.Proxy;

/**
 * @author housirvip
 */
public class RequestProxyFactory {

    @SuppressWarnings("unchecked")
    public static <T> T createProxy(final Class<T> clazz, SocketTransport transport) {
        return (T) Proxy.newProxyInstance(clazz.getClassLoader(),
                new Class<?>[]{clazz}, new RequestInvocationHandler(transport));
    }
}
