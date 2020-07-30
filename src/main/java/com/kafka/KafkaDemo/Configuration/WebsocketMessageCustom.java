package com.kafka.KafkaDemo.Configuration;

import org.springframework.web.socket.WebSocketMessage;

import java.util.Map;

public class WebsocketMessageImpl implements WebSocketMessage<Map<String, Object>> {


    @Override
    public Map<String, Object> getPayload() {
        return null;
    }

    @Override
    public int getPayloadLength() {
        return 0;
    }

    @Override
    public boolean isLast() {
        return false;
    }
}
