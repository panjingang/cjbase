package com.xcz.afcs.util;

import org.apache.http.NameValuePair;
import org.apache.http.client.utils.URLEncodedUtils;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

/**
 * Created by jingang on 2017/6/22.
 */
public class URLUtil {

    public static String genUrl(String baseUrl, String paramName, String paramValue) {
        Map<String, Object> params = new HashMap<>();
        params.put(paramName, paramValue);
        return genUrl(baseUrl, params);
    }

    public static String genUrl(String baseUrl, Map<String, Object> params) {
        if (params == null || params.size() == 0) {
            return baseUrl;
        }
        StringBuilder sb = new StringBuilder();
        Iterator<Map.Entry<String, Object>> iter = params.entrySet().iterator();
        while(iter.hasNext()) {
            Map.Entry<String, Object> entry = iter.next();
            sb.append(entry.getKey()).append("=").append(entry.getValue()).append("&");
        }
        sb.deleteCharAt(sb.length()-1);
        if (baseUrl.contains("?")) {
            sb.insert(0, baseUrl+"&");
        }else {
            sb.insert(0, baseUrl+"?");
        }
        return sb.toString();
    }


    public static Map<String, String> getUrlParamMap(String url) {
        Map<String, String> params = new HashMap<>();
        List<NameValuePair> pairs = getUrlValuePair(url);
        for (NameValuePair pair : pairs) {
            params.put(pair.getName(), pair.getValue());
        }
        return params;
    }

    public static List<NameValuePair> getUrlValuePair(String url) {
        List<NameValuePair> params = null;
        try {
            params = URLEncodedUtils.parse(new URI(url), "UTF-8");
        } catch (URISyntaxException e) {
            throw new RuntimeException("URL 非法，请检查");
        }
        return params;
    }

}
