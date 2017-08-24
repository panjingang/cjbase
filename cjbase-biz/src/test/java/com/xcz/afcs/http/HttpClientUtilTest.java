package com.xcz.afcs.http;

import com.fasterxml.jackson.core.type.TypeReference;
import com.xcz.afcs.api.result.BaseResult;
import com.xcz.afcs.api.result.ObjectResult;
import com.xcz.afcs.biz.http.AfcsHttpClient;
import org.junit.Test;

import java.util.HashMap;

/**
 * Created by jingang on 2017/8/24.
 */
public class HttpClientUtilTest {

    @Test
     public void get() throws Exception {
        BaseHttpClient.Builder builder = new BaseHttpClient.Builder();

        HashMap params = new HashMap();
        params.put("tenantId", "F1");

        BaseResult result = AfcsHttpClient.get(new TypeReference<BaseResult>(){}, "http://192.168.1.138:8950/api/management/tenant/getTenant", params, builder);

        System.out.println(result);
     }
}
