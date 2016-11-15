package protocol;

import com.google.common.collect.Range;
import com.google.common.collect.RangeMap;
import models.EstadoGrupoSemaforico;

import java.util.Formatter;
import java.util.Map;

/**
 * Created by rodrigosol on 11/3/16.
 */
public class MensagemGrupo {

    private int grupo;
    private boolean flagPedestreVeicular = false;
    private FlagUltimoTempo flagUltimoTempo;
    private int tempoAtrasoDeGrupo = 0;
    private int tempoAmareloOuVermelhoIntermitente = 0;
    private int tempoVermelhoLimpeza = 0;
    private int tempoVerdeOuVermelho = 0;

    public MensagemGrupo(int grupo, int tempoAtrasoDeGrupo, int tempoAmareloOuVermelhoIntermitente,
                         int tempoVermelhoLimpeza, int tempoVerdeOuVermelho) {

        this.grupo = grupo;
        this.tempoAtrasoDeGrupo = tempoAtrasoDeGrupo;
        this.tempoAmareloOuVermelhoIntermitente = tempoAmareloOuVermelhoIntermitente;
        this.tempoVermelhoLimpeza = tempoVermelhoLimpeza;
        this.tempoVerdeOuVermelho = tempoVerdeOuVermelho;
    }

    public MensagemGrupo(Integer grupo, RangeMap<Long, EstadoGrupoSemaforico> estadoGrupos) {
        this.grupo = grupo;
        parseEstadoGrupos(estadoGrupos);
    }


    private void parseEstadoGrupos(RangeMap<Long, EstadoGrupoSemaforico> estadoGrupos) {
        boolean first = true;
        for (Map.Entry<Range<Long>, EstadoGrupoSemaforico> entry : estadoGrupos.asMapOfRanges().entrySet()) {
            switch (entry.getValue()) {
                case DESLIGADO:
                    tempoVerdeOuVermelho += (int) (entry.getKey().upperEndpoint() - entry.getKey().lowerEndpoint());
                    flagUltimoTempo = FlagUltimoTempo.DESLIGADO;
                    break;
                case VERDE:
                    if (first && !EstadoGrupoSemaforico.VERDE.equals(estadoGrupos.get(entry.getKey().upperEndpoint() + 1)) && estadoGrupos.asMapOfRanges().size() > 1) {
                        tempoAtrasoDeGrupo += (int) (entry.getKey().upperEndpoint() - entry.getKey().lowerEndpoint());
                    } else {
                        tempoVerdeOuVermelho += (int) (entry.getKey().upperEndpoint() - entry.getKey().lowerEndpoint());
                        flagUltimoTempo = FlagUltimoTempo.VERDE;
                    }
                    break;
                case AMARELO:
                    tempoAmareloOuVermelhoIntermitente += (int) (entry.getKey().upperEndpoint() - entry.getKey().lowerEndpoint());
                    flagPedestreVeicular = false;
                    break;
                case VERMELHO_INTERMITENTE:
                    tempoAmareloOuVermelhoIntermitente += (int) (entry.getKey().upperEndpoint() - entry.getKey().lowerEndpoint());
                    flagPedestreVeicular = true;
                    break;
                case VERMELHO:
                    if (first && estadoGrupos.asMapOfRanges().size() > 1) {
                        tempoAtrasoDeGrupo += (int) (entry.getKey().upperEndpoint() - entry.getKey().lowerEndpoint());
                    } else {
                        tempoVerdeOuVermelho += (int) (entry.getKey().upperEndpoint() - entry.getKey().lowerEndpoint());
                        flagUltimoTempo = FlagUltimoTempo.VERMELHO;
                    }
                    break;
                case AMARELO_INTERMITENTE:
                    tempoVerdeOuVermelho += (int) (entry.getKey().upperEndpoint() - entry.getKey().lowerEndpoint());
                    flagUltimoTempo = FlagUltimoTempo.AMARELO_INTERMITENTE;
                    break;
                case VERMELHO_LIMPEZA:
                    tempoVermelhoLimpeza += (int) (entry.getKey().upperEndpoint() - entry.getKey().lowerEndpoint());
                    break;
            }
            first = false;
        }
    }

