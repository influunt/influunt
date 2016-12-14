package utils;

import com.avaje.ebean.event.changelog.ChangeLogPrepare;
import com.avaje.ebean.event.changelog.ChangeSet;
import models.Usuario;
import play.Configuration;
import play.api.Play;
import play.mvc.Http;
import security.Auditoria;
import uk.co.panaxiom.playjongo.PlayJongo;

public class InfluuntChangeLogPrepare implements ChangeLogPrepare {

    @Override
    public boolean prepare(ChangeSet changes) {
        Configuration configuration = Play.current().injector().instanceOf(Configuration.class);
        boolean gravarAuditoria = configuration.getConfig("auditoria").getBoolean("enabled");
        if (gravarAuditoria) {
            gravarAuditoria(changes);
        }
        return false;
    }

    private void gravarAuditoria(ChangeSet changes) {
        PlayJongo jongo = Play.current().injector().instanceOf(PlayJongo.class);

        Usuario usuario = null;
        try {
            if (Http.Context.current() != null) {
                usuario = (Usuario) Http.Context.current().args.get("user");
            }
        } catch (RuntimeException ignored) {

        }

        final Usuario finalUsuario = usuario;
        changes.getChanges().forEach(change -> {
            change.getValues().remove("dataCriacao");
            change.getValues().remove("dataAtualizacao");
            Auditoria auditoria = new Auditoria();
            auditoria.change = change;
            if (finalUsuario != null) {
                auditoria.usuario = finalUsuario;
            }
            jongo.getCollection("auditorias").insert(auditoria);
        });
    }


}
