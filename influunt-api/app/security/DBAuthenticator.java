package security;

import be.objectify.deadbolt.java.models.Subject;
import com.google.inject.Singleton;
import helpers.HashHelper;
import models.Usuario;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;


@Singleton
public class DBAuthenticator implements Authenticator {

    private Map<String, UserSession> sessions = new HashMap<String, UserSession>();

    @Override
    public Subject getSubjectByCredentials(final String login, final String password) {
        Usuario usuario = Usuario.find.byId(login);
        return usuario != null && HashHelper.checkPassword(password, usuario.getSenha()) ? usuario : null;
    }

    @Override
    public Subject getSubjectByToken(final String token) {
        UserSession us = sessions.get(token);
        return us != null ? us.getSubject() : null;
    }

    @Override
    public String createSession(final Subject subject) {
        UserSession newSession = new UserSession(subject);
        sessions.put(newSession.getToken(), newSession);
        return newSession.getToken();
    }

    @Override
    public void destroySession(final Subject subject) {
        sessions.entrySet().removeIf(entry -> entry.getValue().getSubject().equals(subject));
    }

    @Override
    public void destroySession(final String token) {
        sessions.entrySet().removeIf(entry -> entry.getKey().equals(token));
    }

    @Override
    public Collection<UserSession> listSessions() {
        return sessions.values();
    }

    @Override
    public Collection<UserSession> listSessions(Subject subject) {
        return sessions.values().stream().filter(entry -> entry.getSubject().equals(subject))
                .collect(Collectors.toList());
    }

}
