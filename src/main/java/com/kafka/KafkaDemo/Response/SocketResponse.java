package com.kafka.KafkaDemo.Response;

import com.kafka.KafkaDemo.Enums.MessageType;

import java.util.Map;

public class SocketResponse {
    private MessageType messageType;
    private Map<String, Object> params;

    public MessageType getMessageType() {
        return messageType;
    }

    public void setMessageType(MessageType messageType) {
        this.messageType = messageType;
    }

    public Map<String, Object> getParams() {
        return params;
    }

    public void setParams(Map<String, Object> params) {
        this.params = params;
    }
}
