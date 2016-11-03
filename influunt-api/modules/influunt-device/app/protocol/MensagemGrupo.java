package protocol;

/**
 * Created by rodrigosol on 11/3/16.
 */
public class MensagemGrupo {

    private int grupo;
    private boolean flagAmareloOuVermelhoIntermitente = false;
    private boolean flagVerdeOuVermelho = false;
    private boolean flagPedestreVeicular = false;
    private int tempoAtrasoDeGrupo = 0;
    private int tempoAmareloOuVermelhoIntermitente = 0;
    private int tempoVermelhoLimpeza = 0;
    private int tempoVerdeOuVermelho = 0;

    public MensagemGrupo(int grupo, boolean flagAmareloOuVermelhoIntermitente, boolean flagVerdeOuVermelho,
                         boolean flagPedestreVeicular, int tempoAtrasoDeGrupo, int tempoAmareloOuVermelhoIntermitente,
                         int tempoVermelhoLimpeza, int tempoVerdeOuVermelho) {

        this.grupo = grupo;
        this.flagAmareloOuVermelhoIntermitente = flagAmareloOuVermelhoIntermitente;
        this.flagVerdeOuVermelho = flagVerdeOuVermelho;
        this.flagPedestreVeicular = flagPedestreVeicular;
        this.tempoAtrasoDeGrupo = tempoAtrasoDeGrupo;
        this.tempoAmareloOuVermelhoIntermitente = tempoAmareloOuVermelhoIntermitente;
        this.tempoVermelhoLimpeza = tempoVermelhoLimpeza;
        this.tempoVerdeOuVermelho = tempoVerdeOuVermelho;
    }

    public void fill(int i, int[] resp) {

        int index = (i * 5) + 1;
        resp[index] = getFlags();
        resp[++index] = tempoAtrasoDeGrupo;
        resp[++index] = tempoAmareloOuVermelhoIntermitente;
        resp[++index] = tempoVermelhoLimpeza;
        resp[++index] = tempoVerdeOuVermelho;

    }

    private int getFlags() {
        int r = flagAmareloOuVermelhoIntermitente ? 1 : 0;
        r = r << 1;
        r |= flagVerdeOuVermelho ? 1 : 0;
        r = r << 1;
        r |= flagPedestreVeicular ? 1 : 0;
        r = r << 5;
        return r | grupo;
    }
}
