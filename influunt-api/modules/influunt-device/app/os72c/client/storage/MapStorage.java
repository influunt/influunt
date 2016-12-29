package os72c.client.storage;

import br.org.mapdb.DB;
import br.org.mapdb.HTreeMap;
import br.org.mapdb.Serializer;
import com.fasterxml.jackson.databind.JsonNode;
import com.google.inject.Inject;
import com.google.inject.Singleton;
import engine.CausaERemocaoEvento;
import engine.EventoMotor;
import engine.TipoEvento;
import json.ControladorCustomDeserializer;
import json.ControladorCustomSerializer;
import models.Controlador;
import models.StatusAnel;
import models.StatusDevice;
import os72c.client.observer.EstadoDevice;
import play.api.Play;
import protocol.Envelope;

import java.util.*;

/**
 * Created by leonardo on 9/13/16.
 */
@Singleton
public class MapStorage implements Storage {

    private final DB db;

    private final HTreeMap<String, String> status;

    private final HTreeMap<Integer, String> statusAneis;

    private final HTreeMap<String, String> controlador;

    private final HTreeMap<String, String> keys;

    private final HTreeMap<String, String> params;

    private final HTreeMap<String, String> dadosHardware;

    private final HTreeMap<String, Integer> falhas;

    private final HTreeMap<String, Map<String, String>> tempData;

    private final HTreeMap<String, Envelope> envelopes;

    private final EstadoDevice estadoDevice = Play.current().injector().instanceOf(EstadoDevice.class);

    @Inject
    public MapStorage(StorageConf storageConf) {
        this.db = storageConf.getDB();
        this.status = this.db.hashMap("status")
            .keySerializer(Serializer.STRING)
            .valueSerializer(Serializer.STRING)
            .layout(1, 1, 1)
            .createOrOpen();

        this.statusAneis = this.db.hashMap("statusAneis")
            .keySerializer(Serializer.INTEGER)
            .valueSerializer(Serializer.STRING)
            .layout(1, 2, 1)
            .createOrOpen();

        if (!this.status.containsKey("status")) {
            this.status.put("status", StatusDevice.NOVO.toString());
            db.commit();
        }

        estadoDevice.setStatus(this.getStatus());

        this.keys = this.db.hashMap("keys")
            .keySerializer(Serializer.STRING)
            .valueSerializer(Serializer.STRING)
            .layout(1, 1, 1)
            .createOrOpen();

        this.controlador = this.db.hashMap("controladores")
            .keySerializer(Serializer.STRING)
            .valueSerializer(Serializer.STRING)
            .layout(1, 2, 1)
            .createOrOpen();

        this.params = this.db.hashMap("params")
            .keySerializer(Serializer.STRING)
            .valueSerializer(Serializer.STRING)
            .layout(1, 2, 1)
            .createOrOpen();

        this.dadosHardware = this.db.hashMap("dadosHardware")
            .keySerializer(Serializer.STRING)
            .valueSerializer(Serializer.STRING)
            .layout(1, 2, 1)
            .createOrOpen();

        this.falhas = this.db.hashMap("falhas")
            .keySerializer(Serializer.STRING)
            .valueSerializer(Serializer.INTEGER)
            .layout(1, 2, 1)
            .createOrOpen();

        this.tempData = this.db.hashMap("tempData")
            .keySerializer(Serializer.STRING)
            .valueSerializer(Serializer.JAVA)
            .layout(1, 2, 1)
            .createOrOpen();

        this.envelopes = this.db.hashMap("envelopes")
            .keySerializer(Serializer.STRING)
            .valueSerializer(Serializer.JAVA)
            .layout(1, 2, 1)
            .createOrOpen();

        if (!this.params.containsKey("horarioEntradaTabelaHoraria")) {
            this.params.put("horarioEntradaTabelaHoraria", "-1");
            db.commit();
        }
    }

    @Override
    public StatusDevice getStatus() {
        return StatusDevice.valueOf(this.status.get("status"));
    }

    @Override
    public void setStatus(StatusDevice statusDevice) {
        this.status.put("status", statusDevice.toString());
        db.commit();

        estadoDevice.setStatus(statusDevice);
    }

    @Override
    public void setStatusAnel(Integer anel, StatusAnel statusAnel) {
        this.statusAneis.put(anel, statusAnel.toString());
        db.commit();

        estadoDevice.setStatusAnel(anel, statusAnel);
    }

    @Override
    public void setStatusAneis(StatusAnel statusAnel) {
        atualizarStatusAneis(statusAnel);

        db.commit();

        estadoDevice.setStatusAneis(statusAnel);
    }

    @Override
    public StatusAnel getStatusAnel(Integer anel) {
        return StatusAnel.valueOf(this.statusAneis.get(anel.toString()));
    }

    @Override
    public HashMap<Integer, StatusAnel> getStatusAneis() {
        HashMap<Integer, StatusAnel> status = new HashMap<>();
        this.statusAneis.getEntries().stream().forEach(entry -> {
            status.put(entry.getKey(),
                StatusAnel.valueOf(entry.getValue()));
        });
        return status;
    }

    @Override
    public Controlador getControlador() {
        if (this.controlador.containsKey("atual")) {
            return new ControladorCustomDeserializer().getControladorFromJson(play.libs.Json.parse(this.controlador.get("atual")));
        }
        return null;
    }

    @Override
    public void setControlador(Controlador controlador) {
        this.controlador.put("atual", new ControladorCustomSerializer().getControladorJson(controlador, Collections.singletonList(controlador.getArea().getCidade()), controlador.getRangeUtils()).toString());
        db.commit();
    }

