package model;

import javax.websocket.Session;

/**
 * Created by Girts Zemitis on 19/04/2016.
 * https://github.com/GirtsZemitis
 */

public class User {

    private int id;
    private String name;
    private String status;
    private String type;
    private String description;
    private Session session;

    public User() {
    }

    public Session getSession() {
        return session;
    }

    public void setSession(Session session) {
        this.session = session;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getStatus() {
        return status;
    }

    public String getType() {
        return type;
    }

    public String getDescription() {
        return description;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public void setType(String type) {
        this.type = type;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
