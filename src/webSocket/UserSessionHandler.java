package webSocket;

/**
 * Created by Girts Zemitis on 20/04/2016.
 * https://github.com/GirtsZemitis
 */


import model.User;

import javax.enterprise.context.ApplicationScoped;
import javax.json.JsonObject;
import javax.json.spi.JsonProvider;
import javax.websocket.Session;
import java.io.IOException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

@ApplicationScoped
public class UserSessionHandler {
    private int userId = 0;
    private final ArrayList<Session> sessions = new ArrayList<>();
    private final ArrayList<User> users = new ArrayList<>();

    public void addSession(Session session) {
        sessions.add(session);
        session.setMaxIdleTimeout(1000000);
        /*for (User user : users) {
            JsonObject addMessage = createAddMessage(user);
            sendToSession(session, addMessage);
        }*/

    }

    public void removeSession(Session session) {
        sessions.remove(session);
    }

    public void sendAllUsers(User user){
        for (User u : users) {
            if (u != user) {
                JsonObject addMessage = createAddMessage(u);
                sendToSession(user.getSession(), addMessage);
            }
        }
    }
    public void addUser(User user) {
        sendAllUsers(user);
        user.setId(userId);
        users.add(user);
        userId++;
        JsonObject addMessage = createAddMessage(user);
        sendToAllConnectedSessions(addMessage);
    }

    public void removeUser(int id) {
        User user = getUserById(id);
        if (user != null) {
            users.remove(user);
            JsonProvider provider = JsonProvider.provider();
            JsonObject removeMessage = provider.createObjectBuilder()
                    .add("action", "remove")
                    .add("id", id)
                    .build();
            sendToAllConnectedSessions(removeMessage);
        }
    }


    private User getUserById(int id) {
        for (User user : users) {
            if (user.getId() == id) {
                return user;
            }
        }
        return null;
    }

    private JsonObject createAddMessage(User user) {
        JsonProvider provider = JsonProvider.provider();
        JsonObject addMessage = provider.createObjectBuilder()
                .add("action", "add")
                .add("id", user.getId())
                .add("name", user.getName())
                .build();
        return addMessage;
    }

    private void sendToAllConnectedSessions(JsonObject message) {
        for (Session session : sessions) {
            sendToSession(session, message);
        }
    }

    private void sendToSession(Session session, JsonObject message) {
        try {
            session.getBasicRemote().sendText(message.toString());
        } catch (IOException ex) {
            sessions.remove(session);
            Logger.getLogger(UserSessionHandler.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void removeUser(Session session) {
        for (User user : users){
            if (user.getSession() == session){
                users.remove(user);
                JsonProvider provider = JsonProvider.provider();
                JsonObject removeMessage = provider.createObjectBuilder()
                        .add("action", "remove")
                        .add("id", user.getId())
                        .build();
                sendToAllConnectedSessions(removeMessage);
            }
        }
    }

    public void addMessage(String msg, Session session) {
        JsonProvider provider = JsonProvider.provider();
        for (User user : users){
            if (user.getSession() == session){
                JsonObject addMessage = provider.createObjectBuilder()
                        .add("action", "addMessage")
                        .add("message", msg)
                        .add("author", user.getName())
                        .build();
                sendToAllConnectedSessions(addMessage);
            }
        }

    }
}