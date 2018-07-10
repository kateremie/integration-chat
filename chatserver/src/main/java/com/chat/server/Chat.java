package com.chat.server;

import com.mashape.unirest.http.Unirest;
import io.javalin.Javalin;
import org.eclipse.jetty.websocket.api.Session;
import org.json.JSONObject;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import static j2html.TagCreator.*;

public class Chat {

    private static Map<Session, String> userUsernameMap = new ConcurrentHashMap<Session, String>();

    public static void main(String[] args) {
        Javalin.create()
                .port(7071)
                .enableStaticFiles("/public")
                .ws("/chat", ws -> {
                    ws.onConnect(session ->
                    {
                        String username = "User " + Unirest.get("http://localhost:7070/hashcode").asString().getBody();

                        userUsernameMap.put(session, username);

                        broadcastMessage("Server", (username + " joined the chat"));
                    });
                    ws.onClose((session, status, message) -> {
                        String userName = userUsernameMap.get(session);
                        userUsernameMap.remove(session);
                        broadcastMessage("Server", (userName + " left the chat"));
                    });
                    ws.onMessage((session, message) -> broadcastMessage(userUsernameMap.get(session), message));
                }).start();
    }

    private static void broadcastMessage(String sender, String message) {
        userUsernameMap.keySet().stream().filter(Session::isOpen).forEach(
                session ->
                {
                    try {
                        session.getRemote().sendString(
                                new JSONObject()
                                        .put("userMessage",
                                                createHtmlMessageFromSender(sender, message))
                                        .put("userlist", userUsernameMap.values()).toString()
                        );
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
        );
    }

    private static String createHtmlMessageFromSender(String sender, String message) {
        return article(
                b(sender + " says:"),
                span(attrs(".timestamp"), new SimpleDateFormat("HH:mm:ss").format(new Date())),
                p(message)
        ).render();
    }
}
