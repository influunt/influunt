package status;

import org.jongo.Find;
import org.jongo.FindOne;
import org.jongo.MongoCollection;
import play.api.Play;
import uk.co.panaxiom.playjongo.PlayJongo;

import java.util.HashMap;
import java.util.List;


/**
 * Created by brunaseewald on 22/6/2018.
 */

public class AneisControlador {

    public static final String COLLECTION = "aneis_por_controlador";

    public static PlayJongo jongo = Play.current().injector().instanceOf(PlayJongo.class);

    public static MongoCollection status() {
        return jongo.getCollection(COLLECTION);
    }

    public static void deleteAll() {
        status().drop();
    }

    public void insert() {
        status().insert(this);
    }

    public FindOne findOne() {
        return status().findOne();
    }

    public FindOne findOne(String query) {
        return status().findOne(query);
    }

    public Find find() {
        return status().find();
    }

    public long count() {
        return status().count();
    }

    public void update(String query, String parameters) {
        status().update(query).upsert().with(parameters);
    }
}
