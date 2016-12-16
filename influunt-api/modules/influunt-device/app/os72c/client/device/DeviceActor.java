package os72c.client.device;

import akka.actor.UntypedActor;
import akka.actor.UntypedActorContext;
import com.fasterxml.jackson.databind.JsonNode;
import engine.*;
import engine.TipoEvento;
import logger.InfluuntLogger;
import logger.NivelLog;
import logger.TipoLog;
import models.*;
import org.apache.commons.math3.util.Pair;
import org.joda.time.DateTime;
import os72c.client.storage.Storage;
import os72c.client.utils.AtoresDevice;
import play.libs.Json;
import protocol.*;

import java.util.List;
import java.util.TreeSet;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;

import static engine.TipoEventoParamsTipoDeDado.*;


/**
 * Created by rodrigosol on 11/4/16.
 */
public class DeviceActor extends UntypedActor implements MotorCallback, DeviceBridgeCallback {

    private final Storage storage;

    private Controlador controlador;

    private String id;

    private Motor motor;

    private DeviceBridge device;

    private boolean iniciado = false;

    private UntypedActorContext context;

    private ScheduledFuture<?> executor;

    private TreeSet<String> falhasAtuais = new TreeSet<>();

    private Long tempoDecorrido = 0L;

    private boolean pronto = false;


    public DeviceActor(Storage mapStorage, DeviceBridge device, String id) {
        this.storage = mapStorage;
        this.device = device;
        this.id = id;
        start();
    }

    @Override
    public void preStart() throws Exception {
        this.context = getContext();
        this.device.start(this);
    }

