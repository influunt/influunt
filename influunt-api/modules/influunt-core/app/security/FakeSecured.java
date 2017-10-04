package security;

import be.objectify.deadbolt.java.models.Subject;
import com.google.inject.Inject;
import play.mvc.Http.Context;
import play.mvc.Result;
import play.mvc.Security;

public class FakeSecured extends Security.Authenticator {

    private Authenticator authenticator;

    @Inject
    public FakeSecured(Authenticator authenticator) {
        this.authenticator = authenticator;
    }

    @Override
    public String getUsername(Context ctx) {
        Subject usuario = authenticator.getSubjectByToken("");
        if (usuario != null) {
            ctx.args.put("user", usuario);
            return usuario.getIdentifier();
        }
        return null;
    }

    @Override
    public Result onUnauthorized(final Context ctx) {
        return unauthorized();
    }

}