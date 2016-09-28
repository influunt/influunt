package config;

import com.avaje.ebean.Ebean;
import com.avaje.ebean.EbeanServer;
import com.avaje.ebean.config.ServerConfig;
import com.avaje.ebean.dbmigration.DdlGenerator;
import com.avaje.ebeaninternal.api.SpiEbeanServer;
import org.junit.Before;
import play.Application;
import play.mvc.Http;
import play.test.WithApplication;

import java.util.HashMap;
import java.util.Map;

import static play.test.Helpers.fakeRequest;
import static play.test.Helpers.inMemoryDatabase;

/**
 * Created by pedropires on 9/8/16.
 */
public abstract class WithInfluuntApplication extends WithApplication {


    public Application provideApp;

    @Override
    protected Application provideApplication() {
        Map<String, String> dbOptions = new HashMap<String, String>();
        dbOptions.put("DATABASE_TO_UPPER", "FALSE");
        Map<String, String> abstractAppOptions = inMemoryDatabase("default", dbOptions);
        Map<String, String> appOptions = new HashMap<String, String>();
        appOptions.put("db.default.driver", abstractAppOptions.get("db.default.driver"));
        appOptions.put("db.default.url", abstractAppOptions.get("db.default.url"));
        appOptions.put("play.evolutions.db.default.enabled", "false");
        appOptions.put("central.mqtt.host", "127.0.0.1");
        appOptions.put("central.mqtt.port", "1883");
        provideApp = getApplication(appOptions);
        return provideApp;
    }


    @Before
    public void setupContext() {
        Http.Context context = new Http.Context(fakeRequest());
        context.args.put("user", null);
        Http.Context.current.set(context);
    }


    protected Application getApplication(Map configuration) {
        Application application = createApplication(configuration);
        buildDatabase();
        return application;
    }

    protected abstract Application createApplication(Map configuration);

    private void buildDatabase() {
        ServerConfig config = new ServerConfig();
        config.setDdlGenerate(true);
        config.setDdlCreateOnly(false);
        config.setDdlRun(true);
        EbeanServer server = Ebean.getServer("default");
        DdlGenerator ddl = new DdlGenerator((SpiEbeanServer) server, config);
        ddl.execute(true);
    }
}
