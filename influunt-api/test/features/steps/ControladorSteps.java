package features.steps;

import com.fasterxml.jackson.databind.JsonNode;
import config.WithInfluuntApplicationNoAuthentication;
import cucumber.api.java.Before;
import cucumber.api.java.pt.Dado;
import cucumber.api.java.pt.Quando;
import engine.EventoMotor;
import engine.TipoEvento;
import json.ControladorCustomDeserializer;
import models.Controlador;
import models.Detector;
import models.Plano;
import models.TipoDetector;
import org.apache.commons.io.IOUtils;
import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;
import simulacao.Simulador;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static junit.framework.TestCase.assertNotNull;
import static play.test.Helpers.inMemoryDatabase;

/**
 * Created by rodrigosol on 9/27/16.
 */
public class ControladorSteps extends WithInfluuntApplicationNoAuthentication {


    private static Map<String, Controlador> controladores = new HashMap<>();

    private static Controlador controlador;

    private static Simulador simulador;

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
        simulador = new Simulador();
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
        simulador.setControlador(controlador);
    }


    @Dado("^que o controlador esta em execucao desdes \"([^\"]*)\"$")
    public void que_o_controlador_esta_em_execucao_desdes(String data) throws Throwable {
        simulador.setDataInicioControlador(parseDate(data));
    }

    @Dado("^que a simulacao vai comecar em \"([^\"]*)\"$")
    public void que_a_simulacao_vai_comecar_em(String data) throws Throwable {
        simulador.setInicio(parseDate(data));
    }

    @Dado("^que a simulacao vai terminar em \"([^\"]*)\"$")
    public void que_a_simulacao_vai_terminar_em(String data) throws Throwable {
        simulador.setFim(parseDate(data));
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
        simulador.simular();
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
