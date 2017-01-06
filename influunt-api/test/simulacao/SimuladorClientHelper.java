package simulacao;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.JsonNodeFactory;
import com.fasterxml.jackson.databind.node.ObjectNode;
import org.eclipse.paho.client.mqttv3.*;
import org.fusesource.mqtt.client.QoS;
import play.Logger;
import play.libs.Json;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by rodrigosol on 1/5/17.
 */
public class SimuladorClientHelper {

    private final String simuladorId;

    private MqttClient mqttClient;

    private List<JsonNode> estados = new ArrayList<>();


    public SimuladorClientHelper(String simuladorId) throws MqttException {
        this.simuladorId = simuladorId;
        connectClient();
    }


    private void connectClient() throws MqttException {
        try {
            mqttClient = new MqttClient("tcp://localhost:1883", "sim_client_" + simuladorId);
        } catch (Exception e) {
            e.printStackTrace();
            throw e;
        }


        MqttConnectOptions opts = new MqttConnectOptions();

        opts.setAutomaticReconnect(false);
        opts.setCleanSession(false);
        opts.setConnectionTimeout(0);

        mqttClient.setCallback(new MqttCallback() {
            @Override
            public void connectionLost(Throwable cause) {
                Logger.info("ConectionLost");
            }

            @Override
            public void messageArrived(String topic, MqttMessage message) throws Exception {
                Logger.info("MessageArrived");
            }

            @Override
            public void deliveryComplete(IMqttDeliveryToken token) {
                Logger.info("deliveryComplete");
            }
        });
        mqttClient.connect(opts);

        mqttClient.subscribe("simulador/" + simuladorId + "/estado", QoS.EXACTLY_ONCE.ordinal(), new IMqttMessageListener() {

            @Override
            public void messageArrived(String topic, MqttMessage message) throws Exception {
                estados.add(Json.parse(message.getPayload()));
            }
        });
    }


    public void buscarPagina(int pagina) throws MqttException {
        ObjectNode root = JsonNodeFactory.instance.objectNode();
        root.put("pagina", pagina);
        send("simulador/" + simuladorId + "/proxima_pagina", root.toString());
    }

    public List<JsonNode> getEstados() {
        return estados;
    }

    public void setEstados(List<JsonNode> estados) {
        this.estados = estados;
    }

    public void dispararDetector(int anel, long momentoDisparo, int posicao, String tipo) throws MqttException {
        ObjectNode root = JsonNodeFactory.instance.objectNode();

        root.put("anel", anel);
        root.put("disparo", momentoDisparo);
        root.put("posicao", posicao);
        root.put("tipo", tipo);
        send("simulador/" + simuladorId + "/detector", root.toString());

    }

    public void toggleModoManual(long momentoDisparo, boolean ativar) throws MqttException {
        ObjectNode root = JsonNodeFactory.instance.objectNode();
        root.put("disparo", momentoDisparo);
        root.put("ativarModoManual", ativar);
        send("simulador/" + simuladorId + "/alternar_modo_manual", root.toString());
    }

    public void trocaEstagio(long momentoDisparo) throws MqttException {
        ObjectNode root = JsonNodeFactory.instance.objectNode();
        root.put("disparo", momentoDisparo);
        send("simulador/" + simuladorId + "/trocar_estagio", root.toString());
    }

    private void send(String topic, String msg) throws MqttException {
        MqttMessage message = new MqttMessage();
        message.setQos(QoS.EXACTLY_ONCE.ordinal());
        message.setRetained(false);
        message.setPayload(msg.getBytes());
        mqttClient.publish(topic, message);
    }

}
