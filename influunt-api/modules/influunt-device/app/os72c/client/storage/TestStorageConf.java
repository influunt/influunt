package os72c.client.storage;

import br.org.mapdb.DB;
import br.org.mapdb.DBMaker;
import com.google.inject.Singleton;

/**
 * Created by leonardo on 9/13/16.
 */
@Singleton
public class TestStorageConf implements StorageConf {

    private final DB db;

    public TestStorageConf() {
        db = DBMaker.memoryDB().make();
    }

    @Override
    public DB getDB() {
        return db;
    }
}
