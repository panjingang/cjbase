package com.xcz.afcs.push.impl;

import cn.jpush.api.JPushClient;
import cn.jpush.api.push.PushResult;
import cn.jpush.api.push.model.Platform;
import cn.jpush.api.push.model.PushPayload;
import cn.jpush.api.push.model.audience.Audience;
import cn.jpush.api.push.model.notification.IosNotification;
import cn.jpush.api.push.model.notification.Notification;
import com.xcz.afcs.push.AfcsPushMessageService;
import com.xcz.afcs.push.PayLoadMessage;
import com.xcz.afcs.push.PlatformType;
import com.xcz.afcs.push.PushCertConfig;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by jingang on 2017/6/16.
 */
public class AfcsPushMessageServiceImpl implements AfcsPushMessageService {

    private static final Logger LOG = LoggerFactory.getLogger(AfcsPushMessageServiceImpl.class);

    private static final String MESSAGE_ID   = "msgId";

    private static final String MESSAGE_TYPE = "msgType";

    private JPushClient pushClient = null;

    private PushCertConfig pushCertConfig;

    public void init() {
        if (pushCertConfig == null) {
            throw new IllegalArgumentException(this.getClass().getName()+" 缺少 pushCertConfig 配置项");
        }
        if (StringUtils.isBlank(pushCertConfig.getAppKey())) {
            throw new IllegalArgumentException("请配置推送AppKey");
        }
        if (StringUtils.isBlank(pushCertConfig.getAppSecret())) {
            throw new IllegalArgumentException("请配置推送AppSecret");
        }
        try {
            pushClient = new JPushClient(pushCertConfig.getAppSecret(), pushCertConfig.getAppKey(),  1 == pushCertConfig.getIsProduct(), 30L);
        }catch (Exception e) {
            LOG.error("创建PushClient失败", e);
        }
    }


    private JPushClient getPushClient() {
        return pushClient;
    }

    @Override
    public void sendNotification(PlatformType platformType, String deviceToken, PayLoadMessage message) {
        LOG.debug("推送消息， 设备号:"+deviceToken+","+message.toString());
        Map<String, String> extraParams = new HashMap<>();
        extraParams.put(MESSAGE_ID, message.getMessageId());
        extraParams.put(MESSAGE_TYPE, message.getMessageType());
        if (platformType == PlatformType.IOS) {
            sendIosNotification(deviceToken, message, extraParams);
        }else {
            sendAndroidNotification(deviceToken, message, extraParams);
        }
    }

    private void sendIosNotification(String deviceToken, PayLoadMessage message, Map<String, String> extraParams) {
        try {
            PushPayload payload = PushPayload.newBuilder()
                    .setPlatform(Platform.ios())
                    .setAudience(Audience.registrationId(deviceToken))
                    .setNotification(Notification.newBuilder().setAlert(message.getMessage())
                            .addPlatformNotification(IosNotification.newBuilder().incrBadge(message.getUnReadNum()).addExtras(extraParams).build())
                            .build())
                    .build();
            PushResult result = getPushClient().sendPush(payload);
            LOG.debug("Result:"+result);
            //Thread.sleep(2000);
            //MessagesResult result1 = getPushClient().sendPushValidate();
           // LOG.debug("Result:"+result1);
        } catch (Exception e) {
            LOG.error("推送消息失败, 设备号:"+deviceToken+" 标题："+message.getMessageTitle()+", 内容:"+message.getMessage(), e);
        }

    }

    private void sendAndroidNotification(String deviceToken, PayLoadMessage message, Map<String, String> extraParams) {
        try {
            PushResult result = getPushClient().sendAndroidNotificationWithRegistrationID(message.getMessageTitle(), message.getMessage(), extraParams, deviceToken);
            LOG.debug("Result:"+result);
        } catch (Exception e) {
           LOG.error("推送消息失败, 设备号:"+deviceToken+" 标题："+message.getMessageTitle()+", 内容:"+message.getMessage(), e);
        }
    }

    public void close() {

    }


    public PushCertConfig getPushCertConfig() {
        return pushCertConfig;
    }

    public void setPushCertConfig(PushCertConfig pushCertConfig) {
        this.pushCertConfig = pushCertConfig;
    }
}
