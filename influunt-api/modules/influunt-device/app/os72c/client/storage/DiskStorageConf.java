package os72c.client.storage;

import br.org.mapdb.DB;
import br.org.mapdb.DBMaker;
import com.google.inject.Inject;
import com.google.inject.Singleton;
import com.typesafe.config.Config;
import com.typesafe.config.ConfigFactory;

import java.io.File;

/**
 * Created by leonardo on 9/13/16.
 */
@Singleton
public class DiskStorageConf implements StorageConf {

    private final DB db;

    @Inject
    public DiskStorageConf() {
        Config configuration = ConfigFactory.load();
        File path = new File(configuration.getConfig("72c").getConfig("storage").getString("path"));

        if(!path.exists()){
            path.mkdirs();
        }

        File file = new File(path.getAbsolutePath().concat("/").concat(configuration.getConfig("72c").getConfig("storage").getString("file")));

        db = DBMaker.fileDB(file).transactionEnable().make();

    }



    @Override
    public DB getDB() {
        return db;
    }
}
