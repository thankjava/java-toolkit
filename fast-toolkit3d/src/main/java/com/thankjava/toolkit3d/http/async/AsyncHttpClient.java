package com.thankjava.toolkit3d.http.async;

import java.io.IOException;

import com.thankjava.toolkit3d.http.async.entity.AsyncResponse;
import com.thankjava.toolkit3d.http.async.entity.ResponseCallback;
import org.apache.http.cookie.Cookie;
import org.apache.http.impl.nio.client.CloseableHttpAsyncClient;
import com.thankjava.toolkit3d.http.async.core.DoRequest;
import com.thankjava.toolkit3d.http.async.entity.Cookies;
import com.thankjava.toolkit3d.http.async.entity.AsyncRequest;

public class AsyncHttpClient {

    /**
     * 同步请求的处理
     */
    private static DoRequest doRequest;

    private static CloseableHttpAsyncClient closeableHttpAsyncClient;

    AsyncHttpClient(CloseableHttpAsyncClient closeableHttpAsyncClient) {
        AsyncHttpClient.closeableHttpAsyncClient = closeableHttpAsyncClient;
        closeableHttpAsyncClient.start();
        doRequest = DoRequest.getInterface(closeableHttpAsyncClient);
    }

    /**
     * 发生同步请求,并自动携带历史可用的请求头部信息
     * <p>Function: syncRequestWithSession</p>
     * <p>Description: </p>
     *
     * @param asyncRequest
     * @return
     * @author acexy@thankjava.com
     * @date 2016年12月12日 下午3:54:33
     * @version 1.0
     */
    public AsyncResponse syncRequestWithSession(AsyncRequest asyncRequest) {
        return doRequest.requestWithSession(asyncRequest, true, null);
    }

    /**
     * 发起异步请求,并自动携带历史可用的请求头部信息
     *
     * @param asyncRequest
     * @param responseCallback
     */
    public void asyncRequestWithSession(AsyncRequest asyncRequest, ResponseCallback responseCallback) {
        doRequest.requestWithSession(asyncRequest, true, responseCallback);
    }

    /**
     * 获取历史请求CookieStore
     * <p>Function: getRequestCookieStore</p>
     * <p>Description: </p>
     *
     * @return
     * @author acexy@thankjava.com
     * @date 2017年6月12日 下午3:31:00
     * @version 1.0
     */
    public Cookies getAllCookies() {
        return new Cookies(DoRequest.getSyncCookieStore().getCookies());
    }

    /**
     * 获取指定的cookie
     * <p>Function: getCookie</p>
     * <p>Description: </p>
     *
     * @param cookieName
     * @return
     * @author acexy@thankjava.com
     * @date 2017年6月12日 下午3:41:55
     * @version 1.0
     */
    public Cookie getCookie(String cookieName) {
        return getAllCookies().getCookie(cookieName);
    }

    /**
     * 停止整个client
     * <p>Function: shutdown</p>
     * <p>Description: </p>
     *
     * @author acexy@thankjava.com
     * @date 2016年12月14日 下午2:56:25
     * @version 1.0
     */
    public void shutdown() {
        try {
            closeableHttpAsyncClient.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
