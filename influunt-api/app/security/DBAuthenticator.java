package security;


import be.objectify.deadbolt.java.models.Subject;
import com.avaje.ebean.Ebean;
import com.avaje.ebean.SqlUpdate;
import com.google.inject.Singleton;
import helpers.HashHelper;
import models.Sessao;
import models.Usuario;

import java.util.Collection;
import java.util.UUID;
import java.util.regex.Pattern;


@Singleton
public class DBAuthenticator implements Authenticator {
    private static Pattern uuidPattern = Pattern.compile("/^[0-9a-f]{8}-[0-9a-f]{4}-[1-5][0-9a-f]{3}-[89ab][0-9a-f]{3}-[0-9a-f]{12}$/i");

    @Override
    public Subject getSubjectByCredentials(final String login, final String password) {
        Usuario usuario = Usuario.find.where().ieq("login", login).findUnique();
        if(usuario != null && HashHelper.checkPassword(password, usuario.getSenha())){
            return usuario;
        }
        return  null;
    }

    @Override
    public Subject getSubjectByToken(final String token) {
        if (isUUID(token)) {
            Sessao sessao = Sessao.find.byId(UUID.fromString(token));
            return sessao != null ? sessao.getSubject() : null;
        } else {
            return null;
        }
    }

    @Override
    public String createSession(final Subject subject) {
        return createSession(subject, null);
    }

    @Override
    public String createSession(final Subject subject, final String ip) {
        Sessao newSession = new Sessao((Usuario) subject);
        newSession.setIp(ip);
        newSession.save();
        return newSession.getToken();
    }

    @Override
    public void destroySession(final Subject subject) {
        SqlUpdate sql = Ebean.createSqlUpdate("DELETE FROM sessoes WHERE usuario_id = '" + subject.getIdentifier() + "'");
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
        return Sessao.find.where().eq("login", subject.getIdentifier()).findList();
    }

    private boolean isUUID(String string) {
        try {
            UUID.fromString(string);
            return true;
        } catch (Exception ex) {
            return false;
        }
    }
}
