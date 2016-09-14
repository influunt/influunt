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
 * Created by lesiopinheiro on 9/2/16.
 */
public class StatusConexaoControlador {

    public static final String COLLECTION = "status_conexao_controladores";

    public static PlayJongo jongo = Play.current().injector().instanceOf(PlayJongo.class);

    public String _id;

    public String idControlador;

    public Long timestamp;

    public boolean conectado;

    public StatusConexaoControlador(String idControlador, long timestamp, boolean conectado) {
        this.idControlador = idControlador;
        this.timestamp = timestamp;
        this.conectado = conectado;
    }

    public StatusConexaoControlador(Map map) {
        this.idControlador = map.get("idControlador").toString();
        this.timestamp = (long) map.get("timestamp");
        this.conectado = (boolean) map.get("conectado");
    }

    public static MongoCollection status() {
        return jongo.getCollection(COLLECTION);
    }


    public static List<StatusConexaoControlador> findByIdControlador(String idControlador) {
        return toList(status().find("{ idControlador: # }", idControlador).sort("{timestamp: -1}").as(Map.class));
    }

    public static HashMap<String, Boolean> ultimoStatusDosControladores() {
        //TODO: Confirmar se o last nao pega um registro aleatorio. Ele pode ser causa de inconsitencia
        HashMap<String, Boolean> hash = new HashMap<>();
        Aggregate.ResultsIterator<Map> ultimoStatus =
                status().aggregate("{$sort:{timestamp:-1}}").and("{$group:{_id:'$idControlador', 'timestamp': {$max:'$timestamp'},'conectado': {$first:'$conectado'}}}").
                        as(Map.class);
        for (Map m : ultimoStatus) {
            hash.put(m.get("_id").toString(), (boolean) m.get("conectado"));
        }

        return hash;
    }

    public static StatusConexaoControlador ultimoStatus(String idControlador) {
        MongoCursor<Map> result = status().find("{ idControlador: # }", idControlador).sort("{timestamp:-1}").limit(1).as(Map.class);
        if (result.hasNext()) {
            return new StatusConexaoControlador(result.next());
        } else {
            return null;
        }
    }

    public static List<StatusConexaoControlador> historico(String idControlador, int pagina, int quantidade) {
        MongoCursor<Map> result = status().find("{ idControlador: # }", idControlador).sort("{timestamp:-1}").skip(pagina * quantidade).limit(quantidade).as(Map.class);
        return toList(result);
    }


    @NotNull
    private static List<StatusConexaoControlador> toList(MongoCursor<Map> status) {
        ArrayList<StatusConexaoControlador> lista = new ArrayList<StatusConexaoControlador>(status.count());
        status.forEach(map -> {
            lista.add(new StatusConexaoControlador(map));
        });
        return lista;
    }

    public static void deleteAll() {
        status().drop();
    }

    public static void log(String idControlador, long carimboDeTempo, boolean online) {
        new StatusConexaoControlador(idControlador, carimboDeTempo, online).save();
    }

    public boolean isConectado() {
        return conectado;
    }

    public void insert() {
        status().insert(this);
    }

    private void save() {
        insert();
    }

}
