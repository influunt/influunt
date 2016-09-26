package engine;

import models.Controlador;
import models.Evento;
import org.joda.time.DateTime;

import java.util.ArrayList;
import java.util.List;



/**
 * Created by rodrigosol on 9/26/16.
 */
public class Motor implements MotorEvents {
    private final Controlador controlador;

    private GerenciadorDeEventos gerenciadorDeEventos;
    private GerenciadorDeIntervalos gerenciadorDeIntervalos;
    private List<MotorCallback> callbacks = new ArrayList<>();

    private Evento eventoAtual;

    public Motor(Controlador controlador, MotorCallback callback){
        callbacks.add(callback);
        this.controlador = controlador;
        gerenciadorDeEventos = new GerenciadorDeEventos();
        gerenciadorDeEventos.addEventos(controlador.getTabelaHoraria().getEventos());

    }

    public void start(){
        callbacks.stream().forEach(MotorCallback::onStart);
    }

    public void stop(){
        callbacks.stream().forEach(MotorCallback::onStop);
    }

    public void onEventoChange(Evento atual, Evento novo){
        callbacks.stream().forEach(callback -> callback.onChangeEvento(atual,novo));
    }

    public void onGrupoChange(){
        callbacks.stream().forEach(callback -> callback.onGrupoChange(null,null));
    }

    public void tick(DateTime instante){
        Evento evento = gerenciadorDeEventos.eventoAtual(instante);
        if(eventoAtual == null){
            onEventoChange(null,evento);
            eventoAtual = evento;
        }else{
            if(!evento.equals(eventoAtual)){
                onEventoChange(eventoAtual,evento);
                eventoAtual = evento;
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
