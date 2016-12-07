package status;

import com.fasterxml.jackson.databind.JsonNode;
import engine.TipoEvento;
import engine.TipoEventoControlador;
import org.jetbrains.annotations.NotNull;
import org.jongo.Aggregate;
import org.jongo.MongoCollection;
import org.jongo.MongoCursor;
import play.api.Play;
import play.libs.Json;
import uk.co.panaxiom.playjongo.PlayJongo;
import utils.TipoLogControlador;

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

    private String _id;

    private String idControlador;

    private Long timestamp;

    private String idAnel;

    private boolean recuperado;

    private Long dataRecuperado;

    private JsonNode conteudo;

    public AlarmesFalhasControlador(Map map) {
        this.idControlador = map.get("idControlador").toString();
        this.timestamp = (long) map.get("timestamp");
        this.conteudo = Json.toJson(map.get("conteudo"));
        if(map.containsKey("recuperado")) {
            this.recuperado = Boolean.parseBoolean(map.get("recuperado").toString());
        }
        if(map.containsKey("dataRecuperado")) {
            this.dataRecuperado = Long.parseLong(map.get("dataRecuperado").toString());
        }

        if (map.containsKey("idAnel") && map.get("idAnel") != null) {
            this.idAnel = map.get("idAnel").toString();
        }
    }

    public AlarmesFalhasControlador(String idControlador, Long timestamp, String idAnel, JsonNode objeto) {
        this.idControlador = idControlador;
        this.timestamp = timestamp;
        this.idAnel = idAnel;
        this.conteudo = objeto;
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
            alarmesFalhas().aggregate("{$sort:{timestamp:-1}}").and("{$group:{_id:'$idControlador', 'timestamp': {$max:'$timestamp'}, 'tipoEvento': {$first: '$conteudo.tipoEvento.tipo'}}}").
                as(Map.class);
        for (Map m : ultimo) {
            hash.put(m.get("_id").toString(), TipoEvento.valueOf(m.get("tipoEvento").toString()));
        }

        return hash;
    }

    public static List<AlarmesFalhasControlador> ultimosAlarmesFalhasControladores(Integer limit) {
        ArrayList<AlarmesFalhasControlador> list = new ArrayList<>();

        ArrayList<String> predicates = new ArrayList<>();
        predicates.add("{ $match: { recuperado: false }  }");
        predicates.add("{ $sort: { timestamp: -1 } }");
        predicates.add("{ $project: { _id: 0, idControlador: 1, idAnel: 1, timestamp: 1, conteudo: 1 } }");
        if (limit != null) {
            predicates.add("{ $limit: ".concat(String.valueOf(limit)).concat("}"));
        }

        Aggregate.ResultsIterator<Map> results = alarmesFalhas().aggregate(String.join(",", predicates)).as(Map.class);

        for (Map m : results) {
            list.add(new AlarmesFalhasControlador(m));
        }

        return list;
    }

    public static AlarmesFalhasControlador ultimoAlarmeFalhaControlador(String idControlador) {
        MongoCursor<Map> result = alarmesFalhas().find("{ idControlador: # }", idControlador).sort("{timestamp:-1}").limit(1).as(Map.class);
        if (result.hasNext()) {
            return new AlarmesFalhasControlador(result.next());
        } else {
            return null;
        }
    }

    public static AlarmesFalhasControlador ultimaFalhaControlador(String idControlador, String idAnel) {
        MongoCursor<Map> result = alarmesFalhas()
            .find("{ idControlador: #, " +
                "idAnel: #, " +
                "recuperado: false, " +
                "conteudo.tipoEvento.tipoEventoControlador: # }", idControlador, idAnel, TipoEventoControlador.FALHA.toString())
            .sort("{ timestamp: -1 }")
            .limit(1).as(Map.class);
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

    public static List<AlarmesFalhasControlador> historicoFalha(String idControlador, int pagina, int quantidade) {
        MongoCursor<Map> result = alarmesFalhas()
            .find("{ idControlador: #, " +
                "conteudo.tipoEvento.tipoEventoControlador: # }", idControlador, TipoEventoControlador.FALHA.toString())
            .sort("{timestamp:-1}").skip(pagina * quantidade).limit(quantidade).as(Map.class);
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

    public static void log(Long timestamp, String idControlador, String idAnel, JsonNode objeto) {
        new AlarmesFalhasControlador(idControlador, timestamp, idAnel, objeto).save();
        LogControlador.log(idControlador, timestamp, objeto.get("descricaoEvento").asText(), TipoLogControlador.ALARME_FALHA);
    }

    public static void logRemocao(long carimboDeTempo, String idControlador, String idAnel, JsonNode jsonConteudo) {
        TipoEvento evento = TipoEvento.valueOf(jsonConteudo.get("tipoEvento").get("tipo").asText());

        String falha = evento.toString().replace("REMOCAO_", "").concat(".*");
        alarmesFalhas()
            .update("{ idControlador: #, " +
                "idAnel: #, " +
                "recuperado: false, " +
                "conteudo.tipoEvento.tipo: { $regex: # } }", idControlador, idAnel, falha)
            .multi()
            .with("{ $set: { recuperado: true , dataRecuperado: " + carimboDeTempo + "} }");

        log(carimboDeTempo, idControlador, idAnel, jsonConteudo);
        LogControlador.log(idControlador, carimboDeTempo, evento.getDescricao(), TipoLogControlador.REMOCAO_FALHA);
    }

    public void insert() {
        alarmesFalhas().insert(this);
    }

    private void save() {
        insert();
    }

    public TipoEvento getTipoEvento() {
        return TipoEvento.valueOf(conteudo.get("tipoEvento").get("tipo").asText());
    }

    public String getIdControlador() {
        return idControlador;
    }

    public String getIdAnel() {
        return idAnel;
    }

    public JsonNode getConteudo() {
        return conteudo;
    }

    public Long getTimestamp() {
        return timestamp;
    }

    public Long getDataRecuperado() {
        return dataRecuperado;
    }

    public boolean isRecuperado() {
        return recuperado;
    }
}
