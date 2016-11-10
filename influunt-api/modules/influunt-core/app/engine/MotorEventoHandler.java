package engine;

import models.Anel;
import models.Detector;
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

            case FALHA_FOCO_VERMELHO_DE_GRUPO_SEMAFORICO_APAGADA:
            case FALHA_FOCO_VERMELHO_DE_GRUPO_SEMAFORICO_REMOCAO:
                //TODO: Qual a diferenca para FALHA_FASE_VERMELHA_DE_GRUPO_SEMAFORICO_APAGADA
                break;

            case FALHA_DETECTOR_VEICULAR_FALTA_ACIONAMENTO:
            case FALHA_DETECTOR_PEDESTRE_FALTA_ACIONAMENTO:
                handleFaltaAcionamentoDetector(eventoMotor);
                break;

            case FALHA_DETECTOR_VEICULAR_ACIONAMENTO_DIRETO:
            case FALHA_DETECTOR_PEDESTRE_ACIONAMENTO_DIRETO:
                handleAcionamentoDiretoDetector(eventoMotor);
                break;

            case FALHA_DETECTOR_VEICULAR_REMOCAO:
            case FALHA_DETECTOR_PEDESTRE_REMOCAO:
                handleRemocaoFalhaDetector(eventoMotor);
                break;

            case FALHA_DESRESPEITO_AO_TEMPO_MAXIMO_DE_PERMANENCIA_NO_ESTAGIO:
            case FALHA_VERDES_CONFLITANTES_REMOCAO:
            case FALHA_VERDES_CONFLITANTES:
            case FALHA_SEQUENCIA_DE_CORES:
                handleFalhaAnel(eventoMotor);
                break;

            case FALHA_AMARELO_INTERMITENTE:
            case FALHA_SEMAFORO_APAGADO:
            case FALHA_ACERTO_RELOGIO_GPS:
                break;

            case IMPOSICAO_PLANO:
                break;

        }

    }

    private void handleRemocaoFalhaDetector(EventoMotor eventoMotor) {
        Detector detector = (Detector) eventoMotor.getParams()[0];
        detector.setComFalha(false);
    }

    private void handleFalhaAnel(EventoMotor eventoMotor) {
        Integer anel = (Integer) eventoMotor.getParams()[0];
        motor.getEstagios().get(anel - 1).onEvento(eventoMotor);
    }

    private void handleFaltaAcionamentoDetector(EventoMotor eventoMotor) {
        Detector detector = (Detector) eventoMotor.getParams()[0];
        if (eventoMotor.getTipoEvento().equals(TipoEvento.FALHA_DETECTOR_VEICULAR_FALTA_ACIONAMENTO)) {
            Pair<Integer, TipoDetector> key = new Pair<Integer, TipoDetector>(detector.getPosicao(), detector.getTipo());
            if (!falhasDetector.containsKey(key)) {
                falhasDetector.put(key, eventoMotor);
                motor.getEstagios().get(detector.getAnel().getPosicao() - 1).onEvento(eventoMotor);
            }
        }
    }

    private void handleAcionamentoDiretoDetector(EventoMotor eventoMotor) {
        Integer detector = (Integer) eventoMotor.getParams()[0];
        //TODO: Fazer o que tem que fazer
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


    private void handleAcionamentoDetector(EventoMotor eventoMotor) {
        Integer anel = (Integer) eventoMotor.getParams()[1];
        motor.getEstagios().get(anel - 1).onEvento(eventoMotor);
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
}
