package models;

import com.avaje.ebean.config.ServerConfig;
import com.avaje.ebean.event.ServerConfigStartup;
import com.google.inject.Inject;
import utils.InfluuntChangeLogPrepare;

public class InfluuntServerConfigStartup implements ServerConfigStartup {

    @Inject
    private InfluuntChangeLogPrepare influuntChangeLogPrepare;

    public void onStart(ServerConfig serverConfig){
        serverConfig.setChangeLogPrepare(influuntChangeLogPrepare);
        serverConfig.setDatabaseSequenceBatchSize(1);
    }
}