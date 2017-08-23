package com.xcz.afcs.biz.util;


import com.alibaba.fastjson.JSON;
import com.xcz.afcs.biz.model.SessionKey;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

public class SessionHelper {

    private static final Logger LOG = LoggerFactory.getLogger(SessionHelper.class);

    protected static CacheHelper getCacheHelper() {
        return CacheHelper.getInstance();
    }


    /**
     * 从缓存中获取数据
     * @param cls
     * @param sessionKey
     * @param <T>
     * @return
     */
    public static <T> T getSessionInfo(SessionKey sessionKey, Class<T> cls) {
        try {
            //从缓存获取会话令牌对应的字符串信息
            String stringInfo = getCacheHelper().get(sessionKey.getKey());
            if (StringUtils.isNotBlank(stringInfo)) {
                //解析会话身份信息
                return JSON.parseObject(stringInfo, cls);
            }
        } catch (Exception e) {
            LOG.error("getSessionInfo failed for key:"+sessionKey, e);
        }
        return null;
    }

    public static <T> List<T> getSessionListInfo(SessionKey sessionKey, Class<T> cls) {
        try {
            //从缓存获取会话令牌对应的字符串信息
            String stringInfo = getCacheHelper().get(sessionKey.getKey());
            if (StringUtils.isNotBlank(stringInfo)) {
                //解析会话身份信息
                return JSON.parseArray(stringInfo, cls);
            }
        } catch (Exception e) {
            LOG.error("getSessionInfo failed for key:"+sessionKey, e);
        }
        return null;
    }


    /**
     * 保存缓存信息
     * @param sessionKey
     * @param cls
     * @param sessionTimeout
     * @param <T>
     */
    public static <T> void saveSessionInfo(SessionKey sessionKey, T cls, int sessionTimeout) {
        String stringInfo = JSON.toJSONString(cls);
        // 根据会话令牌保存信息到缓存
        getCacheHelper().set(sessionKey.getKey(), stringInfo, sessionTimeout);
    }

    public static <T> void saveSessionInfo(SessionKey sessionKey, List<T> cls, int sessionTimeout) {
        String stringInfo = JSON.toJSONString(cls);
        // 根据会话令牌保存信息到缓存
        getCacheHelper().set(sessionKey.getKey(), stringInfo, sessionTimeout);
    }


    public static void refreshSession(SessionKey sessionKey, int sessionTimeout) {
        getCacheHelper().refresh(sessionKey.getKey(), sessionTimeout);
    }

    /**
     * 销毁缓存
     * @param sessionKey
     */
    public static void destroySessionInfo(SessionKey sessionKey) {
        getCacheHelper().destroy(sessionKey.getKey());
    }

}