    public void fill(int i, byte[] resp) {

        int index = (i * 9) + 1;
        resp[index] = getFlags();
        resp[++index] = (byte) (tempoAtrasoDeGrupo >> 8);
        resp[++index] = (byte) (tempoAtrasoDeGrupo & 0x00FF);

        resp[++index] = (byte) (tempoAmareloOuVermelhoIntermitente >> 8);
        resp[++index] = (byte) (tempoAmareloOuVermelhoIntermitente & 0x00FF);

        resp[++index] = (byte) (tempoVermelhoLimpeza >> 8);
        resp[++index] = (byte) (tempoVermelhoLimpeza & 0x00FF);

        resp[++index] = (byte) (tempoVerdeOuVermelho >> 8);
        resp[++index] = (byte) (tempoVerdeOuVermelho & 0x00FF);

    }

    public MensagemGrupo(int i, byte[] contents) {
        int index = (i * 9) + 5;
        setFlags(contents[index]);

        tempoAtrasoDeGrupo = (contents[++index] & 0xff) << 8;
        tempoAtrasoDeGrupo |= (contents[++index] & 0xff);

        tempoAmareloOuVermelhoIntermitente = (contents[++index] & 0xff) << 8;
        tempoAmareloOuVermelhoIntermitente |= (contents[++index] & 0xff);

        tempoVermelhoLimpeza = (contents[++index] & 0xff) << 8;
        tempoVermelhoLimpeza |= (contents[++index] & 0xff);

        tempoVerdeOuVermelho = (contents[++index] & 0xff) << 8;
        tempoVerdeOuVermelho |= (contents[++index] & 0xff);

    }

    private void setFlags(byte content) {
        flagPedestreVeicular = (content & 0x80) == 0 ? false : true;
        flagUltimoTempo = FlagUltimoTempo.values()[(content & 0x60) >> 5];
        grupo = content & 0x1F;
    }

    private byte getFlags() {
        int r = flagPedestreVeicular ? 1 : 0;
        r = r << 2;
        r |= flagUltimoTempo.ordinal();
        r = r << 5;
        return (byte) (r | grupo);
    }

    public int getTempoVerdeOuVermelho() {
        return tempoVerdeOuVermelho;
    }

    public void setTempoVerdeOuVermelho(int tempoVerdeOuVermelho) {
        this.tempoVerdeOuVermelho = tempoVerdeOuVermelho;
    }

    public int getTempoVermelhoLimpeza() {
        return tempoVermelhoLimpeza;
    }

    public void setTempoVermelhoLimpeza(int tempoVermelhoLimpeza) {
        this.tempoVermelhoLimpeza = tempoVermelhoLimpeza;
    }

    public int getTempoAmareloOuVermelhoIntermitente() {
        return tempoAmareloOuVermelhoIntermitente;
    }

    public void setTempoAmareloOuVermelhoIntermitente(int tempoAmareloOuVermelhoIntermitente) {
        this.tempoAmareloOuVermelhoIntermitente = tempoAmareloOuVermelhoIntermitente;
    }

    public int getTempoAtrasoDeGrupo() {
        return tempoAtrasoDeGrupo;
    }

    public void setTempoAtrasoDeGrupo(int tempoAtrasoDeGrupo) {
        this.tempoAtrasoDeGrupo = tempoAtrasoDeGrupo;
    }

    public boolean isFlagPedestreVeicular() {
        return flagPedestreVeicular;
    }

    public void setFlagPedestreVeicular(boolean flagPedestreVeicular) {
        this.flagPedestreVeicular = flagPedestreVeicular;
    }


    public int getGrupo() {
        return grupo;
    }

    public void setGrupo(int grupo) {
        this.grupo = grupo;
    }

    @Override
    public String toString() {
        String pv = (flagPedestreVeicular ? "P" : "V");

        return new Formatter().format("|%2d|%1s|%1d|%6d|%6d|%6d|%6d|", grupo, pv, flagUltimoTempo.ordinal(),
            tempoAtrasoDeGrupo, tempoAmareloOuVermelhoIntermitente, tempoVermelhoLimpeza, tempoVerdeOuVermelho).toString();
    }
}
