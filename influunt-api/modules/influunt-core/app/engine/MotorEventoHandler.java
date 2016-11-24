package engine;

import models.Anel;
import models.GrupoSemaforico;
import models.TipoDetector;
import org.apache.commons.math3.util.Pair;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by rodrigosol on 11/1/16.
 */
public class MotorEventoHandler {

    private final Motor motor;

    private Map<Integer, EventoMotor> falhasFaseGrupoSemorofico = new HashMap<>();

    private Map<Pair<Integer, TipoDetector>, EventoMotor> falhasDetector = new HashMap<>();

    public MotorEventoHandler(Motor motor) {
        this.motor = motor;
    }

    public void handle(EventoMotor eventoMotor) {

        switch (eventoMotor.getTipoEvento()) {

            case ACIONAMENTO_DETECTOR_VEICULAR:
            case ACIONAMENTO_DETECTOR_PEDESTRE:
                handleAcionamentoDetector(eventoMotor);
                break;

            case ALARME_ABERTURA_DA_PORTA_PRINCIPAL_DO_CONTROLADOR:
            case ALARME_FECHAMENTO_DA_PORTA_PRINCIPAL_DO_CONTROLADOR:
            case ALARME_ABERTURA_DA_PORTA_DO_PAINEL_DE_FACILIDADES_DO_CONTROLADOR:
            case ALARME_FECHAMENTO_DA_PORTA_DO_PAINEL_DE_FACILIDADES_DO_CONTROLADOR:
                break;

            case INSERCAO_DE_PLUG_DE_CONTROLE_MANUAL:
            case RETIRADA_DE_PLUG_DE_CONTROLE_MANUAL:
                handleAlternarModoManual(eventoMotor);
                break;

            case TROCA_ESTAGIO_MANUAL:
                handleControlador(eventoMotor);
                break;

            case FALHA_FASE_VERMELHA_DE_GRUPO_SEMAFORICO_APAGADA:
            case REMOCAO_FALHA_FASE_VERMELHA_DE_GRUPO_SEMAFORICO:
                handleFaseVermelhaGrupoSemaforico(eventoMotor);
                break;

            case ALARME_FOCO_VERMELHO_DE_GRUPO_SEMAFORICO_APAGADA:
            case ALARME_FOCO_VERMELHO_DE_GRUPO_SEMAFORICO_REMOCAO:
                //TODO: Qual a diferenca para FALHA_FASE_VERMELHA_DE_GRUPO_SEMAFORICO_APAGADA
                break;

            case FALHA_DETECTOR_VEICULAR_FALTA_ACIONAMENTO:
            case FALHA_DETECTOR_PEDESTRE_FALTA_ACIONAMENTO:
            case FALHA_DETECTOR_VEICULAR_ACIONAMENTO_DIRETO:
            case FALHA_DETECTOR_PEDESTRE_ACIONAMENTO_DIRETO:
                handleFalhaDetector(eventoMotor);
                break;

            case REMOCAO_FALHA_DETECTOR_VEICULAR:
            case REMOCAO_FALHA_DETECTOR_PEDESTRE:
                handleRemocaoFalhaDetector(eventoMotor);
                break;


            case FALHA_VERDES_CONFLITANTES:
                handleFalhaAnel(eventoMotor);
                break;

            case FALHA_SEQUENCIA_DE_CORES:
            case FALHA_DESRESPEITO_AO_TEMPO_MAXIMO_DE_PERMANENCIA_NO_ESTAGIO:
                handleFalhaAnel(eventoMotor);
                break;

            case REMOCAO_FALHA_VERDES_CONFLITANTES:
                handleRemoveFalhaAnel(eventoMotor);
                break;

            case FALHA_WATCH_DOG:
            case FALHA_MEMORIA:
                handleControlador(eventoMotor);
                break;

            case ALARME_AMARELO_INTERMITENTE:
            case ALARME_SEMAFORO_APAGADO:
            case ALARME_ACERTO_RELOGIO_GPS:
                break;

            case IMPOSICAO_PLANO:
            case IMPOSICAO_MODO:
                handleAnel(eventoMotor, 1);
                break;

            case LIBERAR_IMPOSICAO:
                handleRemoveFalhaAnel(eventoMotor);
                break;
        }

    }

