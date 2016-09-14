package utils;

import com.avaje.ebean.event.changelog.ChangeLogPrepare;
import com.avaje.ebean.event.changelog.ChangeSet;
import models.Usuario;
import play.Logger;
import play.api.Play;
import play.mvc.Http;
import security.Auditoria;
import uk.co.panaxiom.playjongo.PlayJongo;

public class InfluuntChangeLogPrepare implements ChangeLogPrepare {

    @Override
    public boolean prepare(ChangeSet changes) {
        PlayJongo jongo = Play.current().injector().instanceOf(PlayJongo.class);

        final Usuario usuario;

        if (Http.Context.current() != null) {
            usuario = (Usuario) Http.Context.current().args.get("user");
        } else {
            usuario = null;
        }

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