    private synchronized void start() {

        if (!iniciado && pronto) {

            InfluuntLogger.log(NivelLog.DETALHADO,TipoLog.INICIALIZACAO,"Verificando a configuração do controlador");
            this.controlador = storage.getControlador();
            if (controlador != null) {
                InfluuntLogger.log(NivelLog.DETALHADO,TipoLog.INICIALIZACAO,"Configuração encontrada");
                iniciado = true;
                this.motor = new Motor(this.controlador, new DateTime(), this);

                executor = Executors.newScheduledThreadPool(1)
                    .scheduleAtFixedRate(() -> {
                        try {

                            if(tempoDecorrido % 1000 == 0) {
                                InfluuntLogger.log(NivelLog.SUPERDETALHADO,TipoLog.EXECUCAO, "TICK:" + tempoDecorrido);
                            }
                            tempoDecorrido+=100;
                            motor.tick();
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }, 0, 100, TimeUnit.MILLISECONDS);

                InfluuntLogger.log(NivelLog.DETALHADO,TipoLog.INICIALIZACAO,"O controlador foi colocado em execução");

            } else {
                InfluuntLogger.log(NivelLog.NORMAL,TipoLog.INICIALIZACAO,"Não existe configuração para iniciar o motor");
                InfluuntLogger.log(NivelLog.NORMAL,TipoLog.INICIALIZACAO,"O controlador será iniciado quando um configuração for recebida");
            }
        }
    }

    private void sendAlarmeOuFalha(EventoMotor eventoMotor) {
        Envelope envelope = AlarmeFalha.getMensagem(id, eventoMotor);
        sendMessage(envelope);
    }

    private void sendRemocaoFalha(EventoMotor eventoMotor) {
        Envelope envelope = RemocaoFalha.getMensagem(id, eventoMotor);
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
        storage.addFalha(eventoMotor.getTipoEvento());
        sendMessage(MudancaStatusControlador.getMensagem(id, StatusDevice.COM_FALHAS));
        sendAlarmeOuFalha(eventoMotor);
    }

    @Override
    public void onRemocaoFalha(DateTime timestamp, EventoMotor eventoMotor) {
        TipoEvento falha = CausaERemocaoEvento.getFalha(eventoMotor.getTipoEvento());
        if (falha != null) {
            storage.removeFalha(falha);
            if (!storage.emFalha()) {
                sendMessage(MudancaStatusControlador.getMensagem(id, StatusDevice.ATIVO));
            }
        }
        sendRemocaoFalha(eventoMotor);
    }

    @Override
    public void modoManualAtivo(DateTime timestamp) {
        device.modoManualAtivo();
    }

    @Override
    public void modoManualDesativado(DateTime timestamp) {
        device.modoManualDesativado();
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
        Envelope envelope = TrocaPlanoEfetiva.getMensagem(id, agendamentoTrocaPlano);
        sendMessage(envelope);
    }

    private void sendMessage(Envelope envelope) {
        context.actorFor(AtoresDevice.mqttActorPath(id)).tell(envelope, getSelf());
    }

    @Override
    public void onReceive(Object message) throws Exception {
        if (message instanceof Envelope) {
            Envelope envelope = (Envelope) message;
            switch (envelope.getTipoMensagem()) {
                case CONFIGURACAO_OK:
                    start();
                    break;

                case IMPOSICAO_DE_MODO_OPERACAO:
                    imporModoOperacao(envelope.getConteudoParsed());
                    break;

                case IMPOSICAO_DE_PLANO:
                    imporPlano(envelope.getConteudoParsed());
                    break;

                case LIBERAR_IMPOSICAO:
                    liberarImposicao(envelope.getConteudoParsed());
                    break;

                case TROCAR_TABELA_HORARIA:
                    trocarTabelaHoraria(false);

                    break;

                case TROCAR_TABELA_HORARIA_IMEDIATAMENTE:
                    trocarTabelaHoraria(true);
                    break;

                case LER_DADOS_CONTROLADOR:
                    enviaDadosAtualDoControlador(envelope);
                    break;

                case ATUALIZAR_CONFIGURACAO:
                case TROCAR_PLANOS:
                    alterarControladorNoMotor();
                    break;
            }
        }
    }

    private void enviaDadosAtualDoControlador(Envelope envelope) {
        if (motor != null) {
            sendMessage(LerDadosControlador.retornoLeituraDados(envelope, motor, storage.getStatus()));
        }
    }

    @Override
    public void onReady() {
        pronto = true;
        start();
    }

    @Override
    public void onEvento(EventoMotor eventoMotor) {
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

    private void imporModoOperacao(JsonNode conteudo) {
        String modoOperacao = conteudo.get("modoOperacao").asText();
        int duracao = conteudo.get("duracao").asInt();
        Long horarioEntrada = conteudo.get("horarioEntrada").asLong();
        List<Integer> numerosAneis = Json.fromJson(conteudo.get("numerosAneis"), List.class);
        numerosAneis.forEach(numeroAnel -> {
            motor.onEvento(new EventoMotor(new DateTime(), TipoEvento.IMPOSICAO_MODO, modoOperacao, numeroAnel, duracao, horarioEntrada));
        });
    }

    private void imporPlano(JsonNode conteudo) {
        int posicaoPlano = conteudo.get("posicaoPlano").asInt();
        int duracao = conteudo.get("duracao").asInt();
        Long horarioEntrada = conteudo.get("horarioEntrada").asLong();
        List<Integer> numerosAneis = Json.fromJson(conteudo.get("numerosAneis"), List.class);
        numerosAneis.forEach(numeroAnel -> {
            motor.onEvento(new EventoMotor(new DateTime(), TipoEvento.IMPOSICAO_PLANO, posicaoPlano, numeroAnel, duracao, horarioEntrada));
        });

    }

    private void liberarImposicao(JsonNode conteudo) {
        List<Integer> numerosAneis = Json.fromJson(conteudo.get("numerosAneis"), List.class);
        numerosAneis.forEach(numeroAnel -> {
            motor.onEvento(new EventoMotor(new DateTime(), TipoEvento.LIBERAR_IMPOSICAO, numeroAnel));
        });
    }

    private void trocarTabelaHoraria(boolean imediatamente) {
        alterarControladorNoMotor();
        if (imediatamente) {
            motor.onMudancaTabelaHoraria();
        }
    }

    private void alterarControladorNoMotor() {
        motor.setControladorTemporario(storage.getControladorStaging());
        storage.setControlador(storage.getControladorStaging());
        storage.setControladorStaging(null);
    }


    @Override
    public void aroundPostStop() {
        if (motor != null) {
            motor.stop();
            executor.cancel(true);
        }
        super.aroundPostStop();
    }
}
