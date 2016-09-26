package status;

import models.ModoOperacaoPlano;
import org.jetbrains.annotations.NotNull;
import org.jongo.Aggregate;
import org.jongo.MongoCollection;
import org.jongo.MongoCursor;
import play.Logger;
import play.api.Play;
import uk.co.panaxiom.playjongo.PlayJongo;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by lesiopinheiro on 9/26/16.
 */
public class ModoOperacaoControlador {

    public static final String COLLECTION = "modos_controladores";

    public static PlayJongo jongo = Play.current().injector().instanceOf(PlayJongo.class);

    public String _id;

    public String idControlador;

    public Long timestamp;

    public ModoOperacaoPlano modoOperacao;


    public ModoOperacaoControlador(String idControlador, long timestamp, ModoOperacaoPlano modoOperacao) {
        this.idControlador = idControlador;
        this.timestamp = timestamp;
        this.modoOperacao = modoOperacao;
    }

    public ModoOperacaoControlador(Map map) {
        this.idControlador = map.get("idControlador").toString();
        this.timestamp = (long) map.get("timestamp");
        this.modoOperacao = ModoOperacaoPlano.valueOf(map.get("modoOperacao").toString());
    }

    public static MongoCollection modos() {
        return jongo.getCollection(COLLECTION);
    }


    public static List<ModoOperacaoControlador> findByIdControlador(String idControlador) {
        return toList(modos().find("{ idControlador: # }", idControlador).sort("{timestamp: -1}").as(Map.class));
    }

    public static HashMap<String, ModoOperacaoPlano> ultimoModoOperacaoDosControladores() {
        //TODO: Confirmar se o last nao pega um registro aleatorio. Ele pode ser causa de inconsitencia
        HashMap<String, ModoOperacaoPlano> hash = new HashMap<>();
        Aggregate.ResultsIterator<Map> ultimoStatus =
                modos().aggregate("{$sort:{timestamp:-1}}").and("{$group:{_id:'$idControlador', 'timestamp': {$max:'$timestamp'},'modoOperacao': {$first:'$modoOperacao'}}}").
                        as(Map.class);
        for (Map m : ultimoStatus) {
            Logger.error("KKKKK: " + m.get("modoOperacao").toString());
            hash.put(m.get("_id").toString(), ModoOperacaoPlano.valueOf(m.get("modoOperacao").toString()));
        }

        return hash;
    }

    public static ModoOperacaoControlador ultimoModoOperacao(String idControlador) {
        MongoCursor<Map> result = modos().find("{ idControlador: # }", idControlador).sort("{timestamp:-1}").limit(1).as(Map.class);
        if (result.hasNext()) {
            return new ModoOperacaoControlador(result.next());
        } else {
            return null;
        }
    }

    public static List<ModoOperacaoControlador> historico(String idControlador, int pagina, int quantidade) {
        MongoCursor<Map> result = modos().find("{ idControlador: # }", idControlador).sort("{timestamp:-1}").skip(pagina * quantidade).limit(quantidade).as(Map.class);
        return toList(result);
    }


    @NotNull
    private static List<ModoOperacaoControlador> toList(MongoCursor<Map> status) {
        ArrayList<ModoOperacaoControlador> lista = new ArrayList<ModoOperacaoControlador>(status.count());
        status.forEach(map -> {
            lista.add(new ModoOperacaoControlador(map));
        });
        return lista;
    }

    public static void deleteAll() {
        modos().drop();
    }

    public static void log(String idControlador, long carimboDeTempo, ModoOperacaoPlano modoOperacaoPlano) {
        new ModoOperacaoControlador(idControlador, carimboDeTempo, modoOperacaoPlano).save();

    }

    public ModoOperacaoPlano getModoOperacao() {
        return modoOperacao;
    }

    public void insert() {
        modos().insert(this);
    }

    private void save() {
        insert();
    }
}
