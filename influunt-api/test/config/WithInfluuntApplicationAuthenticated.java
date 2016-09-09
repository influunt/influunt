package config;

import play.Application;
import play.Mode;
import play.inject.guice.GuiceApplicationBuilder;

import java.util.Map;

/**
 * Created by pedropires on 9/8/16.
 */
public class WithInfluuntApplicationAuthenticated extends WithInfluuntApplication {

    @SuppressWarnings({"unchecked", "rawtypes"})
    protected Application createApplication(Map configuration) {
        return new GuiceApplicationBuilder().configure(configuration)
                .in(Mode.TEST).build();
    }

}
