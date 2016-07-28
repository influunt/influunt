package security;

import be.objectify.deadbolt.java.models.Subject;
import com.google.inject.Singleton;
import models.Sessao;
import models.Usuario;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;


@Singleton
public class AllowAllAuthenticator implements Authenticator {

    private Map<String, Sessao> sessions = new HashMap<String, Sessao>();

    @Override
    public Subject getSubjectByCredentials(final String login, final String password) {

        return getUsuario();
    }

    private Subject getUsuario() {
        Usuario usuario = new Usuario();
        usuario.setLogin("test");
        usuario.setNome("Usuario de Teste");
        usuario.setRoot(true);
        usuario.setEmail("test@influunt.com.br");
        return usuario;
    }

    @Override
    public Subject getSubjectByToken(final String token) {
        return getUsuario();
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
    public Collection<Sessao> listSessions(Subject subject) {
        return sessions.values().stream().filter(entry -> entry.getSubject().equals(subject))
                .collect(Collectors.toList());
    }

}
