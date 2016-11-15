package os72c.client.device;

import akka.actor.UntypedActor;
import com.google.inject.Inject;
import engine.*;
import models.Controlador;
import models.Evento;
import org.joda.time.DateTime;
import os72c.client.protocols.Mensagem;
import os72c.client.storage.MapStorage;
import os72c.client.storage.Storage;
import protocol.Envelope;

import java.util.List;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;



/**
 * Created by rodrigosol on 11/4/16.
 */
public class DeviceActor extends UntypedActor implements MotorCallback {
    private  Controlador controlador;

    private final Storage storage;

    private Motor motor;

    private SerialDevice deviceBridge;


    public DeviceActor(Storage mapStorage){
        this.storage = mapStorage;
        start();
    }

    private void start() {
        this.controlador = storage.getControlador();
        if(controlador!=null) {
            this.motor = new Motor(this.controlador, new DateTime(),new DateTime(),this);
            this.deviceBridge = new SerialDevice();

            Executors.newScheduledThreadPool(1)
                .scheduleAtFixedRate(()->{
                    try {
                        motor.tick();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }, 0, 100, TimeUnit.MILLISECONDS);
        }
    }

    @Override
    public void onTrocaDePlano(DateTime timestamp, Evento eventoAnterior, Evento eventoAtual, List<String> modos) {

    }

    @Override
    public void onAlarme(DateTime timestamp, EventoMotor eventoMotor) {

    }

    @Override
    public void onEstagioChange(int anel, Long numeroCiclos, Long tempoDecorrido, DateTime timestamp, IntervaloGrupoSemaforico intervalos) {
        deviceBridge.sendEstagio(intervalos);
    }

    @Override
    public void onEstagioEnds(int anel, Long numeroCiclos, Long tempoDecorrido, DateTime timestamp, IntervaloGrupoSemaforico intervalos) {

    }

    @Override
    public void onCicloEnds(int anel, Long numeroCiclos) {

    }

    @Override
    public void onTrocaDePlanoEfetiva(AgendamentoTrocaPlano agendamentoTrocaPlano) {

    }

    @Override
    public void onReceive(Object message) throws Exception {
        if(message instanceof Envelope){
            Envelope envelope = (Envelope) message;
            switch (envelope.getTipoMensagem()){
                case OK:
                    start();
                    break;
            }
        }
        System.out.println("Menssagem recebida no device actor");
    }
}
