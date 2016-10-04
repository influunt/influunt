//package simulacao.parametros;
//
//import engine.EventoMotor;
//import engine.TipoEvento;
//import models.TipoDetector;
//import org.joda.time.DateTime;
//
///**
// * Created by rodrigosol on 10/4/16.
// */
//public class ParametroSimulacaoImposicaoPlano {
//    private int plano;
//    private DateTime disparo;
//
//    public int getPlano() {
//        return plano;
//    }
//
//    public void setPlano(int plano) {
//        this.plano = plano;
//    }
//
//    public DateTime getDisparo() {
//        return disparo;
//    }
//
//    public void setDisparo(DateTime disparo) {
//        this.disparo = disparo;
//    }
//
//    public EventoMotor toEvento(){
//        return new EventoMotor(disparo, TipoEvento.IMPOSICAO_PLANO, plano);
//    }
//
//}
