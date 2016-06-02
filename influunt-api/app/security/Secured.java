package security;
import com.google.inject.Inject;

import be.objectify.deadbolt.java.models.Subject;
import controllers.SecurityController;
import play.mvc.Http.Context;
import play.mvc.Result;
import play.mvc.Security;
public class Secured extends Security.Authenticator {

	private Authenticator authenticator;

	@Inject
	public Secured(Authenticator authenticator) {
		this.authenticator = authenticator;
	}
	
    @Override
    public String getUsername(Context ctx) {
        String[] authTokenHeaderValues = ctx.request().headers().get(SecurityController.AUTH_TOKEN_HEADER);
        if ((authTokenHeaderValues != null) && (authTokenHeaderValues.length == 1) && (authTokenHeaderValues[0] != null)) {
            Subject usuario = authenticator.getSubjectByToken(authTokenHeaderValues[0]);
            if (usuario != null) {
                ctx.args.put("user", usuario);
                return usuario.getIdentifier();
            }
        }

        return null;
    }

    @Override
    public Result onUnauthorized(Context ctx) {
        return unauthorized();
    }

}