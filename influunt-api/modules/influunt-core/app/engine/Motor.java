package engine;

import models.Controlador;
import models.EstadoGrupoSemaforico;
import models.Evento;
import models.Plano;
import org.joda.time.DateTime;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;


/**
 * Created by rodrigosol on 9/26/16.
 */
public class Motor implements MotorEvents {
    private final Controlador controlador;

    private GerenciadorDeEventos gerenciadorDeEventos;
    private GerenciadorDeIntervalos gerenciadorDeIntervalos;
    private List<MotorCallback> callbacks = new ArrayList<>();

    private Evento eventoAtual;
    private List<EstadoGrupoSemaforico> estadoAtual;

    public Motor(Controlador controlador, MotorCallback callback){
        callbacks.add(callback);
        this.controlador = controlador;
        gerenciadorDeEventos = new GerenciadorDeEventos();
        gerenciadorDeEventos.addEventos(controlador.getTabelaHoraria().getEventos());

    }

    public void start(DateTime timestamp){
        callbacks.stream().forEach(motorCallback -> motorCallback.onStart(timestamp));
    }

    public void stop(DateTime timestamp){
        callbacks.stream().forEach(motorCallback -> motorCallback.onStop(timestamp));
    }

    public void onEventoChange(DateTime timestamp, Evento atual, Evento novo){
        callbacks.stream().forEach(motorCallback -> motorCallback.onChangeEvento(timestamp,atual,novo));
    }

    public void onGrupoChange(DateTime timestamp, List<EstadoGrupoSemaforico> atual, List<EstadoGrupoSemaforico> novo){
        callbacks.stream().forEach(motorCallback -> motorCallback.onGrupoChange(timestamp,atual,novo));
    }

    public void tick(DateTime instante){
        Evento evento = gerenciadorDeEventos.eventoAtual(instante);
        boolean iniciarGrupos = false;
        if(eventoAtual == null){
            onEventoChange(instante,null,evento);
            eventoAtual = evento;
            iniciarGrupos = true;
        }else{
            if(!evento.equals(eventoAtual)){
                onEventoChange(instante,eventoAtual,evento);
                eventoAtual = evento;
                iniciarGrupos = true;
            }
        }

        if(iniciarGrupos) {
            List<Plano> planos = controlador.getAneis().stream()
                    .flatMap(anel -> anel.getPlanos().stream())
                    .filter(plano -> plano.getPosicao() == eventoAtual.getPosicaoPlano())
                    .collect(Collectors.toList());

            gerenciadorDeIntervalos = new GerenciadorDeIntervalos(planos);
        }
        if(estadoAtual == null){
            estadoAtual = gerenciadorDeIntervalos.getEstadosGrupo(instante);
            onGrupoChange(instante,null,estadoAtual);
        }else{
            List<EstadoGrupoSemaforico> estadoGrupoSemaforico = gerenciadorDeIntervalos.getEstadosGrupo(instante);
            if(!estadoAtual.equals(estadoGrupoSemaforico)){
                onGrupoChange(instante,estadoAtual,estadoGrupoSemaforico);
                estadoAtual = estadoGrupoSemaforico;
            }
        }
    }


    @Override
    public void onError() {

    }

    @Override
    public void imporPlano(Evento evento) {

    }

    @Override
    public void desimporPlano() {

    }

    @Override
    public void ativarOperacaoManual() {

    }

    @Override
    public void desativarOperacaoManual() {

    }
}
