package com.kafka.KafkaDemo.Handler;

import com.google.gson.Gson;
import com.kafka.KafkaDemo.Configuration.WebsocketMessageCustom;
import com.kafka.KafkaDemo.Constant.SocketConstant;
import com.kafka.KafkaDemo.Enums.MessageType;
import com.kafka.KafkaDemo.Response.SocketResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.*;

import java.io.IOException;
import java.util.*;


@Component
public class CustomWebSocketHandler extends SocketConstant implements WebSocketHandler {

    private static final Logger logger = LoggerFactory.getLogger(CustomWebSocketHandler.class);

    private static final String MESSAGE_TYPE = "messageType";

    private static final Gson GSON = new Gson();

    Set<WebSocketSession> listSessions = new HashSet<>();

    Set<WebSocketSession> userSessions = new HashSet<>();

    @Override
    public void afterConnectionEstablished(WebSocketSession webSocketSession) {
        listSessions.add(webSocketSession);
    }

    @Override
    public void handleMessage(WebSocketSession webSocketSession, WebSocketMessage<?> webSocketMessage) throws Exception {
        SocketResponse responseMessage = GSON.fromJson(webSocketMessage.getPayload().toString(), SocketResponse.class);

        if(responseMessage != null && responseMessage.getMessageType() == MessageType.SUBSCRIBE_USER_UPDATE){
            subscribeUserUpdate(webSocketSession, responseMessage);
        }
    }

    @Override
    public void handleTransportError(WebSocketSession webSocketSession, Throwable throwable) throws Exception {

    }

    @Override
    public void afterConnectionClosed(WebSocketSession webSocketSession, CloseStatus closeStatus) throws Exception {
        userSessions.remove(webSocketSession);
    }

    @Override
    public boolean supportsPartialMessages() {
        return false;
    }

    public void subscribeUserUpdate(WebSocketSession webSocketSession, SocketResponse responseMessage){
        Map<String, Object> openingMsg = new HashMap<>();
        openingMsg.put(MESSAGE_TYPE, responseMessage.getMessageType());

        userSessions.add(webSocketSession);
        sendInitialMsg(openingMsg, webSocketSession);
    }

    private void sendData(WebsocketMessageCustom message, Set<WebSocketSession> sessionList) {
        if (sessionList != null)
            sessionList.parallelStream().forEach(session -> {
                try {
                    synchronized (session) {
                        session.sendMessage(new TextMessage(GSON.toJson(message.getPayload())));
                    }
                } catch (IOException e) {
                    logger.error(e.getMessage(), e);
                }
            });
    }

    private void sendInitialMsg(Map<String, Object> initialMsg, WebSocketSession session) {
        try {
            synchronized (session){
                session.sendMessage(new TextMessage(GSON.toJson(initialMsg)));
            }
        }catch (IOException e){
            logger.error(e.getMessage(), e);
        }
    }

    public void sendLevelPairStatus(String lvlPairStatus){
        WebsocketMessageCustom websocketMessageCustom = new WebsocketMessageCustom();
        Map<String, Object> lvlPairMsg = new HashMap<>();
        lvlPairMsg.put("data", lvlPairStatus);
        lvlPairMsg.put(MESSAGE_TYPE,MessageType.USER_UPDATE);
        websocketMessageCustom.setPayload(lvlPairMsg);

        sendData(websocketMessageCustom, userSessions);
    }
}
