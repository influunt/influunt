package simulador.akka;

import akka.actor.UntypedActor;
import engine.IntervaloGrupoSemaforico;
import models.simulador.parametros.ParametroSimulacao;
import org.apache.commons.math3.util.Pair;
import org.eclipse.paho.client.mqttv3.MqttClient;
import org.eclipse.paho.client.mqttv3.MqttConnectOptions;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.joda.time.DateTime;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by rodrigosol on 10/4/16.
 */
public class SimuladorActor extends UntypedActor {

    private final static int SEGUNDOS_POR_PAGINA = 120;

    private final ParametroSimulacao params;

    private final SimuladorAkka simulador;

    private final String id;

    private MqttClient client;

    private int pagina = 0;

    private HashMap<Integer, List<Pair<DateTime, IntervaloGrupoSemaforico>>> estagios = new HashMap();


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

        StringBuffer buffer = new StringBuffer("{\"estados\":[");
        try {
//            String estados = estadoBatch.entrySet()
//                    .stream().map(e -> {
//                        return e.getValue().toJson(e.getKey());
//                    }).collect(Collectors.joining(","));
//
//            buffer.append(estados).append("],\"eventos\":[");
//
//
//            String eventos = eventosBatch.stream()
//                    .map(EventoLog::toJson)
//                    .collect(Collectors.joining(","));
//
//            buffer.append(eventos).append("]}");


            client.publish("simulador/" + id + "/estado", getJson().getBytes(), 1, true);
        } catch (MqttException e) {
            e.printStackTrace();
        }
    }

    public String getJson() {

        StringBuffer sb = new StringBuffer("\"estagios\":{");
        String sbAnel = estagios.keySet().stream().map(key -> {

            String buffer = estagios.get(key).stream().map(e -> {
                System.out.println(e.getFirst().minus(params.getInicioSimulacao().getMillis()).getMillis());
                return e.getSecond().toJson(e.getFirst().minus(params.getInicioSimulacao().getMillis()));
            }).collect(Collectors.joining(",")) + "]";

            return "\"" + key.toString() + "\":[" + buffer;
        }).collect(Collectors.joining(","));

        return "{\"aneis\":{" + sbAnel.toString() + "}}";
    }

}
