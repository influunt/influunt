package security;

import java.util.Date;
import java.util.UUID;

import be.objectify.deadbolt.java.models.Subject;

public class UserSession {

    private String token;
    private Subject subject;
    private Date createdAt;

    public UserSession(final Subject subject) {
        this.token = UUID.randomUUID().toString();
        this.subject = subject;
        this.createdAt = new Date();
    }

    public String getToken() {
        return token;
    }

    public void setToken(final String token) {
        this.token = token;
    }

    public Subject getSubject() {
        return subject;
    }

    public void setSubject(final Subject subject) {
        this.subject = subject;
    }

    public Date getCreatedAt() {
        return createdAt;
    }

}
