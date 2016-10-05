package engine;

import models.EstadoGrupoSemaforico;

import java.util.Arrays;
import java.util.List;

/**
 * Created by rodrigosol on 9/15/16.
 */
public class EstadoGrupoBaixoNivel {
    public static final byte DESLIGADO = 0;

    public static final byte VERDE = 1;

    public static final byte VERMELHO = 2;

    public static final byte AMARELO = 3;

    public static final byte VERMELHO_INTERMITENTE = 4;

    public static final byte AMARELO_INTERMITENTE = 5;

    public static final byte VERMELHO_LIMPEZA = 6;

    private byte[] estadoGlobal = new byte[8];

    public void mudar(int grupo, byte estado) {
        int index = (grupo - 1) / 2;
        if (grupo % 2 != 0) {
            estadoGlobal[index] = (byte) (estado << 4);
        } else {
            estadoGlobal[index] |= estado;
        }
    }

    public byte[] ler() {
        return estadoGlobal;
    }

    public String toString(){
        final StringBuffer sb = new StringBuffer();
        for(int i = 0; i < 8; i++){
            if(i < 7) {
                sb.append(estadoGlobal[i]).append(",");
            }else{
                sb.append(estadoGlobal[i]);
            }
        }
        return sb.toString();
    }

    public static EstadoGrupoBaixoNivel parse(List<EstadoGrupoSemaforico> estadoAtual) {
        EstadoGrupoBaixoNivel estadoGrupoBaixoNivel = new EstadoGrupoBaixoNivel();
        for (int i = 1; i <= estadoAtual.size(); i++) {
            estadoGrupoBaixoNivel.mudar(i, estadoAtual.get(i - 1).asByte());
        }
        return estadoGrupoBaixoNivel;
    }
}
