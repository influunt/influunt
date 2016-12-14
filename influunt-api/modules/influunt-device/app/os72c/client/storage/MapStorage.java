package os72c.client.storage;

import br.org.mapdb.DB;
import br.org.mapdb.HTreeMap;
import br.org.mapdb.Serializer;
import com.fasterxml.jackson.databind.JsonNode;
import com.google.inject.Inject;
import com.google.inject.Singleton;
import engine.TipoEvento;
import json.ControladorCustomDeserializer;
import json.ControladorCustomSerializer;
import models.Controlador;
import models.StatusDevice;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by leonardo on 9/13/16.
 */
@Singleton
public class MapStorage implements Storage {

    private final DB db;

    private final HTreeMap<String, String> status;

    private final HTreeMap<String, String> controlador;

    private final HTreeMap<String, String> keys;

    private final HTreeMap<String, String> params;

    private final HTreeMap<String, String> dadosHardware;

    private final HTreeMap<String, Boolean> falhas;

    private final HTreeMap<String, Map<String,String>> tempData;

    @Inject
    public MapStorage(StorageConf storageConf) {
        this.db = storageConf.getDB();
        this.status = this.db.hashMap("status")
            .keySerializer(Serializer.STRING)
            .valueSerializer(Serializer.STRING)
            .layout(1, 1, 1)
            .createOrOpen();

        if (!this.status.containsKey("status")) {
            this.status.put("status", StatusDevice.NOVO.toString());
            db.commit();
        }

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
            .valueSerializer(Serializer.BOOLEAN)
            .layout(1, 2, 1)
            .createOrOpen();

        this.tempData = this.db.hashMap("tempData")
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
    public void addFalha(TipoEvento falha) {
        this.falhas.put(falha.toString(), true);
        this.status.put("status", StatusDevice.COM_FALHAS.toString());
        db.commit();
    }

    @Override
    public void removeFalha(TipoEvento falha) {
        if (this.falhas.containsKey(falha.toString())) {
            this.falhas.remove(falha.toString());
            if (!emFalha()) {
                this.status.put("status", StatusDevice.ATIVO.toString());
            }
            db.commit();
        }
    }

    @Override
    public boolean emFalha() {
        return this.falhas.size() > 0;
    }

    @Override
    public void setTempData(String id,String key, String value) {
        Map<String,String> map = null;

        if(!this.tempData.containsKey(id)){
            map = new HashMap<>();
        }else{
            map = this.tempData.get(id);
        }

        map.put(key,value);
        this.tempData.put(id,map);
        db.commit();
    }

    @Override
    public String getTempData(String id,String key) {
        return this.tempData.get(id).get(key);
    }

    @Override
    public void clearTempData(String id) {
        if(this.tempData.containsKey(id)) {
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

}
