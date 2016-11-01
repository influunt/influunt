package engine;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by rodrigosol on 11/1/16.
 */
public class MotorEventoHandler {

    private final Motor motor;

    private Map<Integer, EventoMotor> falhasFaseGrupoSemorofico = new HashMap<>();

    public MotorEventoHandler(Motor motor){
        this.motor = motor;
    }

    public void handle(EventoMotor eventoMotor) {

        switch (eventoMotor.getTipoEvento()){

            case ACIONAMENTO_DETECTOR_VEICULAR:
            case ACIONAMENTO_DETECTOR_PEDESTRE:
                handleAcionamentoDetector(eventoMotor);
                break;

            case ALARME_ABERTURA_DA_PORTA_PRINCIPAL_DO_CONTROLADOR:
            case ALARME_FECHAMENTO_DA_PORTA_PRINCIPAL_DO_CONTROLADOR:
            case ALARME_ABERTURA_DA_PORTA_DO_PAINEL_DE_FACILIDADES_DO_CONTROLADOR:
            case ALARME_FECHAMENTO_DA_PORTA_DO_PAINEL_DE_FACILIDADES_DO_CONTROLADOR:
            case ALARME_INSERCAO_DE_PLUG:
            case ALARME_RETIRADA_DO_PLUG:
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
            case FALHA_DETECTOR_VEICULAR_ACIONAMENTO_DIRETO:
            case FALHA_DETECTOR_PEDESTRE_FALTA_ACIONAMENTO:
            case FALHA_DETECTOR_PEDESTRE_ACIONAMENTO_DIRETO:
                handleFalhaDetector(eventoMotor);
                break;

            case FALHA_DESRESPEITO_AO_TEMPO_MAXIMO_DE_PERMANENCIA_NO_ESTAGIO:
            case FALHA_VERDES_CONFLITANTES:
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

    private void handleFalhaAnel(EventoMotor eventoMotor) {
        Integer anel = (Integer) eventoMotor.getParams()[0];
        //TODO: Fazer o que tem que fazer
    }

    private void handleFalhaDetector(EventoMotor eventoMotor) {
        Integer detector = (Integer) eventoMotor.getParams()[0];
        //TODO: Fazer o que tem que fazer
    }


    private void handleFaseVermelhaGrupoSemaforico(EventoMotor eventoMotor) {
        Integer grupoSemaforico = (Integer) eventoMotor.getParams()[0];
        if(eventoMotor.getTipoEvento().equals(TipoEvento.FALHA_FASE_VERMELHA_DE_GRUPO_SEMAFORICO_APAGADA)){
            if(!falhasFaseGrupoSemorofico.containsKey(grupoSemaforico)){
                falhasFaseGrupoSemorofico.put(grupoSemaforico,eventoMotor);
                //Todo:Faz o que tem que fazer
            }

        }else{
            falhasFaseGrupoSemorofico.remove(grupoSemaforico);
            //Todo:Faz o que tem que fazer
        }
    }


    private void handleAcionamentoDetector(EventoMotor eventoMotor) {
        Integer anel = (Integer) eventoMotor.getParams()[1];
        motor.getEstagios().get(anel - 1).onEvento(eventoMotor);
    }
}
