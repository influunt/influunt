package security;

import be.objectify.deadbolt.java.models.Subject;
import com.google.inject.Inject;
import models.Usuario;
import play.mvc.Http;

/**
 * Created by pedropires on 9/12/16.
 */
public class InfluuntContextManager {

    private Authenticator authenticator;

    @Inject
    public InfluuntContextManager(Authenticator authenticator) {
        this.authenticator = authenticator;
    }

    public Usuario getUsuario(Http.Context ctx) {
        Subject usuario = (Subject) ctx.args.get("user");
        if (usuario == null) {
            final String authToken = getAuthToken(ctx);
            if (authToken != null) {
                usuario = authenticator.getSubjectByToken(authToken);
            } else {
                usuario = authenticator.getSubjectByToken("");
            }

            if (usuario != null) {
                ctx.args.put("user", usuario);
            }
        }
        return (Usuario) usuario;
    }

    public String getChave(Http.Context ctx) {
        String path = ctx.args.get("ROUTE_PATTERN").toString().replace("/apiv1/", "/api/v1/");
        return ctx.args.get("ROUTE_VERB").toString() + " " + path;
    }

    public String getAuthToken(Http.Context ctx) {
        final String[] authTokenHeaderValues = ctx.request().headers().get(AuthToken.TOKEN);
        if (authTokenHeaderValues != null && authTokenHeaderValues.length == 1 && authTokenHeaderValues[0] != null) {
            return authTokenHeaderValues[0];
        }
        return null;
    }
}
