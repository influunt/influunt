package status;

import engine.TipoEvento;
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
public class AlarmesFalhasControlador {

    public static final String COLLECTION = "alarmes_falhas_controladores";

    public static PlayJongo jongo = Play.current().injector().instanceOf(PlayJongo.class);

    public String _id;

    public String idControlador;

    public Long timestamp;

    public String tipoEvento;

    public String mensagem;

    public String evento;


    public AlarmesFalhasControlador(String idControlador, Long timestamp,
                                    TipoEvento tipoEvento, String mensagem,
                                    String evento) {
        this.idControlador = idControlador;
        this.timestamp = timestamp;
        this.tipoEvento = tipoEvento.toString();

        this.mensagem = mensagem;
        this.evento = evento;
    }

    public AlarmesFalhasControlador(String idControlador, Long timestamp,
                                    TipoEvento tipoEvento, String mensagem) {
        this.idControlador = idControlador;
        this.timestamp = timestamp;
        this.tipoEvento = tipoEvento.toString();

        this.mensagem = mensagem;
    }

    public AlarmesFalhasControlador(Map map) {
        this.idControlador = map.get("idControlador").toString();
        this.timestamp = (long) map.get("timestamp");
        this.tipoEvento = map.get("tipoEvento").toString();
        this.mensagem = map.get("mensagem").toString();
        this.evento = map.get("evento").toString();
    }

    public static MongoCollection alarmesFalhas() {
        return jongo.getCollection(COLLECTION);
    }


    public static List<AlarmesFalhasControlador> findByIdControlador(String idControlador) {
        return toList(alarmesFalhas().find("{ idControlador: # }", idControlador).sort("{timestamp: -1}").as(Map.class));
    }

    public static HashMap<String, TipoEvento> ultimosAlarmesFalhas() {
        //TODO: Confirmar se o last nao pega um registro aleatorio. Ele pode ser causa de inconsitencia
        HashMap<String, TipoEvento> hash = new HashMap<>();
        Aggregate.ResultsIterator<Map> ultimo =
            alarmesFalhas().aggregate("{$sort:{timestamp:-1}}").and("{$group:{_id:'$idControlador', 'timestamp': {$max:'$timestamp'},'tipoEvento': {$first:'$tipoEvento'}}}").
                as(Map.class);
        for (Map m : ultimo) {
            hash.put(m.get("_id").toString(), TipoEvento.valueOf(m.get("tipoEvento").toString()));
        }

        return hash;
    }

    public static HashMap<String, Object> ultimosAlarmesFalhasControladores(Integer limit) {
        //TODO: Confirmar se o last nao pega um registro aleatorio. Ele pode ser causa de inconsitencia
        HashMap<String, Object> hash = new HashMap<>();
        Aggregate query = null;
        if (limit != null) {
            query = alarmesFalhas().aggregate("{$sort:{timestamp:-1}}").and("{$group:{_id:'$idControlador', 'timestamp': {$max:'$timestamp'},'tipoEvento': {$first:'$tipoEvento'}}}").and("{$limit: " + limit + "}");
        } else {
            query = alarmesFalhas().aggregate("{$sort:{timestamp:-1}}").and("{$group:{_id:'$idControlador', 'timestamp': {$max:'$timestamp'},'tipoEvento': {$first:'$tipoEvento'}}}");
        }
        Aggregate.ResultsIterator<Map> ultimo = query.as(Map.class);
        for (Map m : ultimo) {
            hash.put(m.get("_id").toString(), m);
        }

        return hash;
    }

    public static AlarmesFalhasControlador ultimoAlarmeFalhaControlador(String idControlador) {
        MongoCursor<Map> result = alarmesFalhas().find("{ idControlador: # }", idControlador).sort("{timestamp:-1}").limit(1).as(Map.class);
        if (result.hasNext()) {
            return new AlarmesFalhasControlador(result.next());
        } else {
            return null;
        }
    }

    public static List<AlarmesFalhasControlador> historico(String idControlador, int pagina, int quantidade) {
        MongoCursor<Map> result = alarmesFalhas().find("{ idControlador: # }", idControlador).sort("{timestamp:-1}").skip(pagina * quantidade).limit(quantidade).as(Map.class);
        return toList(result);
    }


    @NotNull
    private static List<AlarmesFalhasControlador> toList(MongoCursor<Map> erros) {
        ArrayList<AlarmesFalhasControlador> lista = new ArrayList<AlarmesFalhasControlador>(erros.count());
        erros.forEach(map -> {
            lista.add(new AlarmesFalhasControlador(map));
        });
        return lista;
    }

    public static void deleteAll() {
        alarmesFalhas().drop();
    }

    public static void log(String idControlador, Long timestamp, TipoEvento tipoEvento, String mensagem, String evento) {
        new AlarmesFalhasControlador(idControlador, timestamp, tipoEvento, mensagem, evento).save();
    }

    public static void log(String idControlador, Long timestamp, TipoEvento tipoEvento, String mensagem) {
        new AlarmesFalhasControlador(idControlador, timestamp, tipoEvento, mensagem).save();
    }

    public void insert() {
        alarmesFalhas().insert(this);
    }

    private void save() {
        insert();
    }

    public TipoEvento getTipoEvento() {
        return TipoEvento.valueOf(tipoEvento);
    }
}
