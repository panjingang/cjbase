package com.xcz.afcs.http;

import com.alibaba.fastjson.JSON;
import com.xcz.afcs.core.enums.BaseErrorCode;
import com.xcz.afcs.core.util.VOUtil;
import com.xcz.afcs.util.IOUtil;
import com.xcz.afcs.util.URLUtil;
import com.xcz.afcs.util.ValueUtil;
import org.apache.http.*;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.InputStreamEntity;
import org.apache.http.entity.StringEntity;
import org.apache.http.entity.mime.MultipartEntityBuilder;
import org.apache.http.entity.mime.content.InputStreamBody;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.InputStream;
import java.net.ConnectException;
import java.net.SocketTimeoutException;
import java.nio.charset.StandardCharsets;
import java.util.*;
import java.util.Map.Entry;

public class BaseHttpClient {

    private static final Logger LOG = LoggerFactory.getLogger(BaseHttpClient.class);

    public static String get(String url) {
        return get(url, null, Builder.create());
    }

    public static String get(String url, Builder builder) {
        return get(url, null, builder);
    }

    public static String get(String url, Map<String, Object> params, Builder builder) {
        String result = httpInvoke(URLUtil.genUrl(url, params), null, null, builder.get());
        if (VOUtil.isSuccess(builder.getResponseCode())) {
            return result;
        }
        return null;
    }

    public static String post( String url, Map<String, Object> params) {
        return post(url, params, Builder.create());
    }

    public static String post(String url, Map<String, Object> params, Builder builder) {
        HttpEntity entity = getEntity(null, null, null, params, builder.post());
        String result = httpInvoke(url, params, entity, builder);
        if (VOUtil.isSuccess(builder.getResponseCode())) {
            return result;
        }
        return null;
    }

    public static String upload(String url, String name, String filename, InputStream in,
                                               Map<String, Object> params, Builder builder) {
        HttpEntity entity = getEntity(name, filename, in, params, builder.setRequestType(RequestType.MIME).post());
        String result = httpInvoke(url, params, entity, builder);
        if (VOUtil.isSuccess(builder.getResponseCode())) {
            return result;
        }
        return null;
    }

    public static void download(String url,  File saveFile, Builder builder) {
        CloseableHttpClient httpclient = null;
        try {
            HttpClientBuilder clientBuilder = HttpClients.custom();
            clientBuilder.setDefaultRequestConfig(getDefaultRequestConfig(builder));
            httpclient = clientBuilder.build();
            LOG.info("[client->server] url:"+url);
            HttpGet get = new HttpGet(url);
            if (builder.header != null) {
                get.setHeader(builder.header);
            }
            HttpResponse response = httpclient.execute(get);
            int statusCode = response.getStatusLine().getStatusCode();
            HttpEntity responseEntity = response.getEntity();
            LOG.info("[server->client] url:"+url+", reponse:"+statusCode);
            if (statusCode == HttpStatus.SC_OK) {
                InputStream in = responseEntity.getContent();
                IOUtil.streamToFile(in, saveFile, true);
                builder.responseCode = BaseErrorCode.SUCCESS;
            } else {
                LOG.warn("downloadFile fail, url:" + url + ", status:" + statusCode);
            }
        } catch (ConnectException e) {
            LOG.error("HTTP Exception", e);
            builder.responseCode = BaseErrorCode.REMOTE_CONNECT_ERROR;
        }
        catch (SocketTimeoutException e) {
            LOG.error("HTTP Exception", e);
            builder.responseCode = BaseErrorCode.READ_TIMEOUT_ERROR;
        }
        catch (Exception e) {
            LOG.error("HTTP Exception", e);
            builder.responseCode = BaseErrorCode.INVOKE_REMOTE_ERROR;
        } finally {
            if (null != httpclient) {
                try {
                    httpclient.close();
                } catch (Exception e) {
                    LOG.error("http close error", e);
                }
            }
        }
    }



    @SuppressWarnings("unused")
    private static RequestConfig getDefaultRequestConfig(Builder builder) {
        return RequestConfig.custom().setSocketTimeout(builder.readTimeOut).setConnectTimeout(builder.connectTimeout).build();
    }

