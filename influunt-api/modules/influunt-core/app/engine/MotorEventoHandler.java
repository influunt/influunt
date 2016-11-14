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
                handleTrocaEstagioManual(eventoMotor);
                break;

            case FALHA_FASE_VERMELHA_DE_GRUPO_SEMAFORICO_APAGADA:
            case FALHA_FASE_VERMELHA_DE_GRUPO_SEMAFORICO_REMOCAO:
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

            case FALHA_DETECTOR_VEICULAR_REMOCAO:
            case FALHA_DETECTOR_PEDESTRE_REMOCAO:
                handleRemocaoFalhaDetector(eventoMotor);
                break;


            case FALHA_VERDES_CONFLITANTES:
                getMotor().getMonitor().monitoraRepeticaoVerdesConflitantes((Integer) eventoMotor.getParams()[0]);
                handleFalhaAnel(eventoMotor);
                break;

            case FALHA_SEQUENCIA_DE_CORES:
            case FALHA_DESRESPEITO_AO_TEMPO_MAXIMO_DE_PERMANENCIA_NO_ESTAGIO:
                handleFalhaAnel(eventoMotor);
                break;

            case FALHA_VERDES_CONFLITANTES_REMOCAO:
                handleRemoveFalhaAnel(eventoMotor);
                break;

            case FALHA_WATCH_DOG:
            case FALHA_MEMORIA:
                handleFalhaControlador(eventoMotor);
                break;

            case ALARME_AMARELO_INTERMITENTE:
            case ALARME_SEMAFORO_APAGADO:
            case ALARME_ACERTO_RELOGIO_GPS:
                break;

            case IMPOSICAO_PLANO:
                break;

        }

    }

    private void handleRemoveFalhaAnel(EventoMotor eventoMotor) {
        Integer anel = (Integer) eventoMotor.getParams()[0];
        eventoMotor.setParams(new Object[]{anel, getMotor().getPlanoAtual(anel)});
        motor.getEstagios().get(anel - 1).onEvento(eventoMotor);
    }

    private void handleFalhaControlador(EventoMotor eventoMotor) {
        motor.getEstagios().forEach(gerenciadorDeEstagios -> {
            gerenciadorDeEstagios.onEvento(eventoMotor);
        });
    }

    private void handleFalhaAnel(EventoMotor eventoMotor) {
        Integer anel = (Integer) eventoMotor.getParams()[0];
        motor.getEstagios().get(anel - 1).onEvento(eventoMotor);
    }

    private void handleFaseVermelhaGrupoSemaforico(EventoMotor eventoMotor) {
        GrupoSemaforico grupoSemaforico = (GrupoSemaforico) eventoMotor.getParams()[0];
        if (eventoMotor.getTipoEvento().equals(TipoEvento.FALHA_FASE_VERMELHA_DE_GRUPO_SEMAFORICO_APAGADA)) {
            if (!falhasFaseGrupoSemorofico.containsKey(grupoSemaforico.getPosicao())) {
                if (grupoSemaforico.isFaseVermelhaApagadaAmareloIntermitente()) {
                    falhasFaseGrupoSemorofico.put(grupoSemaforico.getPosicao(), eventoMotor);
                    motor.getEstagios().get(grupoSemaforico.getAnel().getPosicao() - 1).onEvento(eventoMotor);
                }
            }
        } else {
            if (grupoSemaforico.isFaseVermelhaApagadaAmareloIntermitente()) {
                falhasFaseGrupoSemorofico.remove(grupoSemaforico.getPosicao());
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
        Integer anel = (Integer) eventoMotor.getParams()[1];
        motor.getEstagios().get(anel - 1).onEvento(eventoMotor);
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

    private void handleTrocaEstagioManual(EventoMotor eventoMotor) {
        motor.getEstagios().forEach(estagio -> {
            estagio.onEvento(eventoMotor);
        });
    }

    public Motor getMotor() {
        return motor;
    }
}
