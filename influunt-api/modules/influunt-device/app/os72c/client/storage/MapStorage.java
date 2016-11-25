package os72c.client.storage;

import br.org.mapdb.DB;
import br.org.mapdb.HTreeMap;
import br.org.mapdb.Serializer;
import com.fasterxml.jackson.databind.JsonNode;
import com.google.inject.Inject;
import com.google.inject.Singleton;
import json.ControladorCustomDeserializer;
import json.ControladorCustomSerializer;
import models.Controlador;
import models.StatusDevice;

import java.util.Collections;

/**
 * Created by leonardo on 9/13/16.
 */
@Singleton
public class MapStorage implements Storage {

    private final DB db;

    private final HTreeMap<String, String> status;

    private final HTreeMap<String, String> controlador;

    private final HTreeMap<String, String> keys;

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
        String json = this.controlador.get("atual");
        if (json != null) {
            return new ControladorCustomDeserializer().getControladorFromJson(play.libs.Json.parse(json));
        } else {
            return null;
        }
    }

    @Override
    public void setControlador(Controlador controlador) {
        this.controlador.put("atual", new ControladorCustomSerializer().getControladorJson(controlador, Collections.singletonList(controlador.getArea().getCidade()), controlador.getRangeUtils()).toString());
        db.commit();
    }

    @Override
    public Controlador getControladorStaging() {
        return new ControladorCustomDeserializer().getControladorFromJson(play.libs.Json.parse(this.controlador.get("temp")));
    }

    @Override
    public void setControladorStaging(Controlador controlador) {
        this.controlador.put("temp", new ControladorCustomSerializer().getControladorJson(controlador, Collections.singletonList(controlador.getArea().getCidade()), controlador.getRangeUtils()).toString());
        db.commit();
    }

    @Override
    public JsonNode getControladorJson() {
        return play.libs.Json.parse(this.controlador.get("atual"));
    }

    @Override
    public JsonNode getControladorJsonStaging() {
        return play.libs.Json.parse(this.controlador.get("temp"));
    }

    @Override
    public JsonNode getPlanos() {
        return play.libs.Json.parse(this.controlador.get("atualPlanos"));
    }

    @Override
    public void setPlanos(JsonNode plano) {
        this.controlador.put("atualPlanos", plano.toString());
        db.commit();
    }

    @Override
    public JsonNode getPlanosStaging() {
        return play.libs.Json.parse(this.controlador.get("tempPlanos"));
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

}
