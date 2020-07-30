package com.kafka.KafkaDemo.Configuration;

import com.google.gson.Gson;
import com.kafka.KafkaDemo.Enums.MessageType;
import org.springframework.web.socket.WebSocketMessage;

import java.util.Map;

public class WebsocketMessageCustom implements WebSocketMessage<Map<String, Object>> {

    private static final Gson gson = new Gson();
    private MessageType messageType;
    private Map<String, Object> payload;

    public WebsocketMessageCustom(MessageType messageType, Map<String, Object> payload) {
        super();
        this.messageType = messageType;
        this.payload = payload;
    }

    public WebsocketMessageCustom() {
        super();
    }

    public MessageType getMessageType(){
        return messageType;
    }

    public void setMessageType(MessageType messageType) {
        this.messageType = messageType;
    }

    public void setPayload(Map<String, Object> payload) {
        this.payload = payload;
    }

    @Override
    public Map<String, Object> getPayload() {
        return payload;
    }

    @Override
    public int getPayloadLength() {
        String jsonPayload = gson.toJson(payload);
        return jsonPayload.length();
    }

    @Override
    public boolean isLast() {
        return false;
    }

    @Override
    public String toString() {
        return "WebSocketMessageImpl [messageType=" + messageType + ", payload=" + payload + "]";
    }
}
