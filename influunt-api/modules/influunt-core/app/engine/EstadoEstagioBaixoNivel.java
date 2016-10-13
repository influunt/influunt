package engine;

import models.EstadoGrupoSemaforico;
import org.apache.commons.math3.util.Pair;
import org.joda.time.DateTime;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by rodrigosol on 9/15/16.
 */
public enum EstadoEstagioBaixoNivel {
    VERDE,
    ENTREVERDE;


    private int estadoGlobal = 0;

    public void mudar(int anel, int estagio, EstadoEstagioBaixoNivel estado) {
        int meuEstado = estagio << 1 | estado.ordinal();
        int mask = (31 & meuEstado) << (anel * 5);
        estadoGlobal |= mask;
    }

    public List<Pair<Integer,EstadoEstagioBaixoNivel>> decode() {
        List<Pair<Integer,EstadoEstagioBaixoNivel>> estagios = new ArrayList<>();
        for(int i = 0; i < 20; i+=5){
            int estado = estadoGlobal >> i;
            estado &= 31;

            estagios.add(EstadoEstagioBaixoNivel.decode(estado));
        }
        return  estagios;
    }

    private static Pair<Integer,EstadoEstagioBaixoNivel> decode(int estado) {
        int v = estado & 1;
        EstadoEstagioBaixoNivel e = (v == 0) ? VERDE : ENTREVERDE;
        v = estado >> 1;
        return new Pair<Integer, EstadoEstagioBaixoNivel>(v,e);
    }


//    public String toString(){
//        return String.valueOf(estadoGlobal);
//    }

    public String toJson(DateTime timeStamp) {
        return "{\"timestamp\":"+timeStamp.getMillis() / 1000 +",\"estado\":\""+this.toString()+"\"}";
    }

    public int getValue() {
        return estadoGlobal;
    }
}
