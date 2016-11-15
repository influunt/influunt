import com.google.inject.AbstractModule;
import play.Logger;
import server.Central;

/**
 * Created by rodrigosol on 11/15/16.
 */
public class CentralModule extends AbstractModule{
    @Override
    protected void configure() {
        Logger.info("Binding central");
        bind(Central.class).asEagerSingleton();
    }
}
