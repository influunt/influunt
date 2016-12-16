package simulador.akka;

import akka.actor.UntypedActor;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import engine.EventoMotor;
import engine.IntervaloGrupoSemaforico;
import models.Evento;
import models.TipoDetector;
import models.simulador.parametros.ParametroSimulacao;
import org.apache.commons.math3.util.Pair;
import org.eclipse.paho.client.mqttv3.MqttClient;
import org.eclipse.paho.client.mqttv3.MqttConnectOptions;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.fusesource.mqtt.client.QoS;
import org.joda.time.DateTime;
import play.libs.Json;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import static play.libs.Json.newObject;

/**
 * Created by rodrigosol on 10/4/16.
 */
public class SimuladorActor extends UntypedActor {

    private final static int SEGUNDOS_POR_PAGINA = 256;

    private final ParametroSimulacao params;

    private final SimuladorAkka simulador;

    private final String id;

    private MqttClient client;

    private HashMap<Integer, List<Pair<DateTime, IntervaloGrupoSemaforico>>> estagios = new HashMap();

    private List<ArrayNode> trocasDePlanos = new ArrayList<>();

    private List<ArrayNode> alarmes = new ArrayList<>();

    private List<ArrayNode> modoManual = new ArrayList<>();

    private String jsonTrocas;

    private StringBuffer bufferTrocaDePlanos = null;

    private int pagina;

    public SimuladorActor(String host, String port, String login, String senha, ParametroSimulacao params) {
        this.params = params;
        this.simulador = new SimuladorAkka(this, params);
        this.id = params.getId().toString();

        try {
            client = new MqttClient("tcp://" + host + ":" + port, "sim_" + id);
            MqttConnectOptions opts = new MqttConnectOptions();

            if (!"".equals(login)) { opts.setUserName(login); }
            if (!"".equals(senha)) { opts.setPassword(senha.toCharArray()); }

            opts.setAutomaticReconnect(false);
            opts.setConnectionTimeout(10);
            opts.setWill("simulador/" + id + "/morreu", "morreu".getBytes(), QoS.AT_LEAST_ONCE.ordinal(), false);

            client.connect(opts);
            client.subscribe("simulador/" + id + "/proxima_pagina", 1, (topic, message) -> {
                JsonNode root = Json.parse(message.getPayload());
                proximaPagina(root.get("pagina").asInt());
            });

            client.subscribe("simulador/" + id + "/detector", 1, (topic, message) -> {
                JsonNode root = Json.parse(message.getPayload());
                TipoDetector td = TipoDetector.valueOf(root.get("tipo").asText());
                int posicao = root.get("posicao").asInt();
                int anel = root.get("anel").asInt();
                DateTime disparo = new DateTime(root.get("disparo").asLong());
                detectorAcionador(anel, td, disparo, posicao);
            });

            client.subscribe("simulador/" + id + "/alternar_modo_manual", 1, (topic, message) -> {
                JsonNode root = Json.parse(message.getPayload());
                DateTime disparo = new DateTime(root.get("disparo").asLong());
                boolean ativar = root.get("ativarModoManual").asBoolean();

                alternarModoManual(disparo, ativar);
            });

            client.subscribe("simulador/" + id + "/trocar_estagio", 1, (topic, message) -> {
                JsonNode root = Json.parse(message.getPayload());
                DateTime disparo = new DateTime(root.get("disparo").asLong());

                trocarEstagioModoManual(disparo);
            });

        } catch (MqttException e) {
            e.printStackTrace();
        }


    }

    private void alternarModoManual(DateTime disparo, boolean ativar) throws Exception {
        simulador.alternarModoManual(disparo, ativar);
        proximaPagina(disparo);
    }

    private void trocarEstagioModoManual(DateTime disparo) throws Exception {
        simulador.trocarEstagioModoManual(disparo);
        proximaPagina(disparo);
    }

    private void detectorAcionador(int anel, TipoDetector tipoDetector, DateTime disparo, int detector) throws Exception {
        simulador.detectorAcionador(anel, tipoDetector, disparo, detector);
        proximaPagina(disparo);
    }

