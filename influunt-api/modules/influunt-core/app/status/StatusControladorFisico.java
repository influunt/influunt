package status;

import models.StatusAnel;
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

    private HashMap<Integer, StatusAnel> statusAneis = new HashMap<>();

    public StatusControladorFisico(String idControlador, long timestamp,
                                   StatusDevice statusDevice, HashMap<Integer, StatusAnel> statusAneis) {
        this.idControlador = idControlador;
        this.timestamp = timestamp;
        this.statusDevice = statusDevice;
        this.statusAneis = statusAneis;
    }

    public StatusControladorFisico(Map map) {
        this.idControlador = map.get("idControlador").toString();
        this.timestamp = (long) map.get("timestamp");
        this.statusDevice = StatusDevice.valueOf(map.get("statusDevice").toString());
        this.statusAneis = (HashMap) map.get("statusAneis");
    }

    public static MongoCollection status() {
        return jongo.getCollection(COLLECTION);
    }


    public static List<StatusControladorFisico> findByIdControlador(String idControlador) {
        return toList(status().find("{ idControlador: # }", idControlador).sort("{timestamp: -1}").as(Map.class));
    }

    public static HashMap<String, HashMap> ultimoStatusDosControladores(List<String> ids) {
        String controladoresIds = "[\"" + StringUtils.join(ids, "\",\"") + "\"]";
        Aggregate.ResultsIterator<Map> ultimoStatus =
            status()
                .aggregate("{ $match: { idControlador: {$in: " + controladoresIds + "} } }")
                .and("{$sort:{timestamp:-1}}")
                .and("{$group:{_id:'$idControlador', 'timestamp': {$first:'$timestamp'},'statusDevice': {$first:'$statusDevice'}, 'statusAneis': {$first:'$statusAneis'}}}")
                .as(Map.class);

        HashMap<String, HashMap> hash = new HashMap<>();
        for (Map m : ultimoStatus) {
            hash.put(m.get("_id").toString(), (HashMap) m);
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

    public static HashMap<String, HashMap> getControladoresByStatusAnel(StatusAnel status) {
        StringBuilder matchQuery = new StringBuilder("{ $or: [");
        int numeroMaximoDeAneis = 16; // depende do modelo do controlador
        for (int i = 1; i <= numeroMaximoDeAneis; i++) {
            matchQuery.append("{ 'statusAneis.").append(i).append("': '").append(status.toString()).append("' }");
            if (i < numeroMaximoDeAneis) {
                matchQuery.append(", ");
            }
        }
        matchQuery.append("] }");

        Aggregate.ResultsIterator<Map> ultimoStatus = status()
            .aggregate("{ $sort: { timestamp: -1 } }")
            .and("{ $group: { _id: '$idControlador', 'timestamp': { $first: '$timestamp' }, 'statusDevice': { $first: '$statusDevice' }, 'statusAneis': { $first: '$statusAneis' } } }")
            .and("{ $match: " + matchQuery.toString() + " }")
            .as(Map.class);

        HashMap<String, HashMap> hash = new HashMap<>();
        for (Map m : ultimoStatus) {
            hash.put(m.get("_id").toString(), (HashMap) m);
        }

        return hash;
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

    public static void log(String idControlador, long carimboDeTempo,
                           StatusDevice statusDevice, HashMap<Integer, StatusAnel> statusAneis) {
        new StatusControladorFisico(idControlador, carimboDeTempo, statusDevice, statusAneis).save();
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