    private void handleRemoveFalhaAnel(EventoMotor eventoMotor) {
        Integer anel = (Integer) eventoMotor.getParams()[0];
        eventoMotor.setParams(new Object[]{anel, getMotor().getPlanoAtual(anel)});
        motor.getEstagios().get(anel - 1).onEvento(eventoMotor);
    }

    private void handleControlador(EventoMotor eventoMotor) {
        motor.getEstagios().forEach(gerenciadorDeEstagios -> {
            gerenciadorDeEstagios.onEvento(eventoMotor);
        });
    }

    private void handleFalhaAnel(EventoMotor eventoMotor) {
        handleAnel(eventoMotor, 0);
    }

    private void handleFaseVermelhaGrupoSemaforico(EventoMotor eventoMotor) {
        Integer posicaoGrupoSemaforico = (Integer) eventoMotor.getParams()[0];
        GrupoSemaforico grupoSemaforico = motor.getControlador().findGrupoSemaforicoByPosicao(posicaoGrupoSemaforico);
        if (eventoMotor.getTipoEvento().equals(TipoEvento.FALHA_FASE_VERMELHA_DE_GRUPO_SEMAFORICO_APAGADA)) {
            if (!falhasFaseGrupoSemorofico.containsKey(posicaoGrupoSemaforico)) {
                if (grupoSemaforico.isFaseVermelhaApagadaAmareloIntermitente()) {
                    falhasFaseGrupoSemorofico.put(posicaoGrupoSemaforico, eventoMotor);
                    motor.getEstagios().get(grupoSemaforico.getAnel().getPosicao() - 1).onEvento(eventoMotor);
                }
            }
        } else {
            if (grupoSemaforico.isFaseVermelhaApagadaAmareloIntermitente()) {
                falhasFaseGrupoSemorofico.remove(posicaoGrupoSemaforico);
                Anel anel = grupoSemaforico.getAnel();
                if (!anel.getGruposSemaforicos().stream().anyMatch(grupo -> falhasFaseGrupoSemorofico.containsKey(grupo.getPosicao()))) {
                    eventoMotor.setParams(new Object[]{eventoMotor.getParams()[0], motor.getPlanoAtual(anel.getPosicao())});
                    motor.getEstagios().get(anel.getPosicao() - 1).onEvento(eventoMotor);
                }
            }
        }
    }

    private void handleFalhaDetector(EventoMotor eventoMotor) {
        Pair<Integer, TipoDetector> key = (Pair<Integer, TipoDetector>) eventoMotor.getParams()[0];
        Integer anel = (Integer) eventoMotor.getParams()[1];

        if (!falhasDetector.containsKey(key)) {
            falhasDetector.put(key, eventoMotor);
            motor.getEstagios().get(anel - 1).onEvento(eventoMotor);
        }
    }

    private void handleRemocaoFalhaDetector(EventoMotor eventoMotor) {
        handleAnel(eventoMotor, 1);
    }


    private void handleAcionamentoDetector(EventoMotor eventoMotor) {
        Pair<Integer, TipoDetector> key = (Pair<Integer, TipoDetector>) eventoMotor.getParams()[0];
        Integer anel = (Integer) eventoMotor.getParams()[1];

        GerenciadorDeEstagios gerenciador = motor.getEstagios().get(anel - 1);

        motor.getMonitor().registraAcionamentoDetector(gerenciador.getDetector(key.getFirst(), key.getSecond()));
        gerenciador.onEvento(eventoMotor);
        if (falhasDetector.containsKey(key)) {
            falhasDetector.remove(key);
        }
    }

    private void handleAlternarModoManual(EventoMotor eventoMotor) {
        motor.getEstagios().forEach(estagio -> {
            eventoMotor.setParams(new Object[]{motor.getPlanoAtual(estagio.getAnel())});
            estagio.onEvento(eventoMotor);
        });
    }


    private void handleAnel(EventoMotor eventoMotor, Integer posicaoAnel) {
        Integer anel = (Integer) eventoMotor.getParams()[posicaoAnel];
        motor.getEstagios().get(anel - 1).onEvento(eventoMotor);
    }

    public Motor getMotor() {
        return motor;
    }
}