    private static String httpInvoke(String url, Map<String, Object> params, HttpEntity entity, Builder builder) {
        CloseableHttpClient httpclient = null;
        try {
            long startTime = System.currentTimeMillis();
            HttpClientBuilder clientBuilder = HttpClientBuilder.create();
            clientBuilder.setDefaultRequestConfig(getDefaultRequestConfig(builder));
            httpclient = clientBuilder.build();
            LOG.info("[client->server] url:"+url+", params:"+params);
            HttpResponse response = null;
            if (builder.isPost) {
                HttpPost post = new HttpPost(url);
                post.setEntity(entity);
                if (builder.header != null) {
                    post.setHeader(builder.header);
                }
                response = httpclient.execute(post);
            }else {
                HttpGet get = new HttpGet(url);
                if (builder.header != null) {
                    get.setHeader(builder.header);
                }
                response = httpclient.execute(get);
            }
            int statusCode = response.getStatusLine().getStatusCode();
            HttpEntity responseEntity = response.getEntity();
            String data = EntityUtils.toString(responseEntity, StandardCharsets.UTF_8);
            LOG.info("[server->client] url:"+url+" times:"+(System.currentTimeMillis()-startTime)+" response:"+data);
            builder.httpStatus = statusCode;
            if (statusCode == HttpStatus.SC_OK) {
                builder.responseCode = BaseErrorCode.SUCCESS;
                return data;
            } else {
                LOG.warn("http invoke fail, url:" + url + ", status:" + statusCode);
                builder.responseCode = BaseErrorCode.INVOKE_REMOTE_ERROR;
            }
        }
        catch (ConnectException e) {
            LOG.error("HTTP Exception", e);
            builder.responseCode = BaseErrorCode.REMOTE_CONNECT_ERROR;
        }
        catch (SocketTimeoutException e) {
            LOG.error("HTTP Exception", e);
            builder.responseCode = BaseErrorCode.READ_TIMEOUT_ERROR;
        }
        catch (Exception e) {
            LOG.error("HTTP Exception", e);
            builder.responseCode = BaseErrorCode.INVOKE_REMOTE_ERROR;
        } finally {
            if (null != httpclient) {
                try {
                    httpclient.close();
                } catch (Exception e) {
                    LOG.error("http close error", e);
                }
            }
        }
        return null;
    }

    private static HttpEntity getEntity(String name, String filename, InputStream in, Map<String, Object> params,
            Builder builder) {
        if (params == null) {
            params = new HashMap<>();
        }
        RequestType requestType = builder.requestType;
        switch (requestType) {
            case FORM:
                return createUrlEncodedFormEntity(params);
            case JSON:
                return createJSONEntity(params);
            case STREAM:
                return createInputStreamEntity(in);
            case MIME:
                return createMultipartEntity(name, new InputStreamBody(in, filename), params);
            default:
                return createUrlEncodedFormEntity(params);
        }
    }
    
    private static HttpEntity createJSONEntity(Map<String, Object> params) {
        StringEntity entity = new StringEntity(JSON.toJSONString(params), ContentType.APPLICATION_JSON);
        return entity;
    }
    
    private static HttpEntity createInputStreamEntity(InputStream in) {
        InputStreamEntity entity = new InputStreamEntity(in);
        return entity;
    }
    
    private static HttpEntity createUrlEncodedFormEntity(Map<String, Object> params) {
        List<NameValuePair> pairs = new ArrayList<NameValuePair>();
        Iterator<Entry<String, Object>> iter = params.entrySet().iterator();
        while (iter.hasNext()) {
            Entry<String, Object> entry = iter.next();
            pairs.add(new BasicNameValuePair(entry.getKey(), ValueUtil.getString(entry.getValue())));
        }
        UrlEncodedFormEntity entity = new UrlEncodedFormEntity(pairs, StandardCharsets.UTF_8);
        return entity;
    }
    
    private static HttpEntity createMultipartEntity(String name, InputStreamBody in, Map<String, Object> params) {
        MultipartEntityBuilder builder = MultipartEntityBuilder.create();
        builder.addPart(name, in);
        Iterator<Entry<String, Object>> iter = params.entrySet().iterator();
        while (iter.hasNext()) {
            Entry<String, Object> entry = iter.next();
            builder.addTextBody(entry.getKey(), ValueUtil.getString(entry.getValue()));
        }
        return builder.build();
    }


    public static class Builder {

        private RequestType requestType = RequestType.FORM;

        private ResponseType responseType = ResponseType.JSON;

        private Integer connectTimeout = 10 * 1000;

        private Integer readTimeOut = 60 * 1000;

        private boolean isPost = true;

        private Header header;

        private int httpStatus;

        private BaseErrorCode responseCode;

        public static Builder create() {
            return new Builder();
        }

        public RequestType getRequestType() {
            return requestType;
        }

        public Builder setRequestType(RequestType requestType) {
            this.requestType = requestType;
            return this;
        }

        public Builder setResponseType(ResponseType responseType) {
            this.responseType = responseType;
            return this;
        }

        public Builder post() {
            this.isPost = true;
            return this;
        }

        public Builder get() {
            this.isPost = false;
            return this;
        }

        public Builder setConnectTimeout(Integer connectTimeout) {
            this.connectTimeout = connectTimeout;
            return this;
        }

        public Builder setReadTimeOut(Integer readTimeOut) {
            this.readTimeOut = readTimeOut;
            return this;
        }

        public Builder setHeader(Header header) {
            this.header = header;
            return this;
        }

        public int getHttpStatus() {
            return httpStatus;
        }

        public BaseErrorCode getResponseCode() {
            return responseCode;
        }
    }


    
}
