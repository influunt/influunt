package status;

import com.google.gson.Gson;
import models.MotivoFalhaControlador;
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
public class ErrosControlador {

    public static final String COLLECTION = "erros_controladores";

    public static PlayJongo jongo = Play.current().injector().instanceOf(PlayJongo.class);

    public String _id;

    public String idControlador;

    public String idFabricante;

    public Long timestamp;

    public MotivoFalhaControlador motivoFalhaControlador;

    public ErrosControlador(String idControlador, long timestamp, MotivoFalhaControlador motivoFalhaControlador, String idFabricante) {
        this.idControlador = idControlador;
        this.timestamp = timestamp;
        this.motivoFalhaControlador = motivoFalhaControlador;
        this.idFabricante = idFabricante;
    }

    public ErrosControlador(Map map) {
        this.idControlador = map.get("idControlador").toString();
        this.timestamp = (long) map.get("timestamp");
        this.motivoFalhaControlador = MotivoFalhaControlador.valueOf(map.get("motivoFalhaControlador").toString());
        this.idFabricante = map.get("idFabricante").toString();
    }

    public static MongoCollection erros() {
        return jongo.getCollection(COLLECTION);
    }


    public static List<ErrosControlador> findByIdControlador(String idControlador) {
        return toList(erros().find("{ idControlador: # }", idControlador).sort("{timestamp: -1}").as(Map.class));
    }

    public static HashMap<String, MotivoFalhaControlador> ultimosErrosDosControladores() {
        //TODO: Confirmar se o last nao pega um registro aleatorio. Ele pode ser causa de inconsitencia
        HashMap<String, MotivoFalhaControlador> hash = new HashMap<>();
        Aggregate.ResultsIterator<Map> ultimoErro =
                erros().aggregate("{$sort:{timestamp:-1}}").and("{$group:{_id:'$idControlador', 'timestamp': {$max:'$timestamp'},'motivoFalhaControlador': {$first:'$motivoFalhaControlador'}}}").
                        as(Map.class);
        for (Map m : ultimoErro) {
            hash.put(m.get("_id").toString(), MotivoFalhaControlador.valueOf(m.get("motivoFalhaControlador").toString()));
        }

        return hash;
    }

    public static HashMap<String, Object> ultimosErrosDosControladoresPorErro(Integer limit) {
        //TODO: Confirmar se o last nao pega um registro aleatorio. Ele pode ser causa de inconsitencia
        HashMap<String, Object> hash = new HashMap<>();
        Aggregate query = null;
        if (limit != null) {
            query = erros().aggregate("{$sort:{timestamp:-1}}").and("{$group:{_id:'$idControlador', 'timestamp': {$max:'$timestamp'},'motivoFalhaControlador': {$first:'$motivoFalhaControlador'}}}").and("{$limit: " + limit + "}");
        } else {
            query = erros().aggregate("{$sort:{timestamp:-1}}").and("{$group:{_id:'$idControlador', 'timestamp': {$max:'$timestamp'},'motivoFalhaControlador': {$first:'$motivoFalhaControlador'}}}");
        }
        Aggregate.ResultsIterator<Map> ultimoErro = query.as(Map.class);
        for (Map m : ultimoErro) {
            hash.put(m.get("_id").toString(), m);
        }

        return hash;
    }

    public static ErrosControlador ultimoErroControlador(String idControlador) {
        MongoCursor<Map> result = erros().find("{ idControlador: # }", idControlador).sort("{timestamp:-1}").limit(1).as(Map.class);
        if (result.hasNext()) {
            return new ErrosControlador(result.next());
        } else {
            return null;
        }
    }

    public static List<ErrosControlador> historico(String idControlador, int pagina, int quantidade) {
        MongoCursor<Map> result = erros().find("{ idControlador: # }", idControlador).sort("{timestamp:-1}").skip(pagina * quantidade).limit(quantidade).as(Map.class);
        return toList(result);
    }


    @NotNull
    private static List<ErrosControlador> toList(MongoCursor<Map> erros) {
        ArrayList<ErrosControlador> lista = new ArrayList<ErrosControlador>(erros.count());
        erros.forEach(map -> {
            lista.add(new ErrosControlador(map));
        });
        return lista;
    }

    public static void deleteAll() {
        erros().drop();
    }

    public static void log(String idControlador, long carimboDeTempo, MotivoFalhaControlador motivoFalhaControlador, String idFabricante) {
        new ErrosControlador(idControlador, carimboDeTempo, motivoFalhaControlador, idFabricante).save();

    }

    public MotivoFalhaControlador getMotivoFalhaControlador() {
        return motivoFalhaControlador;
    }

    public void insert() {
        erros().insert(this);
    }

    private void save() {
        insert();
    }


    public static List<String> errosPorFabricante() {
        ArrayList<String> resultado = new ArrayList<>();
        Aggregate query = erros().aggregate("{$group:{_id:{idFabricante: '$idFabricante','status': '$motivoFalhaControlador'}, total:{$sum:1}}}, {$sort:{'total':-1}}");
        Aggregate.ResultsIterator<Map> ultimoErro = query.as(Map.class);
        Gson gson = new Gson();
        for (Map m : ultimoErro) {
            resultado.add(gson.toJson(m));
        }
        return resultado;
    }
}