    private void proximaPagina(DateTime disparo) throws Exception {
        final int diff = ((Long) ((disparo.getMillis() - params.getInicioControlador().getMillis()) / 1000)).intValue();
        final int pagina = diff / SEGUNDOS_POR_PAGINA;
        proximaPagina(pagina);
    }

    private void proximaPagina(int pagina) throws Exception {
        this.pagina = pagina;
        DateTime inicio = params.getInicioControlador().plusSeconds(pagina * SEGUNDOS_POR_PAGINA);
        DateTime fim = inicio.plusSeconds(SEGUNDOS_POR_PAGINA);
        simulador.simular(fim);
        send();
    }

    @Override
    public void onReceive(Object message) throws Exception {

    }


    public void storeEstagio(int anel, DateTime timeStamp, IntervaloGrupoSemaforico intervaloGrupoSemaforico) {
        if (!estagios.containsKey(anel)) {
            estagios.put(anel, new ArrayList<>());
        }
        estagios.get(anel).add(new Pair<DateTime, IntervaloGrupoSemaforico>(timeStamp, intervaloGrupoSemaforico));
    }


    public void send() {
        try {
            client.publish("simulador/" + id + "/estado", getJson().getBytes(), 1, false);
            estagios.clear();
            trocasDePlanos.clear();
            alarmes.clear();
            modoManual.clear();
            bufferTrocaDePlanos = null;
        } catch (MqttException e) {
            e.printStackTrace();
        }
    }

    public String getJson() {
        ObjectNode root = newObject();
        ObjectNode aneis = root.putObject("aneis");

        estagios.keySet().stream().forEach(key -> {
            ArrayNode anelArray = aneis.putArray(key.toString());
            estagios.get(key).stream().forEach(e -> {
                anelArray.add(e.getSecond().toJson(e.getFirst().minus(params.getInicioControlador().getMillis())));
            });
        });

        ArrayNode trocas = root.putArray("trocas");
        trocasDePlanos.forEach(troca -> trocas.add(troca));

        ArrayNode alarmesNode = root.putArray("alarmes");
        alarmes.forEach(alarme -> alarmesNode.add(alarme));

        ArrayNode manual = root.putArray("manual");
        modoManual.forEach(modo -> manual.add(modo));

        return root.toString();
    }


    public void storeTrocaDePlano(DateTime timestamp, Evento eventoAnterior, Evento eventoAtual, List<String> modos) {
        ArrayNode troca = Json.newArray();

        troca.add(timestamp.getMillis());

        if (eventoAnterior != null) {
            troca.add(eventoAnterior.getPosicaoPlano());
        } else {
            troca.add("null");
        }
        troca.add(eventoAtual.getPosicaoPlano());
        ArrayNode modosJson = Json.newArray();
        modos.forEach(modo -> modosJson.add(modo));
        troca.add(modosJson);

        trocasDePlanos.add(troca);

    }

    public void storeAlarme(DateTime timestamp, EventoMotor eventoMotor) {
        ArrayNode alarme = Json.newArray();

        alarme.add(timestamp.getMillis());
        alarme.add(eventoMotor.getTipoEvento().getCodigo());
        alarme.add(eventoMotor.getTipoEvento().toString());
        alarme.add(eventoMotor.getTipoEvento().getMessage(eventoMotor.getStringParams()));
        alarmes.add(alarme);

    }

    public void ativaModoManual(DateTime timestamp) {
        ArrayNode manual = Json.newArray();
        manual.add("ATIVAR");
        manual.add(timestamp.getMillis());
        modoManual.add(manual);
    }

    public void desativaModoManual(DateTime timestamp) {
        ArrayNode manual = Json.newArray();
        manual.add("DESATIVAR");
        manual.add(timestamp.getMillis());
        modoManual.add(manual);
    }

    public DateTime getPagina() {
        return params.getInicioControlador().plusSeconds(pagina * SEGUNDOS_POR_PAGINA);
    }
}
