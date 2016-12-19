package status;

import org.jetbrains.annotations.NotNull;
import org.joda.time.DateTime;
import org.jongo.Aggregate;
import org.jongo.MongoCollection;
import org.jongo.MongoCursor;
import play.api.Play;
import uk.co.panaxiom.playjongo.PlayJongo;
import utils.TipoLogControlador;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * Created by lesiopinheiro on 9/2/16.
 */
public class StatusConexaoControlador {

    public static final String COLLECTION = "status_conexao_controladores";

    public static PlayJongo jongo = Play.current().injector().instanceOf(PlayJongo.class);

    private String _id;

    private String idControlador;

    private Long timestamp;

    private boolean conectado;

    public StatusConexaoControlador(String idControlador, long timestamp, boolean conectado) {
        this.idControlador = idControlador;
        this.timestamp = timestamp;
        this.conectado = conectado;
    }

    public StatusConexaoControlador(Map map) {
        this._id = map.get("_id").toString();
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

    public static List<StatusConexaoControlador> findByIdControladorUltimos30Dias(String idControlador) {
        long ultimos30Dias = DateTime.now().minusDays(30).getMillis();
        return toList(status().find("{ idControlador: #, timestamp: { $gte: # } }", idControlador, ultimos30Dias).sort("{ timestamp: -1 }").as(Map.class));
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

    public static HashMap<String, Object> ultimoStatusDosControladoresOfflines() {
        return ultimoStatusDosControladoresPorSituacao(false);
    }

    public static HashMap<String, Object> ultimoStatusDosControladoresOnlines() {
        return ultimoStatusDosControladoresPorSituacao(true);
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

    public static Integer tempoOnline(List<StatusConexaoControlador> status) {
        if (!status.isEmpty()) {
            // ordenar em ordem crescente
            status = status.stream().sorted((s1, s2) -> s1.getTimestamp().compareTo(s2.getTimestamp())).collect(Collectors.toList());

            long totalOnlineMillis = 0L;
            StatusConexaoControlador ultimoStatus = status.get(0);
            StatusConexaoControlador statusAtual;

            for (int i = 1; i < status.size(); i++) {
                statusAtual = status.get(i);
                if (ultimoStatus.isConectado()) {
                    totalOnlineMillis += statusAtual.getTimestamp() - ultimoStatus.getTimestamp();
                }
                ultimoStatus = statusAtual;
            }

            if (ultimoStatus.isConectado()) {
                totalOnlineMillis += DateTime.now().getMillis() - ultimoStatus.getTimestamp();
            }

            return (int) totalOnlineMillis / 1000 / 60 / 60;
        }

        return 0;
    }

    public static Integer tempoOffline(List<StatusConexaoControlador> status) {
        if (!status.isEmpty()) {
            // ordenar em ordem crescente
            status = status.stream().sorted((s1, s2) -> s1.getTimestamp().compareTo(s2.getTimestamp())).collect(Collectors.toList());

            long totalOfflineMillis = 0L;
            StatusConexaoControlador ultimoStatus = status.get(0);
            StatusConexaoControlador statusAtual;

            for (int i = 1; i < status.size(); i++) {
                statusAtual = status.get(i);
                if (!ultimoStatus.isConectado()) {
                    totalOfflineMillis += statusAtual.getTimestamp() - ultimoStatus.getTimestamp();
                }
                ultimoStatus = statusAtual;
            }

            if (!ultimoStatus.isConectado()) {
                totalOfflineMillis += DateTime.now().getMillis() - ultimoStatus.getTimestamp();
            }

            return (int) totalOfflineMillis / 1000 / 60 / 60;
        }

        return 0;
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
        String mensagem = online ? "Conectado" : "Desconectado";
        LogControlador.log(idControlador, carimboDeTempo, mensagem, TipoLogControlador.STATUS_CONEXAO);
    }

    private static HashMap<String, Object> ultimoStatusDosControladoresPorSituacao(Boolean online) {
        //TODO: Confirmar se o last nao pega um registro aleatorio. Ele pode ser causa de inconsitencia
        HashMap<String, Object> hash = new HashMap<>();
        Aggregate.ResultsIterator<Map> ultimoStatus =
            status().aggregate("{$sort:{timestamp:-1}}").and("{$group:{_id:'$idControlador', 'timestamp': {$first:'$timestamp'},'conectado': {$first: '$conectado'}}}").
                and("{$match: {'conectado': " + online + "}}").
                as(Map.class);
        for (Map m : ultimoStatus) {
            hash.put(m.get("_id").toString(), m);
        }
        return hash;
    }

    public boolean isConectado() {
        return conectado;
    }

    public Long getTimestamp() {
        return timestamp;
    }

    public void insert() {
        status().insert(this);
    }

    private void save() {
        insert();
    }

    @Override
    public String toString() {
        return "StatusConexaoControlador{" +
            "_id='" + _id + '\'' +
            ", idControlador='" + idControlador + '\'' +
            ", timestamp=" + timestamp +
            ", conectado=" + conectado +
            '}';
    }


}
