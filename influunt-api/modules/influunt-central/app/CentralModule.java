import com.google.inject.AbstractModule;
import play.Configuration;
import play.Environment;
import play.Logger;
import server.Central;

/**
 * Created by rodrigosol on 11/15/16.
 */
public class CentralModule extends AbstractModule {
    private final Environment environment;

    private final Configuration configuration;

    public CentralModule(Environment environment, Configuration configuration) {
        this.environment = environment;
        this.configuration = configuration;
    }

    @Override
    protected void configure() {
        if (!environment.isTest()) {
            Logger.info("Binding central");
            bind(Central.class).asEagerSingleton();
        }
    }
}
