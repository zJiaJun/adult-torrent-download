package com.github.zjiajun.adult.connection;

import com.github.zjiajun.adult.Page;
import com.github.zjiajun.adult.Request;
import okhttp3.ResponseBody;

import java.io.IOException;

/**
 * @author zhujiajun
 * @since 2017/2/3
 */
public abstract class AbstractConnection {

    private final RetrofitClient retrofitClient = RetrofitClient.getInstance();

    /**
     * 请求之前的回调操作
     * @param request 请求对象
     */
    protected abstract void beforeConnection(Request request);

    protected Page connect(Request request) {
        beforeConnection(request);
        ResponseBody responseBody;
        Page page = new Page();
        try {
            switch (request.getMethod()) {
                case GET:
                    responseBody = retrofitClient.get(request.getUrl(), request.getData());
                    break;
                case POST:
                    responseBody = retrofitClient.post(request.getUrl(), request.getData());
                    break;
                default:
                    throw new RuntimeException();
            }
            afterConnection(request, responseBody, page);
        } catch (IOException e) {
            e.printStackTrace();
            exceptionCaught(request, e);
        }
        return page;
    }


    /**
     * 发生异常时回调操作
     * @param request   请求对象
     * @param exception 异常信息
     */
    protected abstract void exceptionCaught(Request request, Exception exception);

    /**
     * 请求完成后的回调操作
     * @param request       请求对象
     * @param responseBody  返回对象
     * @throws IOException
     */
    protected abstract void afterConnection(Request request, ResponseBody responseBody, Page page) throws IOException;
}