    @Override
    public Controlador getControladorStaging() {
        if (this.controlador.containsKey("temp") && !this.controlador.get("temp").equals("")) {
            return new ControladorCustomDeserializer().getControladorFromJson(play.libs.Json.parse(this.controlador.get("temp")));
        }
        return null;
    }

    @Override
    public void setControladorStaging(Controlador controlador) {
        String controladorStr = "";
        if (controlador != null) {
            controladorStr = new ControladorCustomSerializer().getControladorJson(controlador, Collections.singletonList(controlador.getArea().getCidade()), controlador.getRangeUtils()).toString();
        }
        this.controlador.put("temp", controladorStr);
        db.commit();
    }

    @Override
    public JsonNode getControladorJson() {
        if (this.controlador.containsKey("atual")) {
            return play.libs.Json.parse(this.controlador.get("atual"));
        }
        return null;
    }

    @Override
    public JsonNode getControladorJsonStaging() {
        if (this.controlador.containsKey("temp")) {
            return play.libs.Json.parse(this.controlador.get("temp"));
        }
        return null;
    }

    @Override
    public JsonNode getPlanos() {
        if (this.controlador.containsKey("atualPlanos")) {
            return play.libs.Json.parse(this.controlador.get("atualPlanos"));
        }
        return null;
    }

    @Override
    public void setPlanos(JsonNode plano) {
        this.controlador.put("atualPlanos", plano.toString());
        db.commit();
    }

    @Override
    public JsonNode getPlanosStaging() {
        if (this.controlador.containsKey("tempPlanos")) {
            return play.libs.Json.parse(this.controlador.get("tempPlanos"));
        }
        return null;
    }

    @Override
    public void setPlanosStaging(JsonNode plano) {
        this.controlador.put("tempPlanos", plano.toString());
        db.commit();
    }


    @Override
    public String getPrivateKey() {
        return this.keys.get("private");
    }

    @Override
    public void setPrivateKey(String privateKey) {
        this.keys.put("private", privateKey);
        db.commit();
    }

    @Override
    public String getCentralPublicKey() {
        return this.keys.get("central");
    }

    @Override
    public void setCentralPublicKey(String publicKey) {
        this.keys.put("central", publicKey);
        db.commit();
    }

    @Override
    public long getHorarioEntradaTabelaHoraria() {
        return Long.valueOf(this.params.get("horarioEntradaTabelaHoraria"));
    }

    @Override
    public void setHorarioEntradaTabelaHoraria(long horarioEntrada) {
        this.params.put("horarioEntradaTabelaHoraria", String.valueOf(horarioEntrada));
        db.commit();
    }

    @Override
    public void addFalha(EventoMotor falha) {
        this.falhas.put(falha.getTipoEvento().toString(), falha.getAnel());
        this.status.put("status", StatusDevice.COM_FALHAS.toString());
        if (falha.getAnel() > 0) {
            if (falha.getTipoEvento().isEntraEmIntermitente()) {
                this.statusAneis.put(falha.getAnel(), StatusAnel.AMARELO_INTERMITENTE_POR_FALHA.toString());
            } else {
                this.statusAneis.put(falha.getAnel(), StatusAnel.COM_FALHA.toString());
            }
        } else {
            atualizarStatusAneis(StatusAnel.AMARELO_INTERMITENTE_POR_FALHA);
        }
        db.commit();
    }

    @Override
    public void removeFalha(EventoMotor remocao) {
        TipoEvento falha = CausaERemocaoEvento.getFalha(remocao.getTipoEvento());
        if (this.falhas.containsKey(falha.toString())) {
            this.falhas.remove(falha.toString());
            if (!emFalha()) {
                this.status.put("status", StatusDevice.ATIVO.toString());
            }
            if (!emFalha(remocao.getAnel())) {
                this.statusAneis.put(remocao.getAnel(), StatusAnel.NORMAL.toString());
            }
            db.commit();
        }
    }

    private boolean emFalha(Integer anel) {
        return this.falhas.values().contains(anel);
    }

    @Override
    public boolean emFalha() {
        return this.falhas.size() > 0;
    }

    @Override
    public void setTempData(String id, String key, String value) {
        Map<String, String> map;

        if (!this.tempData.containsKey(id)) {
            map = new HashMap<>();
        } else {
            map = this.tempData.get(id);
        }

        map.put(key, value);
        this.tempData.put(id, map);
        db.commit();
    }

    @Override
    public String getTempData(String id, String key) {
        return this.tempData.get(id).get(key);
    }

    @Override
    public void clearTempData(String id) {
        if (this.tempData.containsKey(id)) {
            this.tempData.get(id).clear();
            db.commit();
        }
    }

    @Override
    public String getMarca() {
        return this.dadosHardware.get("marca");
    }

    @Override
    public String getModelo() {
        return this.dadosHardware.get("modelo");
    }

    @Override
    public String getFirmware() {
        return this.dadosHardware.get("firmware");
    }

    @Override
    public void storeEnvelope(Envelope envelope) {
        this.envelopes.put(envelope.getIdMensagem(), envelope);
        this.db.commit();
    }

    @Override
    public Collection getEnvelopes() {
        return this.envelopes.values();
    }

    @Override
    public void clearEnvelopes() {
        this.envelopes.clear();
        this.db.commit();
    }

    private void atualizarStatusAneis(StatusAnel statusAnel) {
        for (int i = 1; i <= this.statusAneis.size(); i++) {
            this.statusAneis.put(i, statusAnel.toString());
        }
    }
}
