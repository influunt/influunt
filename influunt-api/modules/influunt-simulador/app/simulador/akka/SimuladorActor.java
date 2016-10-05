package simulador.akka;

import akka.actor.UntypedActor;
import com.google.gson.Gson;
import engine.EstadoGrupoBaixoNivel;
import engine.EventoMotor;
import models.Evento;
import org.apache.commons.math3.util.Pair;
import org.eclipse.paho.client.mqttv3.MqttClient;
import org.eclipse.paho.client.mqttv3.MqttConnectOptions;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.joda.time.DateTime;
import scala.concurrent.duration.Duration;
import simulador.akka.SimuladorAkka;
import simulador.parametros.ParametroSimulacao;

import java.util.concurrent.TimeUnit;

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


    public void sendEstado(DateTime timeStamp, EstadoGrupoBaixoNivel estado) {
        try {

            String msg = "{\"timestamp\":"+timeStamp.getMillis() / 1000 +",\"estado\":\""+estado.toString()+"\"}";

            client.publish("simulador/"+id+"/estado",msg.getBytes(),1,true);
        } catch (MqttException e) {
            e.printStackTrace();
        }
    }

    public void sendEvento(DateTime timeStamp, Evento evento){
        try {

            String msg = "{'timespamp':"+timeStamp.getMillis() / 1000 +",'evento':"+new Gson().toJson(evento.getTipo())+"}";

            client.publish("simulador/"+id+"/estado",msg.getBytes(),1,true);
        } catch (MqttException e) {
            e.printStackTrace();
        }

    }
}
