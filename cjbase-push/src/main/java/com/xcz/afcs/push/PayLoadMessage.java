package com.xcz.afcs.push;

import java.io.Serializable;

/**
 * Created by jingang on 2017/6/16.
 */
public class PayLoadMessage implements Serializable{

    //消息编号
    private String messageId;

    //消息标题
    private String messageTitle;

    //消息内容
    private String message;

    //消息类型
    private String messageType;

    //未读消息数
    private Integer unReadNum;

    public String getMessageTitle() {
        return messageTitle;
    }

    public void setMessageTitle(String messageTitle) {
        this.messageTitle = messageTitle;
    }

    public String getMessageId() {
        return messageId;
    }

    public void setMessageId(String messageId) {
        this.messageId = messageId;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getMessageType() {
        return messageType;
    }

    public void setMessageType(String messageType) {
        this.messageType = messageType;
    }

    public Integer getUnReadNum() {
        return unReadNum;
    }

    public void setUnReadNum(Integer unReadNum) {
        this.unReadNum = unReadNum;
    }

    @Override
    public String toString() {
        return "PayLoadMessage{" +
                "messageId='" + messageId + '\'' +
                ", messageTitle='" + messageTitle + '\'' +
                ", message='" + message + '\'' +
                ", messageType='" + messageType + '\'' +
                ", unReadNum=" + unReadNum +
                '}';
    }
}
