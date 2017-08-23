package com.xcz.afcs.http;

import com.alibaba.fastjson.JSON;
import com.xcz.afcs.core.enums.BaseErrorCode;
import com.xcz.afcs.core.util.VOUtil;
import com.xcz.afcs.util.IOUtil;
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

public class HttpClientUtil {

    public static final String KEY_STATUS_CODE = "statusCode";

    public static final String KEY_DATA = "data";

    private static final Logger LOG = LoggerFactory.getLogger(HttpClientUtil.class);

    private static final int socketTimeout = 30000;

    private static final int connectTimeout = 30000;

    public static Map<String, Object> postData(String url, Map<String, Object> params) {
        return postData(url, null, params, null, RequestType.FORM, ResponseType.JSON);
    }

    public static Map<String, Object> postData(String url, Map<String, Object> params, Header header) {
        return postData(url, null, params, header, RequestType.FORM, ResponseType.JSON);
    }

    public static Map<String, Object> postData(String url, Map<String, Object> params, RequestType requestType,
            ResponseType responseType) {
        return postData(url,  null, params, null, requestType, responseType);
    }

    public static Map<String, Object> postData(String url, InputStream in) {
        return postData(url,  in, null, null, RequestType.STREAM, ResponseType.JSON);
    }

    public static Map<String, Object> postData(String url, String name, String filename, InputStream in,
                                               Map<String, Object> params) {
        return postData(url, name, filename, in, params, null, RequestType.MIME, ResponseType.JSON);
    }


    public static Map<String, Object> downloadFile(String url, File saveFile,  Header header) {
        CloseableHttpClient httpclient = null;
        try {
            HttpClientBuilder clientBuilder = HttpClients.custom();
            clientBuilder.setDefaultRequestConfig(RequestConfig.custom().setSocketTimeout(600*1000).setConnectTimeout(connectTimeout).build());
            httpclient = clientBuilder.build();
            LOG.info("[client->server] url:"+url);
            HttpGet get = new HttpGet(url);
            if (header != null) {
                get.setHeader(header);
            }
            HttpResponse response = httpclient.execute(get);
            int statusCode = response.getStatusLine().getStatusCode();
            HttpEntity responseEntity = response.getEntity();
            LOG.info("[server->client] url:"+url+", reponse:"+statusCode);
            if (statusCode == HttpStatus.SC_OK) {
                InputStream in = responseEntity.getContent();
                IOUtil.streamToFile(in, saveFile);
                return VOUtil.genSuccessResult();
            } else {
                LOG.warn("downloadFile fail, url:" + url + ", status:" + statusCode);
            }
        } catch (Exception e) {
            LOG.error("downloadFile Exception", e);
        } finally {
            if (null != httpclient) {
                try {
                    httpclient.close();
                } catch (Exception e) {
                    LOG.error("http close error", e);
                }
            }
        }
        return VOUtil.genCommonErrorResult();
    }

    public static Map<String, Object> getData(String url, Header header) {
        CloseableHttpClient httpclient = null;
        try {
            HttpClientBuilder clientBuilder = HttpClients.custom();
            clientBuilder.setDefaultRequestConfig(getDefaultRequestConfig());
            httpclient = clientBuilder.build();
            LOG.info("[client->server] url:"+url);
            HttpGet get = new HttpGet(url);
            if (header != null) {
                get.setHeader(header);
            }
            HttpResponse response = httpclient.execute(get);
            int statusCode = response.getStatusLine().getStatusCode();
            HttpEntity responseEntity = response.getEntity();
            String data = EntityUtils.toString(responseEntity, StandardCharsets.UTF_8);
            LOG.info("[server->client] url:"+url+", reponse:"+data);
            Map<String, Object> res = new HashMap<String, Object>();
            res.put(KEY_STATUS_CODE, statusCode);
            res.put(KEY_DATA, data);
            if (statusCode == HttpStatus.SC_OK) {
                return VOUtil.setSuccessResult(res);
            } else {
                LOG.warn("getData fail, url:" + url + ", status:" + statusCode);
                return VOUtil.setErrorResult(res, BaseErrorCode.INVOKE_REMOTE_ERROR);
            }
        }
        catch (ConnectException e) {
            LOG.error("getData Exception", e);
            return VOUtil.genErrorResult(BaseErrorCode.REMOTE_CONNECT_ERROR);
        }
        catch (SocketTimeoutException e) {
            LOG.error("getData Exception", e);
            return VOUtil.genErrorResult(BaseErrorCode.READ_TIMEOUT_ERROR);
        }
        catch (Exception e) {
            LOG.error("getData Exception", e);
            return VOUtil.genErrorResult(BaseErrorCode.INVOKE_REMOTE_ERROR);
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
    private static RequestConfig getDefaultRequestConfig() {
        return RequestConfig.custom().setSocketTimeout(socketTimeout).setConnectTimeout(connectTimeout).build();
    }

    public static Map<String, Object> postData(String url, InputStream in,
                                               Map<String, Object> params, Header header, RequestType requestType, ResponseType responseType){
        return postData(url, null, null, in, params, header, requestType, responseType);
    }

    public static Map<String, Object> postData(String url, String name, String filename, InputStream in,
            Map<String, Object> params, Header header, RequestType requestType, ResponseType responseType) {
        CloseableHttpClient httpclient = null;
        try {
            HttpClientBuilder clientBuilder = HttpClientBuilder.create();
            clientBuilder.setDefaultRequestConfig(getDefaultRequestConfig());
            httpclient = clientBuilder.build();
            LOG.info("[client->server] url:"+url+", params:"+params);
            HttpPost post = new HttpPost(url);
            post.setEntity(getEntity(name, filename, in, params, requestType));
            if (header != null) {
                post.setHeader(header);
            }
            HttpResponse response = httpclient.execute(post);
            int statusCode = response.getStatusLine().getStatusCode();
            HttpEntity responseEntity = response.getEntity();
            String data = EntityUtils.toString(responseEntity, StandardCharsets.UTF_8);
            Map<String, Object> res = new HashMap<String, Object>();
            res.put(KEY_STATUS_CODE, statusCode);
            res.put(KEY_DATA, data);
            LOG.info("[server->client] url:"+url+", reponse:"+data);
            if (statusCode == HttpStatus.SC_OK) {
                return VOUtil.setSuccessResult(res);
            } else {
                LOG.warn("postData fail, url:" + url + ", status:" + statusCode);
                return VOUtil.setErrorResult(res, BaseErrorCode.INVOKE_REMOTE_ERROR);
            }
        }
        catch (ConnectException e) {
            LOG.error("postData Exception", e);
            return VOUtil.genErrorResult(BaseErrorCode.REMOTE_CONNECT_ERROR);
        }
        catch (SocketTimeoutException e) {
            LOG.error("postData Exception", e);
            return VOUtil.genErrorResult(BaseErrorCode.READ_TIMEOUT_ERROR);
        }
        catch (Exception e) {
            LOG.error("postData Exception", e);
        } finally {
            if (null != httpclient) {
                try {
                    httpclient.close();
                } catch (Exception e) {
                    LOG.error("http close error", e);
                }
            }
        }
        return VOUtil.genCommonErrorResult();
    }
    
    private static HttpEntity getEntity(String name, String filename, InputStream in, Map<String, Object> params,
            RequestType requestType) {
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


    
}
