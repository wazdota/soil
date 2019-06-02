package com.soil.th.websocket;

import com.soil.th.util.JwtTokenUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import javax.websocket.OnClose;
import javax.websocket.OnError;
import javax.websocket.OnMessage;
import javax.websocket.OnOpen;
import javax.websocket.Session;
import javax.websocket.server.ServerEndpoint;
import java.util.concurrent.CopyOnWriteArraySet;
import java.util.HashMap;
import java.util.Map;
import javax.websocket.server.PathParam;

@Component
@ServerEndpoint("/websocket/{id}")
public class WebSocket {

    @Value("${jwt.tokenHead}")
    private String tokenHead;

    private int userId = 0;
    private Session session;

    private static CopyOnWriteArraySet<WebSocket> webSockets =new CopyOnWriteArraySet<>();
    private static Map<Integer,Session> sessionPool = new HashMap<Integer,Session>();
    private static Map<Integer,Integer> sessionCount = new HashMap<>();

    @OnOpen
    public void onOpen(Session session, @PathParam(value="id")int id) {
        this.session = session;
        webSockets.add(this);
        this.userId = id;
        Session session1 = sessionPool.get(this.userId);
        Integer count = sessionCount.get(this.userId);
        if(count == null || count==0){
            sessionCount.put(this.userId,1);
        }else{
            sessionCount.put(this.userId,count+1);
        }
        if(session1 != null){
            try{
                session1.close();
            }catch (Exception e) {
                e.printStackTrace();
            }
        }
        System.out.println("【websocket消息】有新的连接，总数为:"+webSockets.size());
        sessionPool.put(this.userId, session);
    }

    @OnClose
    public void onClose() {
        Integer count = sessionCount.get(this.userId);
        if(count >= 2){
            sessionCount.put(this.userId,count-1);
        }else {
            sessionCount.remove(this.userId);
            sessionPool.remove(this.userId);
        }
        webSockets.remove(this);
        System.out.println("【websocket消息】连接断开，总数为:"+webSockets.size());
    }

    @OnMessage
    public void onMessage(String message) {
        System.out.println("【websocket消息】收到客户端消息:"+message);
    }

    public void sendAllMessage(String message) {
        for(WebSocket webSocket : webSockets) {
            System.out.println("【websocket消息】广播消息:" + message);
            try {
                webSocket.session.getAsyncRemote().sendText(message);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public void sendOneMessage(int userId, String message) {
        Session session = sessionPool.get(userId);
        if (session != null) {
            try {
                session.getAsyncRemote().sendText(message);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}
