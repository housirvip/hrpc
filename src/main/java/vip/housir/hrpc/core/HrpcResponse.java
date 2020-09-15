package vip.housir.hrpc.core;

import java.io.Serializable;

/**
 * @author housirvip
 */
public class HrpcResponse implements Serializable {

    private int statusCode;

    private Object body;

    private String message;

    public int getStatusCode() {
        return statusCode;
    }

    public void setStatusCode(int statusCode) {
        this.statusCode = statusCode;
    }

    public Object getBody() {
        return body;
    }

    public void setBody(Object body) {
        this.body = body;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    @Override
    public String toString() {
        return "HrpcResponse{" +
                "statusCode=" + statusCode +
                ", body=" + body +
                ", message='" + message + '\'' +
                '}';
    }
}
