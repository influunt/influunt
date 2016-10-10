package simulador.akka;

import akka.actor.UntypedActor;
import engine.EstadoGrupoBaixoNivel;
import org.eclipse.paho.client.mqttv3.MqttClient;
import org.eclipse.paho.client.mqttv3.MqttConnectOptions;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.joda.time.DateTime;
import simulador.eventos.EventoLog;
import simulador.parametros.ParametroSimulacao;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by rodrigosol on 10/4/16.
 */
public class SimuladorActor extends UntypedActor{

    private final ParametroSimulacao params;
    private final SimuladorAkka simulador;
    private MqttClient client;
    private final String id;
    private int pagina = 0;
    private final static int SEGUNDOS_POR_PAGINA = 120;
    private final static int ESTADO_BATCH_SIZE = 120;
    private HashMap<DateTime,EstadoGrupoBaixoNivel> estadoBatch = new HashMap<>();
    private List<EventoLog> eventosBatch = new ArrayList<>();


    public SimuladorActor(String host, String port,ParametroSimulacao params){
        this.params = params;
        this.simulador = new SimuladorAkka(this,params);
        this.id = params.getId().toString();

        try {
            client = new MqttClient("tcp://" + host + ":" + port, "sim_" + id);
            MqttConnectOptions opts = new MqttConnectOptions();
            opts.setAutomaticReconnect(false);
            opts.setConnectionTimeout(10);
            opts.setWill("simulador/"+id+"/morreu", "1".getBytes(), 1, true);
            client.connect(opts);
            client.subscribe("simulador/"+id+"/proxima_pagina", 1, (topic, message) -> {
                proximaPagina();
            });

            client.publish("simulador/"+id+"/pronto","1".getBytes(),1,true);
            proximaPagina();

        } catch (MqttException e) {
            e.printStackTrace();
        }



    }

    private void proximaPagina() {
        DateTime inicio = params.getInicioSimulacao().plusSeconds(pagina * SEGUNDOS_POR_PAGINA);
        DateTime fim = inicio.plusSeconds(SEGUNDOS_POR_PAGINA);
        simulador.simular(inicio,fim);
        pagina++;
    }

    @Override
    public void onReceive(Object message) throws Exception {
    }


    public void storeEstado(DateTime timeStamp, EstadoGrupoBaixoNivel estado) {
        estadoBatch.put(timeStamp,estado);
        if(estadoBatch.size() >= ESTADO_BATCH_SIZE){
            enviaEstadoBatch();
            estadoBatch.clear();
            eventosBatch.clear();
        }
    }

    private void enviaEstadoBatch() {
        StringBuffer buffer = new StringBuffer("{\"estados\":[");
        try {
            String estados = estadoBatch.entrySet()
                                        .stream().map(e -> {
                                            return e.getValue().toJson(e.getKey());
                                      }).collect(Collectors.joining(","));

            buffer.append(estados).append("],\"eventos\":[");


            String eventos = eventosBatch.stream()
                                         .map(EventoLog::toJson)
                                         .collect(Collectors.joining(","));

            buffer.append(eventos).append("]}");


            client.publish("simulador/"+id+"/estado",buffer.toString().getBytes(),1,true);
        } catch (MqttException e) {
            e.printStackTrace();
        }

    }

    public void storeEvento(EventoLog evento){
        eventosBatch.add(evento);
    }
}
