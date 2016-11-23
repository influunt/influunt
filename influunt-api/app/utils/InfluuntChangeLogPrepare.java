package utils;

import com.avaje.ebean.event.changelog.ChangeLogPrepare;
import com.avaje.ebean.event.changelog.ChangeSet;
import models.Usuario;
import play.api.Play;
import play.mvc.Http;
import security.Auditoria;
import uk.co.panaxiom.playjongo.PlayJongo;

public class InfluuntChangeLogPrepare implements ChangeLogPrepare {

    @Override
    public boolean prepare(ChangeSet changes) {
        PlayJongo jongo = Play.current().injector().instanceOf(PlayJongo.class);

        Usuario usu = null;
        try {
            if (Http.Context.current() != null) {
                usu = (Usuario) Http.Context.current().args.get("user");
            }
        } catch (RuntimeException e) {
        }


        final Usuario usuario = usu;

        changes.getChanges().forEach(change -> {
            change.getValues().remove("dataCriacao");
            change.getValues().remove("dataAtualizacao");
            Auditoria auditoria = new Auditoria();
            auditoria.change = change;
            if (usuario != null) {
                auditoria.usuario = usuario;
            }

            jongo.getCollection("auditorias").insert(auditoria);
        });

        return false;
    }


}
