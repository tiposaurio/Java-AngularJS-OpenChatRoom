package webSocket;

/**
 * Created by Girts Zemitis on 19/04/2016.
 * https://github.com/GirtsZemitis
 */

import model.User;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.json.Json;
import javax.json.JsonObject;
import javax.json.JsonReader;
import javax.websocket.*;
import javax.websocket.server.ServerEndpoint;
import java.io.StringReader;
import java.util.logging.Level;
import java.util.logging.Logger;


@ApplicationScoped
@ServerEndpoint("/actions")
public class UserWebSocketServer {

    @Inject
    private UserSessionHandler sessionHandler;


    @OnOpen
    public void open(Session session) {
        sessionHandler.addSession(session);
    }

    @OnClose
    public void close(Session session) {
        sessionHandler.removeSession(session);
    }

    @OnError
    public void onError(Throwable error) {
        Logger.getLogger(UserWebSocketServer.class.getName()).log(Level.SEVERE, null, error);
    }

    @OnMessage
    public void handleMessage(String message, Session session) {
        Logger.getLogger(UserSessionHandler.class.getName()).log(Level.SEVERE, null, "aaa");
        try (JsonReader reader = Json.createReader(new StringReader(message))) {
            JsonObject jsonMessage = reader.readObject();

            /** Triggered when someone joins*/
            if ("add".equals(jsonMessage.getString("action"))) {
                User user = new User();
                user.setSession(session);
                user.setName(jsonMessage.getString("name"));
                sessionHandler.addUser(user);
                //sessionHandler.sendAllUsers(user);
            }
            /** Triggered when someone quit and
             * need to be cleaned from screen*/
            if ("remove".equals(jsonMessage.getString("action"))) {
                int id = (int) jsonMessage.getInt("id");
                sessionHandler.removeUser(id);
            }
            /** Triggered when client quits*/
            if ("removeUser".equals(jsonMessage.getString("action"))) {
                sessionHandler.removeUser(session);
            }

            if ("addMessage".equals(jsonMessage.getString("action"))) {
                String msg =  jsonMessage.getString("message");
                sessionHandler.addMessage(msg, session);
            }
        }
    }
}