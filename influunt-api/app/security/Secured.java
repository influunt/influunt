package security;

import be.objectify.deadbolt.java.models.Subject;
import com.google.inject.Inject;
import play.mvc.Http.Context;
import play.mvc.Result;
import play.mvc.Security;

public class Secured extends Security.Authenticator {

    private InfluuntContextManager ctxManager;

    @Inject
    public Secured(InfluuntContextManager ctxManager) {
        this.ctxManager = ctxManager;
    }

    @Override
    public String getUsername(Context ctx) {
        Subject usuario = ctxManager.getUsuario(ctx);
        if (usuario != null) {
            return usuario.getIdentifier();
        }
        return null;
    }

    @Override
    public Result onUnauthorized(final Context ctx) {
        return unauthorized();
    }

}
