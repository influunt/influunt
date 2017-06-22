package status;

import org.apache.commons.lang.StringUtils;
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
        this(map.get("idControlador").toString(), (long) map.get("timestamp"), (boolean) map.get("conectado"));
        this._id = map.get("_id").toString();
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

    public static Map<String, Boolean> ultimoStatusDosControladores(List<String> ids) {
        HashMap<String, Boolean> hash = new HashMap<>();
        String controladoresIds = "[\"" + StringUtils.join(ids, "\",\"") + "\"]";
        Aggregate.ResultsIterator<Map> ultimoStatus =
            status()
                .aggregate("{ $match: { idControlador: {$in: " + controladoresIds + "} } }")
                .and("{$sort:{timestamp:-1}}")
                .and("{$group:{_id:'$idControlador', 'timestamp': {$first:'$timestamp'},'conectado': {$first:'$conectado'}}}")
                .as(Map.class);
        for (Map m : ultimoStatus) {
            hash.put(m.get("_id").toString(), (boolean) m.get("conectado"));
        }

        return hash;
    }

    public static Map<String, Object> ultimoStatusDosControladoresOfflines(List<String> ids) {
        return ultimoStatusDosControladoresPorSituacao(ids, false);
    }

    public static Map<String, Object> ultimoStatusDosControladoresOnlines(List<String> ids) {
        return ultimoStatusDosControladoresPorSituacao(ids, true);
    }

    public static StatusConexaoControlador ultimoStatus(String idControlador) {
        MongoCursor<Map> result = status().find("{ idControlador: # }", idControlador).sort("{ timestamp: -1 }").limit(1).as(Map.class);
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

    public static Integer tempoOnlineOffline(List<StatusConexaoControlador> status, boolean online) {
        if (!status.isEmpty()) {
            boolean offline = !online;
            // ordenar em ordem crescente
            status = status.stream().sorted((s1, s2) -> s1.getTimestamp().compareTo(s2.getTimestamp())).collect(Collectors.toList());

            long tempoTotalMillis = 0L;
            StatusConexaoControlador ultimoStatus = status.get(0);
            StatusConexaoControlador statusAtual;

            for (int i = 1; i < status.size(); i++) {
                statusAtual = status.get(i);
                if ((online && ultimoStatus.isConectado()) || (offline && !ultimoStatus.isConectado())) {
                    tempoTotalMillis += statusAtual.getTimestamp() - ultimoStatus.getTimestamp();
                }
                ultimoStatus = statusAtual;
            }

            if ((online && ultimoStatus.isConectado()) || (offline && !ultimoStatus.isConectado())) {
                tempoTotalMillis += DateTime.now().getMillis() - ultimoStatus.getTimestamp();
            }

            return (int) tempoTotalMillis / 1000 / 60 / 60;
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

    private static Map<String, Object> ultimoStatusDosControladoresPorSituacao(List<String> ids, Boolean online) {
        String controladoresIds = "[\"" + StringUtils.join(ids, "\",\"") + "\"]";
        //TODO: Confirmar se o last nao pega um registro aleatorio. Ele pode ser causa de inconsitencia
        Map<String, Object> hash = new HashMap<>();
        Aggregate.ResultsIterator<Map> ultimoStatus =
            status()
                .aggregate("{ $match: { idControlador: { $in: " + controladoresIds + " } } }")
                .and("{ $sort: { timestamp:-1 } }")
                .and("{ $group: { _id: '$idControlador', 'timestamp': { $first: '$timestamp' }, 'conectado': { $first: '$conectado' } } }")
                .and("{ $match: { 'conectado': " + online + "} }")
                .as(Map.class);
        for (Map m : ultimoStatus) {
            hash.put(m.get("_id").toString(), m);
        }
        return hash;
    }

    public static List<StatusConexaoControlador> ultimoStatusPorSituacao(boolean online) {
        Aggregate.ResultsIterator<Map> ultimoStatus =
            status()
                .aggregate("{ $sort: { timestamp:-1 } }")
                .and("{ $group: { _id: '$idControlador', 'timestamp': { $first: '$timestamp' }, 'conectado': { $first: '$conectado' }, 'idControlador': { $first: '$idControlador' } } }")
                .and("{ $match: { 'conectado': " + online + "} }")
                .as(Map.class);
        List<StatusConexaoControlador> statuses = new ArrayList<>();
        for (Map m : ultimoStatus) {
            statuses.add(new StatusConexaoControlador(m));
        }
        return statuses;
    }


    public boolean isConectado() {
        return conectado;
    }

    public Long getTimestamp() {
        return timestamp;
    }

    public String getIdControlador() {
        return idControlador;
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
