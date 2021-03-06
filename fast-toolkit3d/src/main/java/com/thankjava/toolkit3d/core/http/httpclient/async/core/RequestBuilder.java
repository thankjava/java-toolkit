package com.thankjava.toolkit3d.core.http.httpclient.async.core;

import com.thankjava.toolkit3d.bean.http.async.AsyncHeaders;
import com.thankjava.toolkit3d.bean.http.async.AsyncHttpMethod;
import com.thankjava.toolkit3d.bean.http.async.AsyncParameters;
import com.thankjava.toolkit3d.bean.http.async.AsyncRequest;
import org.apache.http.client.entity.EntityBuilder;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.*;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.StringEntity;
import org.apache.http.entity.mime.HttpMultipartMode;
import org.apache.http.entity.mime.MultipartEntityBuilder;
import org.apache.http.entity.mime.content.FileBody;

import java.io.File;
import java.io.UnsupportedEncodingException;
import java.net.URISyntaxException;
import java.net.URLConnection;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.util.Map;


/**
 * 负责构建HttpRequestBase对象
 * <p>Function: RequestBuilder</p>
 * <p>Description: </p>
 *
 * @author acexy@thankjava.com
 * @version 1.0
 * @date 2016年12月12日 下午4:44:39
 */
public class RequestBuilder {

    /**
     * 创建请求信息
     *
     * @param asyncRequest
     * @return
     */
    public static HttpRequestBase builderRequest(AsyncRequest asyncRequest) {
        return getRequest(asyncRequest);
    }

    private static HttpRequestBase addEntityParams(HttpEntityEnclosingRequestBase httpEntityEnclosingRequestBase, AsyncRequest asyncRequest) {

        AsyncParameters parameter = asyncRequest.getParameter();

        if (parameter != null) {

            // 如果bodyString 和valuePair 同时存在，且请求类型为entityParams时，valuePair将需要转化为uri参数
            if (parameter.getNameValuePair() != null && parameter.getBodyString() != null) {
                HttpRequestBase httpRequestBase = (HttpRequestBase) httpEntityEnclosingRequestBase;
                try {
                    httpRequestBase.setURI(new URIBuilder(httpRequestBase.getURI()).setCharset(StandardCharsets.UTF_8).addParameters(parameter.getNameValuePair()).build());
                } catch (URISyntaxException e) {
                    e.printStackTrace();
                }
            } else {
                if (parameter.getNameValuePair() != null) {
                    try {
                        httpEntityEnclosingRequestBase.setEntity(new UrlEncodedFormEntity(parameter.getNameValuePair(), "UTF-8"));
                    } catch (UnsupportedEncodingException e) {
                        e.printStackTrace();
                    }
                }
            }

            if (parameter.getBodyString() != null) {
                httpEntityEnclosingRequestBase.setEntity(
                        new StringEntity(parameter.getBodyString(),
                                parameter.getContentType()
                        )
                );

            }

            if (parameter.getByteData() != null) {

                EntityBuilder entityBuilder = EntityBuilder.create();
                entityBuilder.setBinary(parameter.getByteData());

                if (parameter.getCharset() != null) {
                    entityBuilder.setContentEncoding(parameter.getCharset());
                }

                entityBuilder.setContentType(parameter.getContentType());

                httpEntityEnclosingRequestBase.setEntity(entityBuilder.build());

            } else if (parameter.getFile() != null) {

                EntityBuilder entityBuilder = EntityBuilder.create();
                entityBuilder.setFile(parameter.getFile());

                if (parameter.getCharset() != null) {
                    entityBuilder.setContentEncoding(parameter.getCharset());
                }
                entityBuilder.setContentType(parameter.getContentType());
                httpEntityEnclosingRequestBase.setEntity(entityBuilder.build());

            }

        }

        return httpEntityEnclosingRequestBase;
    }

    private static HttpRequestBase addBaseParams(HttpRequestBase httpRequestBase, AsyncRequest asyncRequest) {
        AsyncParameters parameter = asyncRequest.getParameter();
        if (parameter != null && parameter.getNameValuePair() != null) {
            try {
                httpRequestBase.setURI(new URIBuilder(httpRequestBase.getURI()).addParameters(parameter.getNameValuePair()).build());
            } catch (URISyntaxException e) {
                e.printStackTrace();
            }
        }
        return httpRequestBase;
    }

    private static HttpRequestBase getRequest(AsyncRequest asyncRequest) {

        AsyncHttpMethod asyncHttpMethod = asyncRequest.getAsyncHttpMethod();
        HttpRequestBase requestBase;

        boolean useEntity = false;
        switch (asyncHttpMethod) {
            case get:
                requestBase = new HttpGet(asyncRequest.getUrl());
                break;
            case post:
                requestBase = new HttpPost(asyncRequest.getUrl());
                useEntity = true;
                break;
            case patch:
                requestBase = new HttpPatch(asyncRequest.getUrl());
                useEntity = true;
                break;
            case delete:
                requestBase = new HttpDelete(asyncRequest.getUrl());
                break;
            case head:
                requestBase = new HttpHead(asyncRequest.getUrl());
            case options:
                requestBase = new HttpOptions(asyncRequest.getUrl());
            case put:
                requestBase = new HttpPut(asyncRequest.getUrl());
                useEntity = true;
                break;
            case trace:
                requestBase = new HttpTrace(asyncRequest.getUrl());
            default:
                return new HttpGet(asyncRequest.getUrl());
        }

        AsyncHeaders header = asyncRequest.getHeader();
        if (header != null) {
            requestBase.setHeaders(header.toHeaderArray());
        }

        if (useEntity) {
            requestBase = addEntityParams((HttpEntityEnclosingRequestBase) requestBase, asyncRequest);
        } else {
            requestBase = addBaseParams(requestBase, asyncRequest);
        }

        return requestBase;

    }
}
