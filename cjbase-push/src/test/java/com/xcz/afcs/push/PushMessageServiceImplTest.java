package com.xcz.afcs.push;

import com.xcz.afcs.push.impl.AfcsPushMessageServiceImpl;
import org.junit.Test;

/**
 * Created by jingang on 2017/6/16.
 */
public class PushMessageServiceImplTest {

    @Test
    public void sendMessage() throws InterruptedException {
        PushCertConfig config = new PushCertConfig();
        config.setAppKey("1dd6787ea700a717f4a38975");
        config.setAppSecret("344d5d5a90c20a93a492a6b8");
        AfcsPushMessageServiceImpl pushMessageService = new AfcsPushMessageServiceImpl();
        pushMessageService.setPushCertConfig(config);
        pushMessageService.init();

        PayLoadMessage payLoadMessage = new PayLoadMessage();
        payLoadMessage.setMessageTitle("测试");
        payLoadMessage.setMessageId("1001");
        payLoadMessage.setUnReadNum(1);
        payLoadMessage.setMessage("这是一条测试推送消息");
        pushMessageService.sendNotification(PlatformType.ANDROID, "100d85590973ca27221", payLoadMessage);

        Thread.sleep(2000);
    }
}
