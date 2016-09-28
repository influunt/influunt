package status;

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
 * Created by lesiopinheiro on 9/27/16.
 */
public class ImposicaoPlanosControlador {

    public static final String COLLECTION = "imposicao_plano_controladores";

    public static PlayJongo jongo = Play.current().injector().instanceOf(PlayJongo.class);

    public String _id;

    public String idControlador;

    public Long timestamp;

    public boolean planoImposto;

    public ImposicaoPlanosControlador(String idControlador, long timestamp, boolean planoImposto) {
        this.idControlador = idControlador;
        this.timestamp = timestamp;
        this.planoImposto = planoImposto;
    }

    public ImposicaoPlanosControlador(Map map) {
        this.idControlador = map.get("idControlador").toString();
        this.timestamp = (long) map.get("timestamp");
        this.planoImposto = (boolean) map.get("planoImposto");
    }

    public static MongoCollection status() {
        return jongo.getCollection(COLLECTION);
    }


    public static List<ImposicaoPlanosControlador> findByIdControlador(String idControlador) {
        return toList(status().find("{ idControlador: # }", idControlador).sort("{timestamp: -1}").as(Map.class));
    }

    public static HashMap<String, Boolean> ultimoStatusPlanoImpostoDosControladoresOff() {
        return ultimoStatusPlanoImpostoDosControladoresPorSituacao(false);
    }

    public static HashMap<String, Boolean> ultimoStatusPlanoImpostoDosControladoresOn() {
        return ultimoStatusPlanoImpostoDosControladoresPorSituacao(true);
    }

    public static HashMap<String, Boolean> ultimoStatusPlanoImpostoDosControladores() {
        //TODO: Confirmar se o last nao pega um registro aleatorio. Ele pode ser causa de inconsitencia
        HashMap<String, Boolean> hash = new HashMap<>();
        Aggregate.ResultsIterator<Map> ultimoStatus =
                status().aggregate("{$sort:{timestamp:-1}}").and("{$group:{_id:'$idControlador', 'timestamp': {$max:'$timestamp'},'planoImposto': {$first:'$planoImposto'}}}").
                        as(Map.class);
        for (Map m : ultimoStatus) {
            hash.put(m.get("_id").toString(), (boolean) m.get("planoImposto"));
        }

        return hash;
    }

    public static ImposicaoPlanosControlador ultimoStatusPlanoImposto(String idControlador) {
        MongoCursor<Map> result = status().find("{ idControlador: # }", idControlador).sort("{timestamp:-1}").limit(1).as(Map.class);
        if (result.hasNext()) {
            return new ImposicaoPlanosControlador(result.next());
        } else {
            return null;
        }
    }

    public static List<ImposicaoPlanosControlador> historico(String idControlador, int pagina, int quantidade) {
        MongoCursor<Map> result = status().find("{ idControlador: # }", idControlador).sort("{timestamp:-1}").skip(pagina * quantidade).limit(quantidade).as(Map.class);
        return toList(result);
    }


    @NotNull
    private static List<ImposicaoPlanosControlador> toList(MongoCursor<Map> status) {
        ArrayList<ImposicaoPlanosControlador> lista = new ArrayList<ImposicaoPlanosControlador>(status.count());
        status.forEach(map -> {
            lista.add(new ImposicaoPlanosControlador(map));
        });
        return lista;
    }

    public static void deleteAll() {
        status().drop();
    }

    public static void log(String idControlador, long carimboDeTempo, Boolean planoImposto) {
        new ImposicaoPlanosControlador(idControlador, carimboDeTempo, planoImposto).save();

    }

    public Boolean isPlanoImposto() {
        return planoImposto;
    }

    public void insert() {
        status().insert(this);
    }

    private void save() {
        insert();
    }

    private static HashMap<String, Boolean> ultimoStatusPlanoImpostoDosControladoresPorSituacao(Boolean planoImposto) {
        //TODO: Confirmar se o last nao pega um registro aleatorio. Ele pode ser causa de inconsitencia
        HashMap<String, Boolean> hash = new HashMap<>();
        Aggregate.ResultsIterator<Map> ultimoStatus =
                status().aggregate("{$sort:{timestamp:-1}}").and("{$match: {'planoImposto': " + planoImposto + "}}").and("{$group:{_id:'$idControlador', 'timestamp': {$max:'$timestamp'},'planoImposto': {$first: '$planoImposto'}}}").
                        as(Map.class);
        for (Map m : ultimoStatus) {
            hash.put(m.get("_id").toString(), (boolean) m.get("planoImposto"));
        }
        return hash;
    }
}
