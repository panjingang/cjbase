package com.xcz.afcs.push;

/**
 * Created by jingang on 2017/6/16.
 */
public interface AfcsPushMessageService {

     void sendNotification(PlatformType platformType, String deviceToken, PayLoadMessage message);

}
