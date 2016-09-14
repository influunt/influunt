package os72c.client.supervisores;

import akka.actor.ActorRef;
import akka.actor.Props;
import akka.actor.UntypedActor;
import akka.event.Logging;
import akka.event.LoggingAdapter;
import com.google.common.collect.ImmutableList;
import os72c.client.models.EstadoGrupo;
import os72c.client.models.Interrupcao;
import os72c.client.models.Intervalos;
import os72c.client.models.Troca;
import os72c.client.protocols.MensagemControladorSupervisor;
import os72c.client.protocols.MensagemInterrupcao;

/**
 * Created by rodrigosol on 6/24/16.
 */
public class Supervisor extends UntypedActor {
    LoggingAdapter log = Logging.getLogger(getContext().system(), this);

    Long i = 0l;

    private Intervalos intervalos;

    private ActorRef controlador;

    //    private Config conf;
    private boolean ativo = false;

    public static Props props() {
        return Props.create(Supervisor.class);
    }

    @Override
    public void preStart() throws Exception {
        String args[] = new String[6];
//        if (conf.getString("type").equals("serial")) {
//            setupSerial(args);
//            controlador = getContext().actorOf(Props.create(ControladorSerial.class), "controlador");
//        } else if (conf.getString("type").equals("i2c")) {
//            controlador = getContext().actorOf(Props.create(ControladorI2C.class), "controlador");
//        } else {
//            //Modo de comunicação com o hardware não definido
//            throw new RuntimeException("O tipo de driver não foi especificado");
//        }
//        intervalos = new Intervalos(getContext(), controlador, getSelf());
//        controlador.tell(new MensagemControladorSupervisor(TipoEvento.SUPERVISOR_PRONTO, null, args), getSelf());

    }

    private void setupSerial(String[] args) {
//        args[Constants.SERIAL_PORTA] = conf.getString("serial.porta");
//        args[Constants.SERIAL_BAUDRATE] = conf.getString("serial.baudrate");
//        args[Constants.SERIAL_DATABITS] = conf.getString("serial.databits");
//        args[Constants.SERIAL_STOPBITS] = conf.getString("serial.stopbits");
//        args[Constants.SERIAL_PARITY] = conf.getString("serial.parity");
//        args[Constants.SERIAL_START_DELAY] = conf.getString("serial.startdelay");
    }

    @Override
    public void onReceive(Object message) throws Exception {
        log.info("onReceive: {}", message);

        if (message instanceof MensagemControladorSupervisor) {
            final MensagemControladorSupervisor mensagemControladorSupervisor = (MensagemControladorSupervisor) message;
            System.out.println(mensagemControladorSupervisor);
            log.debug(mensagemControladorSupervisor.toString());
            switch (mensagemControladorSupervisor.tipoEvento) {
                case CONTROLADOR_PRONTO:
                    ativo = true;
                    log.info("ClientActor Pronto: [{},{},{}]", mensagemControladorSupervisor.argumentos);
                    registraControlador(mensagemControladorSupervisor);
                    break;
                case PROXIMO_CICLO:
                    if (ativo) {
                        intervalos.proximoCiclo(Integer.valueOf(mensagemControladorSupervisor.argumentos[0]));
                    }
                    break;
            }
        } else if (message instanceof MensagemInterrupcao) {
            intervalos.status();
            tratarInterrupcao(((MensagemInterrupcao) message));
        }
    }

    private void tratarInterrupcao(MensagemInterrupcao interrupcao) {
        interrupcao.interrupcoes.stream().forEach(i -> {
            switch (i.tipoInterrupcao) {
                case ERRO:
                    tratarInterrupcaoErro(i);
                    return;
                case OPERACAO_NORMAL:
                    tratarInterrupcaoNormal(i);
                    break;
                case OPERACAO_MANUAL:
                    tratarInterrupcaoOperacaoManual(i);
                    break;
                case DETECTOR_PEDESTRE:
                    tratarInterrupcaoDetectorPedestre(i);
                    break;
                case DETECTOR_VEICULAR:
                    tratarInterrupcaoDetectorVeicular(i);
                    break;
            }

        });
    }

    private void tratarInterrupcaoDetectorVeicular(Interrupcao i) {

    }

    private void tratarInterrupcaoDetectorPedestre(Interrupcao i) {

    }

    private void tratarInterrupcaoOperacaoManual(Interrupcao i) {
        intervalos.proximoEstagio();
    }

    private void tratarInterrupcaoNormal(Interrupcao i) {
        intervalos.start(0, 0);
        this.ativo = true;
    }

    private void tratarInterrupcaoErro(Interrupcao i) {
        intervalos.pararExecucao();
        this.ativo = false;
        controlador.tell(i, getSelf());
    }

    private void registraControlador(MensagemControladorSupervisor mensagemControladorSupervisor) {
        ImmutableList<Troca> trocas = ImmutableList.of(
                new Troca(6000, 0, EstadoGrupo.VERMELHO),
                new Troca(6000, 1, EstadoGrupo.AMARELO),
                new Troca(6000, 2, EstadoGrupo.VERMELHO)
        );


        intervalos.setTrocas(trocas);
        intervalos.start(0, 0);

    }

}


