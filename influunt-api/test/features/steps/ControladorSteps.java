package features.steps;

import com.avaje.ebeaninternal.server.lib.util.Str;
import com.fasterxml.jackson.databind.JsonNode;
import config.WithInfluuntApplicationNoAuthentication;
import cucumber.api.DataTable;
import cucumber.api.PendingException;
import cucumber.api.java.Before;
import cucumber.api.java.pt.Dado;
import cucumber.api.java.pt.Então;
import cucumber.api.java.pt.Quando;
import engine.EventoMotor;
import engine.TipoEvento;
import json.ControladorCustomDeserializer;
import models.*;
import org.apache.commons.io.IOUtils;
import org.apache.commons.math3.util.Pair;
import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;
import org.junit.Assert;
import simulacao.SimuladorTest;
import simulador.Simulador;
import simulador.eventos.AlteracaoEstadoLog;
import simulador.eventos.EventoLog;
import simulador.eventos.TipoEventoLog;
import simulador.parametros.ParametroSimulacao;


import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static junit.framework.TestCase.assertNotNull;
import static org.junit.Assert.assertEquals;
import static play.test.Helpers.inMemoryDatabase;

/**
 * Created by rodrigosol on 9/27/16.
 */
public class ControladorSteps extends WithInfluuntApplicationNoAuthentication {


    private static Map<String, Controlador> controladores = new HashMap<>();

    private static Controlador controlador;

    private static SimuladorTest simulador;
    private static ParametroSimulacao parametros = new ParametroSimulacao();

    @Before
    public void setUp() {
        Map<String, String> dbOptions = new HashMap<String, String>();
        dbOptions.put("DATABASE_TO_UPPER", "FALSE");
        Map<String, String> abstractAppOptions = inMemoryDatabase("default", dbOptions);
        Map<String, String> appOptions = new HashMap<String, String>();
        appOptions.put("db.default.driver", abstractAppOptions.get("db.default.driver"));
        appOptions.put("db.default.url", abstractAppOptions.get("db.default.url"));
        appOptions.put("play.evolutions.db.default.enabled", "false");
        appOptions.put("central.mqtt.host", "127.0.0.1");
        appOptions.put("central.mqtt.port", "1883");
        provideApp = getApplication(appOptions);
        controladores = new HashMap<>();
        controlador = null;
    }

    @Dado("^que exista o controlador \"([^\"]*)\"$")
    public void que_exista_o_controlador(String controladorId) throws Throwable {

        JsonNode controladorJson = play.libs.Json.parse(readControladorJson(controladorId));
        controladores.put(controladorId, new ControladorCustomDeserializer().getControladorFromJson(controladorJson));
        assertNotNull(controladores.get(controladorId));
    }

    @Dado("^que o controlador \"([^\"]*)\" seja escolhido$")
    public void que_o_controlador_seja_escolhido(String controladorId) throws Throwable {
        controlador = controladores.get(controladorId);
    }


    @Dado("^que o controlador esta em execucao desdes \"([^\"]*)\"$")
    public void que_o_controlador_esta_em_execucao_desdes(String data) throws Throwable {
        parametros.setInicioControlador(parseDate(data));
    }

    @Dado("^que a simulacao vai comecar em \"([^\"]*)\"$")
    public void que_a_simulacao_vai_comecar_em(String data) throws Throwable {
        parametros.setInicioSimulacao(parseDate(data));
    }

    @Dado("^que a simulacao vai terminar em \"([^\"]*)\"$")
    public void que_a_simulacao_vai_terminar_em(String data) throws Throwable {
        parametros.setFimSimulacao(parseDate(data));
    }

    @Dado("^que o detector veicular (\\d+) seja acionado em \"([^\"]*)\"$")
    public void que_o_detector_veicular_seja_acionado_em(int detector, String data) throws Throwable {
        simulador.addEvento(parseDetector(TipoDetector.VEICULAR, detector, data));
    }

    @Dado("^que o detector de pedestre (\\d+) seja acionado em \"([^\"]*)\"$")
    public void que_o_detector_de_pedestre_seja_acionado_em(int detector, String data) throws Throwable {
        simulador.addEvento(parseDetector(TipoDetector.PEDESTRE, detector, data));
    }


    @Dado("^que a falha (\\d+) aconteca em \"([^\"]*)\" no \"([^\"]*)\"$")
    public void que_a_falha_aconteca_em_no(int falha, String data, String param) throws Throwable {
        simulador.addEvento(parseFalha(falha, data, false, param));
    }

    @Dado("^que a falha (\\d+) cesse em \"([^\"]*)\"  no \"([^\"]*)\"$")
    public void que_a_falha_cesse_em_no(int falha, String data, String params) throws Throwable {
        simulador.addEvento(parseFalha(falha, data, true, params));
    }


    @Dado("^que o alarme (\\d+) aconteca em \"([^\"]*)\"$")
    public void que_o_alarme_aconteca_em(int alarme, String data) throws Throwable {
        simulador.addEvento(parseAlarme(alarme, data));
    }

    @Dado("^que o plano (\\d+) seja imposto em \"([^\"]*)\"$")
    public void que_o_plano_seja_imposto_em(int plano, String data) throws Throwable {
        simulador.addEvento(parseImposicaoPlano(plano, data));
    }


    @Quando("^a simulacao terminar$")
    public void a_simulacao_terminar() throws Throwable {
        simulador = new SimuladorTest(parametros.getInicioControlador(), controlador, parametros);
        simulador.simular(parametros.getInicioControlador(), parametros.getFimSimulacao());
    }

