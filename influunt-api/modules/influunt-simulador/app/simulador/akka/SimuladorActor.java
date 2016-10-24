package simulador.akka;

import akka.actor.UntypedActor;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import engine.IntervaloGrupoSemaforico;
import models.Evento;
import models.simulador.parametros.ParametroSimulacao;
import org.apache.commons.math3.util.Pair;
import org.eclipse.paho.client.mqttv3.MqttClient;
import org.eclipse.paho.client.mqttv3.MqttConnectOptions;
import org.eclipse.paho.client.mqttv3.MqttException;
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

    private final static int SEGUNDOS_POR_PAGINA = 300;

    private final ParametroSimulacao params;

    private final SimuladorAkka simulador;

    private final String id;

    private MqttClient client;

    private int pagina = 0;

    private HashMap<Integer, List<Pair<DateTime, IntervaloGrupoSemaforico>>> estagios = new HashMap();

    private List<ArrayNode> trocasDePlanos = new ArrayList<>();

    private String jsonTrocas;

    private StringBuffer bufferTrocaDePlanos = null;


    public SimuladorActor(String host, String port, ParametroSimulacao params) {
        this.params = params;
        this.simulador = new SimuladorAkka(this, params);
        this.id = params.getId().toString();

        try {
            client = new MqttClient("tcp://" + host + ":" + port, "sim_" + id);
            MqttConnectOptions opts = new MqttConnectOptions();
            opts.setAutomaticReconnect(false);
            opts.setConnectionTimeout(10);
            opts.setWill("simulador/" + id + "/morreu", "1".getBytes(), 1, true);
            client.connect(opts);
            client.subscribe("simulador/" + id + "/proxima_pagina", 1, (topic, message) -> {
                proximaPagina();
            });

            client.publish("simulador/" + id + "/pronto", "1".getBytes(), 1, true);
            proximaPagina();

        } catch (MqttException e) {
            e.printStackTrace();
        }


    }

    private void proximaPagina() {
        DateTime inicio = params.getInicioSimulacao().plusSeconds(pagina * SEGUNDOS_POR_PAGINA);
        DateTime fim = inicio.plusSeconds(SEGUNDOS_POR_PAGINA);
        simulador.simular(inicio, fim);
        pagina++;
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
            client.publish("simulador/" + id + "/estado", getJson().getBytes(), 1, true);
            estagios.clear();
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
                 anelArray.add(e.getSecond().toJson(e.getFirst().minus(params.getInicioSimulacao().getMillis())));
            });
        });
        ArrayNode trocas = root.putArray("trocas");
        trocasDePlanos.forEach(troca -> trocas.add(troca));


        return root.toString();
    }


    public void storeTrocaDePlano(DateTime timestamp, Evento eventoAnterior, Evento eventoAtual) {
        ArrayNode troca = Json.newArray();

        troca.add(timestamp.getMillis());

        if(eventoAnterior!=null) {
            troca.add(eventoAnterior.getPosicaoPlano());
        }else{
            troca.add("null");
        }
        troca.add(eventoAtual.getPosicaoPlano());
        trocasDePlanos.add(troca);

    }
}
