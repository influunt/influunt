import be.objectify.deadbolt.java.models.Subject;
import models.Sessao;
import models.Usuario;
import security.Authenticator;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

public class TestAuthenticator implements Authenticator {

    final private Map<String, Sessao> sessions = new HashMap<String, Sessao>();

    @Override
    public Subject getSubjectByCredentials(String user, String password) {
        if ("admin".equals(user) && "1234".equals(password)) {
            Usuario u = new Usuario();
            u.setLogin("admin");
            u.setNome("Administrator");
            return u;
        }
        return null;
    }

    @Override
    public Subject getSubjectByToken(final String token) {
        if ("1234".equals(token)) {
            Usuario u = new Usuario();
            u.setLogin("admin");
            u.setNome("Administrator");
            return u;
        }
        return null;
    }

    @Override
    public String createSession(final Subject subject) {
        Sessao newSession = new Sessao((Usuario) subject);
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
    public Collection<Sessao> listSessions() {
        return sessions.values();
    }

    @Override
    public Collection<Sessao> listSessions(final Subject subject) {
        return sessions.values().stream().filter(entry -> entry.getSubject().equals(subject))
                .collect(Collectors.toList());
    }
}
