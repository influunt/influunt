//package simulacao.akka;
//
//import akka.actor.ActorRef;
//import engine.EstadoGrupoBaixoNivel;
//import engine.MotorCallback;
//import models.EstadoGrupoSemaforico;
//import models.Evento;
//import org.apache.commons.math3.util.Pair;
//import org.joda.time.DateTime;
//import simulacao.Simulador;
//import simulacao.parametros.ParametroSimulacao;
//
//import java.util.List;
//
///**
// * Created by rodrigosol on 10/4/16.
// */
//public class SimuladorAkka extends Simulador {
//
//    private final ActorRef actor;
//
//    private final ParametroSimulacao parametros;
//
//    private DateTime ponteiro;
//
//    public SimuladorAkka(ActorRef actor, ParametroSimulacao parametros){
//        super(parametros.getControlador());
//        this.actor = actor;
//        this.parametros = parametros;
//        this.parametros.getDetectores().stream().forEach(param -> addEvento(param.toEvento()));
//        this.parametros.getImposicoes().stream().forEach(param -> addEvento(param.toEvento()));
//        this.parametros.getFalhas().stream().forEach(param -> addEvento(param.toEvento()));
//        this.ponteiro = parametros.getInicioSimulacao();
//
//    }
//
//    public void avancar(int segundos){
//        DateTime fim = ponteiro.plusSeconds(segundos);
//        simular(ponteiro,fim);
//        ponteiro = fim;
//    }
//
//    @Override
//    public void onStart(DateTime timestamp) {
//
//    }
//
//    @Override
//    public void onStop(DateTime timestamp) {
//
//    }
//
//    @Override
//    public void onChangeEvento(DateTime timestamp, Evento eventoAntigo, Evento eventoNovo) {
//
//    }
//
//    @Override
//    public void onGrupoChange(DateTime timestamp, List<EstadoGrupoSemaforico> estadoAntigo, List<EstadoGrupoSemaforico> estadoNovo) {
//
//    }
//
//    @Override
//    public void onAgendamentoTrocaDePlanos(DateTime timestamp, DateTime momentoAgendamento, int anel, int plano, int planoAnterior) {
//
//    }
//
//    @Override
//    public void onEstado(DateTime timeStamp, EstadoGrupoBaixoNivel estado) {
//        actor.tell(new Pair<Long,EstadoGrupoBaixoNivel>(timeStamp.getMillis(),estado),null);
//    }
//
//}
