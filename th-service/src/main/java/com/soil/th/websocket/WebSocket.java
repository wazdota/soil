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
@ServerEndpoint("/websocket/{token}")
public class WebSocket {

    @Value("${jwt.tokenHead}")
    private String tokenHead;

    private JwtTokenUtil jwtTokenUtil;
    private int userId;
    private Session session;

    private static CopyOnWriteArraySet<WebSocket> webSockets =new CopyOnWriteArraySet<>();
    private static Map<Integer,Session> sessionPool = new HashMap<Integer,Session>();

    @Autowired
    public WebSocket(JwtTokenUtil jwtTokenUtil){
        this.jwtTokenUtil = jwtTokenUtil;
    }

    @OnOpen
    public void onOpen(Session session, @PathParam(value="token")String authHeader) {
        this.session = session;
        webSockets.add(this);
        if (authHeader != null && authHeader.startsWith(tokenHead)){
            String authToken = authHeader.substring(tokenHead.length());
            String role = jwtTokenUtil.getAuthorityFromToken(authToken);
            int userId = jwtTokenUtil.getUserIdFromToken(authToken);
            if(userId != 0 && role.equals("ROLE_USER")) {
                if (jwtTokenUtil.validateToken(authToken)){
                    this.userId = userId;
                }
            }
        }
        sessionPool.put(userId, session);
        System.out.println("【websocket消息】有新的连接，总数为:"+webSockets.size());
        if(this.userId == 0){
            try{
                session.close();
            }catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    @OnClose
    public void onClose() {
        sessionPool.remove(this.userId);
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
