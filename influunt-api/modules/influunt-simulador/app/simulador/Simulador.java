package simulador;

import engine.EventoMotor;
import engine.Motor;
import engine.MotorCallback;
import models.*;
import org.apache.commons.math3.util.Pair;
import org.joda.time.DateTime;
import simulador.eventos.AlteracaoEstadoLog;
import simulador.eventos.EventoLog;
import simulador.eventos.LogSimulacao;
import simulador.eventos.TipoEventoLog;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * Created by rodrigosol on 9/28/16.
 */
public abstract class Simulador implements MotorCallback {
    private Controlador controlador;

    protected DateTime dataInicioControlador;

    private DateTime inicio;

    private DateTime fim;

    private Map<DateTime, List<EventoMotor>> eventos = new HashMap<>();

    private LogSimulacao logSimulacao = new LogSimulacao();

    private Motor motor;

    private int tempoSimulacao = 0;

    public Simulador(DateTime inicioSimulado,Controlador controlador) {
        this.controlador = controlador;
        this.dataInicioControlador = inicioSimulado;
        motor = new Motor(controlador, this, inicioSimulado);
    }

    public void setControlador(Controlador controlador) {
        this.controlador = controlador;
    }

    public void setDataInicioControlador(DateTime dataInicioControlador) {
        this.dataInicioControlador = dataInicioControlador;
    }

    public void setInicio(DateTime inicio) {
        this.inicio = inicio;
    }

    public void setFim(DateTime fim) {
        this.fim = fim;
    }

    public void addEvento(EventoMotor eventoMotor) {
        if (!this.eventos.containsKey(eventoMotor.getTimestamp())) {
            this.eventos.put(eventoMotor.getTimestamp(), new ArrayList<>());
        }
        this.eventos.get(eventoMotor.getTimestamp()).add(eventoMotor);
    }

    public Detector getDetector(TipoDetector tipoDetector, int detector) {
        return controlador.getDetectores().stream()
                .filter(detector1 -> detector1.getTipo().equals(tipoDetector) && detector1.getPosicao().equals(detector))
                .findFirst().orElse(null);
    }

    public List<Plano> getPlano(int plano) {
        return controlador.getAneis().stream()
                .flatMap(anel -> anel.getPlanos().stream()).filter(plano1 -> plano1.getPosicao().equals(plano))
                .collect(Collectors.toList());
    }

    public void simular(DateTime inicio, DateTime fim) {
        DateTime inicioSimulacao = inicio;
        while (inicioSimulacao.getMillis() / 1000 < fim.getMillis() / 1000) {
            motor.tick(inicioSimulacao);
            tempoSimulacao++;
            inicioSimulacao = inicioSimulacao.plusSeconds(1);
        }
    }

    private void processaEventos(DateTime inicio) {
        if(eventos.containsKey(inicio)) {
            eventos.get(inicio).stream().forEach(eventoMotor -> motor.onEvento(eventoMotor));
        }
    }


    public int getTempoSimulacao() {
        return tempoSimulacao;
    }

    public EventoLog findInLog(TipoEventoLog tipoEventoLog, DateTime timestamp, int planoAnterior, int planoAtual, int anel) {
        return logSimulacao.filter(tipoEventoLog,timestamp,planoAnterior,planoAtual,anel);
    }

    public Stream<EventoLog> findInLog(TipoEventoLog alteracaoEstado, DateTime timestamp) {
        return logSimulacao.find(alteracaoEstado,timestamp);
    }

    public EventoLog findFirstInLog(TipoEventoLog alteracaoEstado, DateTime timestamp) {
        return findInLog(alteracaoEstado,timestamp).findFirst().orElse(null);
    }

    public LogSimulacao getLogSimulacao() {
        return logSimulacao;
    }

    public int getQuantidadeGruposSemaforicos() {
        return 2;
    }

    public Map<Integer, java.util.List<Pair<DateTime,Intervalo>>> getIntervalos() {

        Map<Integer, java.util.List<Pair<DateTime,Intervalo>>>  mapa = new HashMap<>();


        List<EventoLog> eventos = logSimulacao.find(TipoEventoLog.ALTERACAO_ESTADO).collect(Collectors.toList());
        DateTime dataInicio = eventos.get(0).getTimeStamp();

        for(EventoLog eventoLog : eventos){
            AlteracaoEstadoLog alteracaoEstadoLog = (AlteracaoEstadoLog) eventoLog;
            int tamanho = (int) (alteracaoEstadoLog.getTimeStamp().getMillis() / 1000 -  dataInicio.getMillis() / 1000);
            dataInicio = alteracaoEstadoLog.getTimeStamp();


            for(int i = 1 ; i <= alteracaoEstadoLog.getAtual().size(); i++){
                if(i > 120){
                    System.out.println("P");
                }
                if(!mapa.containsKey(i)){
                    mapa.put(i,new ArrayList<>());
                }
                if(mapa.get(i).isEmpty()){
                    Intervalo intervalo = new Intervalo();
                    intervalo.setTamanho(tamanho);
                    intervalo.setEstadoGrupoSemaforico(alteracaoEstadoLog.getAtual().get(i - 1));
                    mapa.get(i).add(new Pair<DateTime, Intervalo>(alteracaoEstadoLog.getTimeStamp(),intervalo));
                }else{
                    Intervalo ultimo =  mapa.get(i).get(mapa.get(i).size() -1).getSecond();
                    if(ultimo.getEstadoGrupoSemaforico().equals(alteracaoEstadoLog.getAtual().get(i -1))){
                        ultimo.setTamanho(ultimo.getTamanho() + tamanho);
                    }else{
                        Intervalo intervalo = new Intervalo();
                        intervalo.setTamanho(tamanho);
                        ultimo.setTamanho(ultimo.getTamanho() + tamanho);
                        intervalo.setEstadoGrupoSemaforico(alteracaoEstadoLog.getAtual().get(i - 1));
                        mapa.get(i).add(new Pair<DateTime, Intervalo>(alteracaoEstadoLog.getTimeStamp(),intervalo));

                    }
                }
            }
        }

        return mapa;
    }

    public List<EventoLog> getMudancaEventos() {
        return logSimulacao.find(TipoEventoLog.ALTERACAO_EVENTO).collect(Collectors.toList());
    }

    public DateTime getInicio() {
        return inicio;
    }

    public DateTime getFim() {
        return fim;
    }
}
