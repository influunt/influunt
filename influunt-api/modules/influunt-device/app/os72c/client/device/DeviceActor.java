package os72c.client.device;

import akka.actor.UntypedActor;
import engine.*;
import models.Anel;
import models.Controlador;
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

import static engine.TipoEventoParamsTipoDeDado.*;


/**
 * Created by rodrigosol on 11/4/16.
 */
public class DeviceActor extends UntypedActor implements MotorCallback, DeviceBridgeCallback {

    private final Storage storage;
    private Controlador controlador;
    private Motor motor;

    private DeviceBridge device;

    private boolean iniciado = false;


    public DeviceActor(Storage mapStorage, DeviceBridge device) {
        this.storage = mapStorage;
        this.device = device;
        start();
    }

    private synchronized void start() {
        if (!iniciado) {
            this.controlador = storage.getControlador();
            if (controlador != null) {
                iniciado = true;
                this.device.start(this);
                this.motor = new Motor(this.controlador, new DateTime(), new DateTime(), this);

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
    }

    private void sendAlarmeOuFalha(EventoMotor eventoMotor) {
        Envelope envelope = AlarmeFalha.getMensagem(controlador.getId().toString(), eventoMotor);
        sendMessage(envelope);
    }

    @Override
    public void onTrocaDePlano(DateTime timestamp, Evento eventoAnterior, Evento eventoAtual, List<String> modos) {
    }

    @Override
    public void onAlarme(DateTime timestamp, EventoMotor eventoMotor) {
        sendAlarmeOuFalha(eventoMotor);
    }

    @Override
    public void onFalha(DateTime timestamp, EventoMotor eventoMotor) {
        sendAlarmeOuFalha(eventoMotor);
    }

    @Override
    public void onRemocaoFalha(DateTime timestamp, EventoMotor eventoMotor) {
        sendAlarmeOuFalha(eventoMotor);
    }

    @Override
    public void modoManualAtivo(DateTime timestamp) {
        device.modoManualAtivo(timestamp);
    }

    @Override
    public void modoManualDesativado(DateTime timestamp) {
        device.modoManualDesativo(timestamp);
    }

    @Override
    public void onEstagioChange(int anel, Long numeroCiclos, Long tempoDecorrido, DateTime timestamp, IntervaloGrupoSemaforico intervalos) {
        device.sendEstagio(intervalos);
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
        boolean disparar = true;
        Anel anel = null;
        if (eventoMotor.getTipoEvento().getParamsDescriptor() != null) {
            if (eventoMotor.getTipoEvento().getParamsDescriptor().getTipo().equals(DETECTOR_PEDESTRE) ||
                eventoMotor.getTipoEvento().getParamsDescriptor().getTipo().equals(DETECTOR_VEICULAR)) {
                anel = buscarAnelPorDetector((Pair<Integer, TipoDetector>) eventoMotor.getParams()[0]);
            } else if (eventoMotor.getTipoEvento().getParamsDescriptor().getTipo().equals(GRUPO_SEMAFORICO)) {
                anel = buscarAnelPorGrupo((Integer) eventoMotor.getParams()[0]);
            }
        }

        if (anel != null) {
            eventoMotor.setParams(new Object[]{eventoMotor.getParams()[0],
                anel.getPosicao()});
        }
        motor.onEvento(eventoMotor);
    }

    private Anel buscarAnelPorDetector(Pair<Integer, TipoDetector> pair) {
        return controlador.findAnelByDetector(pair.getSecond(), pair.getFirst());
    }

    private Anel buscarAnelPorGrupo(Integer posicao) {
        return controlador.findAnelByGrupoSemaforico(posicao);
    }
}
