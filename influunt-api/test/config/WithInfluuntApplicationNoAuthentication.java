package config;

import com.google.inject.Singleton;
import play.Application;
import play.Mode;
import play.inject.guice.GuiceApplicationBuilder;
import security.AllowAllAuthenticator;
import security.Authenticator;

import java.util.Map;

import static play.inject.Bindings.bind;

/**
 * Created by pedropires on 9/8/16.
 */
public class WithInfluuntApplicationNoAuthentication extends WithInfluuntApplication {

    @SuppressWarnings({"unchecked", "rawtypes"})
    protected Application createApplication(Map configuration) {
        return new GuiceApplicationBuilder().configure(configuration)
                .overrides(bind(Authenticator.class).to(AllowAllAuthenticator.class).in(Singleton.class))
                .in(Mode.TEST).build();
    }

}
