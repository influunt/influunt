package status;

import models.ModoOperacaoPlano;
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
 * Created by lesiopinheiro on 9/26/16.
 */
public class TrocaDePlanoControlador {

    public static final String COLLECTION = "modos_controladores";

    public static PlayJongo jongo = Play.current().injector().instanceOf(PlayJongo.class);

    public String _id;

    public String idControlador;

    public Long timestamp;

    public ModoOperacaoPlano modoOperacao;


    public TrocaDePlanoControlador(String idControlador, long timestamp, ModoOperacaoPlano modoOperacao) {
        this.idControlador = idControlador;
        this.timestamp = timestamp;
        this.modoOperacao = modoOperacao;
    }

    public TrocaDePlanoControlador(Map map) {
        this.idControlador = map.get("idControlador").toString();
        this.timestamp = (long) map.get("timestamp");
        this.modoOperacao = ModoOperacaoPlano.valueOf(map.get("modoOperacao").toString());
    }

    public static MongoCollection modos() {
        return jongo.getCollection(COLLECTION);
    }


    public static List<TrocaDePlanoControlador> findByIdControlador(String idControlador) {
        return toList(modos().find("{ idControlador: # }", idControlador).sort("{timestamp: -1}").as(Map.class));
    }

    public static HashMap<String, ModoOperacaoPlano> ultimoModoOperacaoDosControladores() {
        //TODO: Confirmar se o last nao pega um registro aleatorio. Ele pode ser causa de inconsitencia
        HashMap<String, ModoOperacaoPlano> hash = new HashMap<>();
        Aggregate.ResultsIterator<Map> ultimoStatus =
            modos().aggregate("{$sort:{timestamp:-1}}").and("{$group:{_id:'$idControlador', 'timestamp': {$max:'$timestamp'},'modoOperacao': {$first:'$modoOperacao'}}}").
                as(Map.class);
        for (Map m : ultimoStatus) {
            hash.put(m.get("_id").toString(), ModoOperacaoPlano.valueOf(m.get("modoOperacao").toString()));
        }

        return hash;
    }

    public static TrocaDePlanoControlador ultimoModoOperacao(String idControlador) {
        MongoCursor<Map> result = modos().find("{ idControlador: # }", idControlador).sort("{timestamp:-1}").limit(1).as(Map.class);
        if (result.hasNext()) {
            return new TrocaDePlanoControlador(result.next());
        } else {
            return null;
        }
    }

    public static List<TrocaDePlanoControlador> historico(String idControlador, int pagina, int quantidade) {
        MongoCursor<Map> result = modos().find("{ idControlador: # }", idControlador).sort("{timestamp:-1}").skip(pagina * quantidade).limit(quantidade).as(Map.class);
        return toList(result);
    }


    @NotNull
    private static List<TrocaDePlanoControlador> toList(MongoCursor<Map> status) {
        ArrayList<TrocaDePlanoControlador> lista = new ArrayList<TrocaDePlanoControlador>(status.count());
        status.forEach(map -> {
            lista.add(new TrocaDePlanoControlador(map));
        });
        return lista;
    }

    public static void deleteAll() {
        modos().drop();
    }

    public static void log(String idControlador, long carimboDeTempo, ModoOperacaoPlano modoOperacaoPlano) {
        new TrocaDePlanoControlador(idControlador, carimboDeTempo, modoOperacaoPlano).save();

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
