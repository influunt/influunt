package os72c.client.device;

import akka.actor.UntypedActor;
import engine.*;
import models.Controlador;
import models.Detector;
import models.Evento;
import models.TipoDetector;
import org.apache.commons.math3.util.Pair;
import org.joda.time.DateTime;
import os72c.client.storage.Storage;
import os72c.client.utils.AtoresDevice;
import protocol.AlarmeFalha;
import protocol.Envelope;
import protocol.TrocaPlanoEfetiva;

import java.util.List;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;


/**
 * Created by rodrigosol on 11/4/16.
 */
public class DeviceActor extends UntypedActor implements MotorCallback, DeviceBridgeCallback {
    private Controlador controlador;

    private final Storage storage;

    private Motor motor;

    private SerialDevice deviceBridge;


    public DeviceActor(Storage mapStorage) {
        this.storage = mapStorage;
        start();
    }

    private void start() {
        this.controlador = storage.getControlador();
        if (controlador != null) {
            this.motor = new Motor(this.controlador, new DateTime(), new DateTime(), this);
            this.deviceBridge = new SerialDevice(this);

            Executors.newScheduledThreadPool(1)
                .scheduleAtFixedRate(() -> {
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
        Envelope envelope = AlarmeFalha.getMensagem(controlador.getId().toString(), eventoMotor);
        sendMessage(envelope);
    }

    @Override
    public void onFalha(DateTime timestamp, EventoMotor eventoMotor) {
        Envelope envelope = AlarmeFalha.getMensagem(controlador.getId().toString(), eventoMotor);
        sendMessage(envelope);
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
        Envelope envelope = TrocaPlanoEfetiva.getMensagem(controlador.getId().toString(), agendamentoTrocaPlano);
        sendMessage(envelope);
    }

    private void sendMessage(Envelope envelope) {
        getContext().actorFor(AtoresDevice.mqttActorPath(controlador.getId().toString())).tell(envelope, getSelf());
    }

    @Override
    public void onReceive(Object message) throws Exception {
        if (message instanceof Envelope) {
            Envelope envelope = (Envelope) message;
            switch (envelope.getTipoMensagem()) {
                case OK:
                    start();
                    break;
            }
        }
        System.out.println("Menssagem recebida no device actor");
    }

    @Override
    public void onReady() {

    }

    @Override
    public void onEvento(EventoMotor eventoMotor) {
        boolean disparar = false;
        switch (eventoMotor.getTipoEvento()){
            case ACIONAMENTO_DETECTOR_PEDESTRE:
            case ACIONAMENTO_DETECTOR_VEICULAR:
                final Pair<Integer, TipoDetector> pair = (Pair<Integer, TipoDetector>) eventoMotor.getParams()[0];
                Detector detector = controlador.findDetectorByPosicaoETipo(pair.getSecond(),pair.getFirst());
                if(detector!=null) {
                    eventoMotor.setParams(new Object[]{eventoMotor.getParams()[0],
                        detector.getAnel().getPosicao()});
                    disparar = true;
                }
            break;
        }
        if(disparar || eventoMotor.getTipoEvento().getTipoEventoControlador().equals(TipoEventoControlador.FALHA) ||
                       eventoMotor.getTipoEvento().getTipoEventoControlador().equals(TipoEventoControlador.ALARME)){
            motor.onEvento(eventoMotor);
        }
    }
}
