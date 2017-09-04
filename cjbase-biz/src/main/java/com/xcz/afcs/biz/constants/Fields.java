package com.xcz.afcs.biz.constants;

import com.xcz.afcs.core.context.DataContext;

public interface Fields {

      //返回码
      String RET_CODE   = "retCode";

      //返回原因
      String RET_MSG     = "retMsg";

      //会话Token
      String ACCESS_TOKEN = "accessToken";

      String AUTHORIZATION = "Authorization";

      String BEARER        = "Bearer ";

      String CONTEXT_ACCESS_URL      = DataContext.NAMESPACE+".accessUrl";
      String CONTEXT_CLIENT_IP       = DataContext.NAMESPACE+".clientIp";
      String CONTEXT_ACCESS_TOKEN    = DataContext.NAMESPACE+".accessToken";
}
