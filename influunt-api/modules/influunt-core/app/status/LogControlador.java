package status;

import org.apache.commons.lang3.StringUtils;
import org.jongo.Aggregate;
import org.jongo.MongoCollection;
import play.api.Play;
import uk.co.panaxiom.playjongo.PlayJongo;
import utils.TipoLogControlador;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Created by lesiopinheiro on 29/11/16.
 */
public class LogControlador {

    public static final String COLLECTION = "logs_controladores";

    public static PlayJongo jongo = Play.current().injector().instanceOf(PlayJongo.class);

    private String _id;

    private String idControlador;

    private Long timestamp;

    private String mensagem;

    private TipoLogControlador tipoLogControlador;

    public LogControlador(Map map) {
        this.idControlador = map.get("idControlador").toString();
        this.timestamp = (long) map.get("timestamp");
        this.mensagem = map.get("mensagem").toString();
        this.tipoLogControlador = TipoLogControlador.valueOf(map.get("tipoLogControlador").toString());
    }

    public LogControlador(String idControlador, Long timestamp, String mensagem, TipoLogControlador tipoLogControlador) {
        this.idControlador = idControlador;
        this.timestamp = timestamp;
        this.mensagem = mensagem;
        this.tipoLogControlador = tipoLogControlador;
    }

    public static MongoCollection logs() {
        return jongo.getCollection(COLLECTION);
    }

    public static void log(String idControlador, long timestamp, String mensagem, TipoLogControlador tipoLogControlador) {
        new LogControlador(idControlador, timestamp, mensagem, tipoLogControlador).save();
    }

    public static List<LogControlador> ultimosLogsControladores(List<String> ids) {
        ArrayList<LogControlador> list = new ArrayList<>();

        String controladoresIds = "[\"" + StringUtils.join(ids, "\",\"") + "\"]";
        Aggregate.ResultsIterator<Map> results = logs()
            .aggregate("{ $match: { idControlador: { $in: " + controladoresIds + "}}}")
            .and("{ $sort: { timestamp: -1 } }")
            .and("{ $project: { _id: 0, idControlador: 1, mensagem: 1, timestamp: 1, tipoLogControlador: 1 } }")
            .as(Map.class);

        for (Map m : results) {
            list.add(new LogControlador(m));
        }

        return list;
    }

    public void insert() {
        logs().insert(this);
    }

    private void save() {
        insert();
    }

    public String getIdControlador() {
        return idControlador;
    }

    public Long getTimestamp() {
        return timestamp;
    }

    public String getMensagem() {
        return mensagem;
    }

    public TipoLogControlador getTipoLogControlador() {
        return tipoLogControlador;
    }
}
