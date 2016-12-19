package status;

import models.StatusDevice;
import org.apache.commons.lang.StringUtils;
import org.jetbrains.annotations.NotNull;
import org.jongo.Aggregate;
import org.jongo.MongoCollection;
import org.jongo.MongoCursor;
import play.api.Play;
import uk.co.panaxiom.playjongo.PlayJongo;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by lesiopinheiro on 9/2/16.
 */
public class StatusControladorFisico {

    public static final String COLLECTION = "status_controladores";

    public static PlayJongo jongo = Play.current().injector().instanceOf(PlayJongo.class);

    private String _id;

    private String idControlador;

    private Long timestamp;

    private StatusDevice statusDevice;

    public StatusControladorFisico(String idControlador, long timestamp, StatusDevice statusDevice) {
        this.idControlador = idControlador;
        this.timestamp = timestamp;
        this.statusDevice = statusDevice;
    }

    public StatusControladorFisico(Map map) {
        this.idControlador = map.get("idControlador").toString();
        this.timestamp = (long) map.get("timestamp");
        this.statusDevice = StatusDevice.valueOf(map.get("statusDevice").toString());
    }

    public static MongoCollection status() {
        return jongo.getCollection(COLLECTION);
    }


    public static List<StatusControladorFisico> findByIdControlador(String idControlador) {
        return toList(status().find("{ idControlador: # }", idControlador).sort("{timestamp: -1}").as(Map.class));
    }

    public static HashMap<String, StatusDevice> ultimoStatusDosControladores(List<String> ids) {
        String controladoresIds = "[\"" + StringUtils.join(ids, "\",\"") + "\"]";
        HashMap<String, StatusDevice> hash = new HashMap<>();
        Aggregate.ResultsIterator<Map> ultimoStatus =
            status()
                .aggregate("{ $match: { idControlador: {$in: " + controladoresIds + "} } }")
                .and("{$sort:{timestamp:-1}}")
                .and("{$group:{_id:'$idControlador', 'timestamp': {$first:'$timestamp'},'statusDevice': {$first:'$statusDevice'}}}")
                .as(Map.class);
        for (Map m : ultimoStatus) {
            hash.put(m.get("_id").toString(), StatusDevice.valueOf(m.get("statusDevice").toString()));
        }

        return hash;
    }

    public static StatusControladorFisico ultimoStatus(String idControlador) {
        MongoCursor<Map> result = status().find("{ idControlador: # }", idControlador).sort("{timestamp:-1}").limit(1).as(Map.class);
        if (result.hasNext()) {
            return new StatusControladorFisico(result.next());
        } else {
            return null;
        }
    }

    public static List<StatusControladorFisico> historico(String idControlador, int pagina, int quantidade) {
        MongoCursor<Map> result = status().find("{ idControlador: # }", idControlador).sort("{timestamp:-1}").skip(pagina * quantidade).limit(quantidade).as(Map.class);
        return toList(result);
    }


    @NotNull
    private static List<StatusControladorFisico> toList(MongoCursor<Map> status) {
        ArrayList<StatusControladorFisico> lista = new ArrayList<StatusControladorFisico>(status.count());
        status.forEach(map -> {
            lista.add(new StatusControladorFisico(map));
        });
        return lista;
    }

    public static void deleteAll() {
        status().drop();
    }

    public static void log(String idControlador, long carimboDeTempo, StatusDevice statusDevice) {
        new StatusControladorFisico(idControlador, carimboDeTempo, statusDevice).save();
    }

    public StatusDevice getStatusDevice() {
        return statusDevice;
    }

    public void insert() {
        status().insert(this);
    }

    private void save() {
        insert();
    }

}
