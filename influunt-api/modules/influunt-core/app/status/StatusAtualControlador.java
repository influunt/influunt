package status;

import org.jongo.Aggregate;
import org.jongo.Find;
import org.jongo.FindOne;
import org.jongo.MongoCollection;
import play.api.Play;
import uk.co.panaxiom.playjongo.PlayJongo;

import java.util.HashMap;
import java.util.Map;


/**
 * Created by brunaseewald on 14/2/2018.
 */

public class StatusAtualControlador {

    public static final String COLLECTION = "status_atual_controlador";

    public static PlayJongo jongo = Play.current().injector().instanceOf(PlayJongo.class);

    public static MongoCollection status() {
        return jongo.getCollection(COLLECTION);
    }

    public static void deleteAll() {
        status().drop();
    }

    public static void log() {
        new StatusAtualControlador().save();
    }

    public void insert() {
        status().insert(this);
    }

    public FindOne findOne(String statusControlador) {
        return status().findOne(statusControlador);
    }

    public Find find(){
        return status().find();
    }

    public long count(){
        return status().count();
    }

    public Aggregate aggregate(String query){
        return status().aggregate(query);
    }

    public void update(String query, String parameters){
        status().update(query).upsert().with(parameters);
    }

    private void save() {
        insert();
    }

}