    @Então("^a simulacao tera duracao de (\\d+) segundos$")
    public void a_simulacao_tera_duracao_de_segundos(int duracao) throws Throwable {
        assertEquals(duracao,simulador.getTempoSimulacao());
    }

    @Dado("^que esse cenario ainda nao foi implementado$")
    public void que_esse_cenario_ainda_nao_foi_implementado() throws Throwable {
        // Write code here that turns the phrase above into concrete actions
        throw new PendingException();
    }

    @Então("^que houveram as seguintes trocas de planos:$")
    public void que_houveram_as_seguintes_trocas_de_planos(DataTable dataTable) throws Throwable {
        List<List<String>> rows = dataTable.asLists(String.class);

        int i = 0;
        for(List<String> row : rows){
            if(i > 0) {
                int planoAnterior = Integer.valueOf(row.get(0));
                int planoAtual = Integer.valueOf(row.get(1));
                DateTime timestamp = parseDate(row.get(2));

                checkMudancaDePlano(planoAnterior, planoAtual, timestamp);
            }
            i++;
        }
    }

    @Então("^que houveram as seguintes trocas de planos reais:$")
    public void que_houveram_as_seguintes_trocas_de_planos_reais(DataTable dataTable) throws Throwable {
        List<List<String>> rows = dataTable.asLists(String.class);

        int i = 0;
        for(List<String> row : rows){
            if(i > 0) {
                int planoAnterior = Integer.valueOf(row.get(0));
                int planoAtual = Integer.valueOf(row.get(1));
                DateTime timestamp = parseDate(row.get(2));

                checkMudancaDePlanoReal(planoAnterior, planoAtual, timestamp);
            }
            i++;
        }
    }

    @Então("^houveram as seguintes trocas de estados dos grupos semaforicos:$")
    public void houveram_as_seguintes_trocas_de_estados_dos_grupos_semaforicos(DataTable dataTable) throws Throwable {
        // Write code here that turns the phrase above into concrete actions
        // For automatic transformation, change DataTable to one of
        // List<YourType>, List<List<E>>, List<Map<K,V>> or Map<K,V>.
        // E,K,V must be a scalar (String, Integer, Date, enum etc)
        List<List<String>> rows = dataTable.asLists(String.class);
        int i = 0;
        for(List<String> row : rows){
            if(i > 0) {
                DateTime timestamp = parseDate(row.get(0));
                List<EstadoGrupoSemaforico> estado = parserEstadoGrupoSemaforico(1,row);
                checkMudancaDeEstado(timestamp,estado);
            }
            i++;
        }

    }


    private List<EstadoGrupoSemaforico> parserEstadoGrupoSemaforico(int i, List<String> row) {
        List<EstadoGrupoSemaforico> result = new ArrayList<>();
        for(i = 1; i < row.size(); i++){
            result.add(EstadoGrupoSemaforico.valueOf(row.get(i)));
        }
        return result;
    }

    private void checkMudancaDePlano(int planoAnterior, int planoAtual, DateTime timestamp) {
        Pair<Evento, Evento> item = simulador.getListaEvento().get(timestamp);
        assertEquals("Plano antigo está correto", item.getKey().getPosicao().intValue(), planoAnterior);
        assertEquals("Plano novo está correto", item.getValue().getPosicao().intValue(), planoAtual);
    }

    private void checkMudancaDePlanoReal(int planoAnterior, int planoAtual, DateTime timestamp) {
        Pair<Integer, Integer> item = simulador.getListaEventoReal().get(timestamp);
        assertEquals("Plano antigo está correto", item.getKey().intValue(), planoAnterior);
        assertEquals("Plano novo está correto", item.getValue().intValue(), planoAtual);
    }

    private void checkMudancaDeEstado(DateTime timestamp, List<EstadoGrupoSemaforico> estado) {
        simulador.getListaEstadoGrupoSemaforico().get(timestamp);

    }

    private byte[] readControladorJson(String controladorId) throws IOException {
        return IOUtils.toByteArray(this.getClass().getResourceAsStream(("./../simulacao/specs/controladores/" + controladorId + ".json")));
    }

    private DateTime parseDate(String date) {
        DateTimeFormatter formatter = DateTimeFormat.forPattern("dd/MM/yyyy HH:mm:ss");
        return formatter.parseDateTime(date);
    }

    private EventoMotor parseFalha(int falha, String data, boolean recuperacao, String params) {
        return new EventoMotor(parseDate(data), TipoEvento.getFalha(falha), parseParams(params), recuperacao);
    }

    private Object[] parseParams(String params) {
        return params.split(";");
    }

    private EventoMotor parseAlarme(int alarme, String data) {
        return new EventoMotor(parseDate(data), TipoEvento.getAlarme(alarme));
    }

    private EventoMotor parseDetector(TipoDetector tipoDetector, int detector, String data) {
        return new EventoMotor(parseDate(data), TipoEvento.getDetector(tipoDetector), getDetector(tipoDetector, detector));
    }

    private EventoMotor parseImposicaoPlano(int plano, String data) {
        return new EventoMotor(parseDate(data), TipoEvento.IMPOSICAO_PLANO, getPlano(plano));
    }

    private List<Plano> getPlano(int plano) {
        return simulador.getPlano(plano);
    }

    private Detector getDetector(TipoDetector tipoDetector, int detector) {
        return simulador.getDetector(tipoDetector, detector);
    }


}
