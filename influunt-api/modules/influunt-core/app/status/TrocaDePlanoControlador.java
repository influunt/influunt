package status;

import com.fasterxml.jackson.databind.JsonNode;
import engine.AgendamentoTrocaPlano;
import models.ModoOperacaoPlano;
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
 * Created by lesiopinheiro on 9/26/16.
 */
public class TrocaDePlanoControlador {

    public static final String COLLECTION = "troca_planos_controladores";

    public static PlayJongo jongo = Play.current().injector().instanceOf(PlayJongo.class);

    private String _id;

    private String idControlador;

    private String idAnel;

    private Long timestamp;

    private JsonNode conteudo;


    public TrocaDePlanoControlador(long timestamp, String idControlador, String idAnel, JsonNode conteudo) {
        this.idControlador = idControlador;
        this.idAnel = idAnel;
        this.timestamp = timestamp;
        this.conteudo = conteudo;
    }

    public TrocaDePlanoControlador(Map map) {
        this.idAnel = map.get("idAnel").toString();
        this.idControlador = map.get("idControlador").toString();
        this.timestamp = (long) map.get("timestamp");
        this.conteudo = Json.toJson(map.get("conteudo"));
    }

    public static MongoCollection trocas() {
        return jongo.getCollection(COLLECTION);
    }


    public static List<TrocaDePlanoControlador> findByIdControlador(String idControlador) {
        return toList(trocas().find("{ idControlador: # }", idControlador).sort("{timestamp: -1}").as(Map.class));
    }

    public static HashMap<String, Boolean> ultimoStatusPlanoImposto() {
        HashMap<String, Boolean> hash = new HashMap<>();
        Aggregate.ResultsIterator<Map> ultimoStatus =
            trocas().aggregate("{$sort:{timestamp:-1}}").and("{$group:{_id:'$idControlador', 'timestamp': {$max:'$timestamp'}, 'hasPlanoImposto': {$first:'$conteudo.imposicaoDePlano'}}}").
                as(Map.class);
        for (Map m : ultimoStatus) {
            boolean hasPlanoImposto = m.get("hasPlanoImposto") == null ? false : Boolean.valueOf(m.get("hasPlanoImposto").toString());
            hash.put(m.get("_id").toString(), hasPlanoImposto);
        }

        return hash;
    }

    public static HashMap<String, ModoOperacaoPlano> ultimoModoOperacaoDosControladores() {
        HashMap<String, ModoOperacaoPlano> hash = new HashMap<>();
        Aggregate.ResultsIterator<Map> ultimoStatus =
            trocas().aggregate("{$sort:{timestamp:-1}}").and("{$group:{_id:'$idControlador', 'timestamp': {$max:'$timestamp'}, 'modoOperacao': {$first:'$conteudo.plano.modoOperacao'}}}").
                as(Map.class);
        for (Map m : ultimoStatus) {
            hash.put(m.get("_id").toString(), ModoOperacaoPlano.valueOf(m.get("modoOperacao").toString()));
        }

        return hash;
    }

    public static List<TrocaDePlanoControlador> ultimasTrocasDePlanosDosControladores() {
        Aggregate.ResultsIterator<Map> ultimoStatus =
            trocas().aggregate("{$sort:{timestamp:-1}}").and("{$group:{_id:'$idAnel', 'timestamp': {$max:'$timestamp'}, " +
                " idAnel: {$first: '$idAnel'}," +
                " idControlador: {$first: '$idControlador'}," +
                " conteudo: {$first: '$conteudo'}}}").
                as(Map.class);
        List<TrocaDePlanoControlador> resultado = new ArrayList<>();
        for (Map m : ultimoStatus) {
            resultado.add(new TrocaDePlanoControlador(m));
        }

        return resultado;
    }

    public static TrocaDePlanoControlador ultimaTrocaDePlanoDoControlador(String idControlador) {
        MongoCursor<Map> result = trocas().find("{ idControlador: # }", idControlador).sort("{timestamp:-1}").limit(1).as(Map.class);
        if (result.hasNext()) {
            return new TrocaDePlanoControlador(result.next());
        } else {
            return null;
        }
    }

    public static List<TrocaDePlanoControlador> historico(String idControlador, int pagina, int quantidade) {
        MongoCursor<Map> result = trocas().find("{ idControlador: # }", idControlador).sort("{timestamp:-1}").skip(pagina * quantidade).limit(quantidade).as(Map.class);
        return toList(result);
    }

    public static List<HashMap> ultimoStatusPlanoPorAnel() {
        String sortQuery = "{ $sort: {timestamp: -1} }";
        String groupQuery = "{ $group: { _id: { $concat: ['$idControlador', '-', '$conteudo.anel.posicao'] }, idControlador: { $first: '$idControlador' }, anelPosicao: { $first: '$conteudo.anel.posicao' }, hasPlanoImposto: { $first: '$conteudo.imposicaoDePlano' }, inicio: {$first: '$conteudo.momentoOriginal'}, modoOperacao: { $first: '$conteudo.plano.modoOperacao' }, planoPosicao: { $first: '$conteudo.plano.posicao' } } }";

        Aggregate.ResultsIterator<Map> ultimoStatus = trocas().aggregate(sortQuery).and(groupQuery).as(Map.class);
        List<HashMap> resultado = new ArrayList<>();
        for (Map m : ultimoStatus) {
            resultado.add((HashMap) m);
        }

        return resultado;
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
        trocas().drop();
    }

    public static void log(long carimboDeTempo, String idControlador, String idAnel, JsonNode conteudo) {
        new TrocaDePlanoControlador(carimboDeTempo, idControlador, idAnel, conteudo).save();
        LogControlador.log(idControlador, carimboDeTempo, conteudo.get("descricaoEvento").asText(), TipoLogControlador.TROCA_PLANO);
    }

    public ModoOperacaoPlano getModoOperacao() {
        return ModoOperacaoPlano.valueOf(conteudo.get("plano").get("modoOperacao").asText());
    }

    public AgendamentoTrocaPlano getTrocaDePlano() {
        return Json.fromJson(conteudo, AgendamentoTrocaPlano.class);
    }

    public String getIdControlador() {
        return this.idControlador;
    }

    public String getIdAnel() {
        return this.idAnel;
    }

    public JsonNode getConteudo() {
        return this.conteudo;
    }

    public void insert() {
        trocas().insert(this);
    }

    private void save() {
        insert();
    }
}
