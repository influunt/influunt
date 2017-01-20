package controllers;

import com.avaje.ebean.Ebean;
import com.avaje.ebean.EbeanServer;
import com.avaje.ebean.config.DataSourceConfig;
import com.avaje.ebean.config.ServerConfig;
import com.avaje.ebean.dbmigration.DdlGenerator;
import com.avaje.ebeaninternal.api.SpiEbeanServer;
import com.google.inject.Inject;
import com.google.inject.Provider;
import play.Application;
import play.db.ebean.Transactional;
import play.mvc.Controller;
import play.mvc.Result;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CompletionStage;

public class CucumberController extends Controller {

    @Inject
    private Provider<Application> provider;

    @Transactional
    public CompletionStage<Result> createDB() {
        if (provider.get().isProd()) {
            return CompletableFuture.completedFuture(notFound());
        }

        DataSourceConfig dsconfig = new DataSourceConfig();
        dsconfig.setDriver("org.h2.Driver");
        dsconfig.setUrl("jdbc:h2:mem:influunt;DATABASE_TO_UPPER=FALSE");
        dsconfig.setUsername("sa");
        dsconfig.setPassword("");

        ServerConfig config = new ServerConfig();
        config.setDataSourceJndiName("TestDS");
        config.setDataSourceConfig(dsconfig);
        config.setDdlGenerate(true);
        config.setDdlCreateOnly(false);
        config.setDdlRun(true);
        EbeanServer server = Ebean.getServer("default");
        DdlGenerator ddl = new DdlGenerator((SpiEbeanServer) server, config);
        ddl.execute(true);
        return CompletableFuture.completedFuture(ok());
    }

}
