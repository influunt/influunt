package os72c.client.storage;

import br.org.mapdb.DB;
import br.org.mapdb.HTreeMap;
import br.org.mapdb.Serializer;
import com.google.inject.Inject;
import com.google.inject.Singleton;
import json.ControladorCustomDeserializer;
import json.ControladorCustomSerializer;
import models.Controlador;
import models.StatusDevice;

/**
 * Created by leonardo on 9/13/16.
 */
@Singleton
public class MapStorage implements Storage{

    private final DB db;
    private final HTreeMap<String, String> status;
    private final HTreeMap<String, String> controlador;

    @Inject
    public MapStorage(StorageConf storageConf){
        this.db = storageConf.getDB();
        this.status = this.db.hashMap("status")
                .keySerializer(Serializer.STRING)
                .valueSerializer(Serializer.STRING)
                .layout(1,1,1)
                .createOrOpen();

        if(!this.status.containsKey("status")){
            this.status.put("status", StatusDevice.NOVO.toString());
            db.commit();
        }

        this.controlador = this.db.hashMap("controladores")
                .keySerializer(Serializer.STRING)
                .valueSerializer(Serializer.STRING)
                .layout(1,2,1)
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
        return new ControladorCustomDeserializer().getControladorFromJson(play.libs.Json.parse(this.controlador.get("atual")));
    }

    @Override
    public void setControlador(Controlador controlador) {
        this.status.put("atual", new ControladorCustomSerializer().getControladorJson(controlador).toString());
    }

    @Override
    public Controlador getControladorStaging() {
        return new ControladorCustomDeserializer().getControladorFromJson(play.libs.Json.parse(this.controlador.get("temp")));
    }

    @Override
    public void setControladorStaging(Controlador controlador) {
        this.status.put("temp", new ControladorCustomSerializer().getControladorJson(controlador).toString());
    }
}
