package security;

import be.objectify.deadbolt.java.models.Subject;
import com.avaje.ebean.Ebean;
import com.avaje.ebean.SqlUpdate;
import com.google.inject.Singleton;
import helpers.HashHelper;
import models.Sessao;
import models.Usuario;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import java.util.stream.Collectors;


@Singleton
public class DBAuthenticator implements Authenticator {

    @Override
    public Subject getSubjectByCredentials(final String login, final String password) {
        Usuario usuario = Usuario.find.byId(login);
        return usuario != null && HashHelper.checkPassword(password, usuario.getSenha()) ? usuario : null;
    }

    @Override
    public Subject getSubjectByToken(final String token) {
        Sessao sessao = Sessao.find.byId(UUID.fromString(token));
        return sessao != null ? sessao.getSubject() : null;
    }

    @Override
    public String createSession(final Subject subject) {
        Sessao newSession = new Sessao((Usuario)subject);
        newSession.save();
        return newSession.getToken();
    }

    @Override
    public void destroySession(final Subject subject) {
        SqlUpdate sql = Ebean.createSqlUpdate("DELETE FROM sessoes WHERE usuario_id = '"+ subject.getIdentifier() +"'");
        sql.execute();
    }

    @Override
    public void destroySession(final String token) {
        Sessao.find.byId(UUID.fromString(token)).delete();
    }

    @Override
    public Collection<Sessao> listSessions() {
        return Sessao.find.findList();
    }

    @Override
    public Collection<Sessao> listSessions(Subject subject) {
        return Sessao.find.where().eq("login",subject.getIdentifier()).findList();
    }

}
