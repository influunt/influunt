package protocol;

/**
 * Created by rodrigosol on 11/3/16.
 */
public class MensagemEstagio extends Mensagem {

    private MensagemGrupo[] mensagemGrupo;

    public MensagemEstagio(TipoDeMensagemBaixoNivel tipoMensagem, Integer sequencia,Integer quantidadeGrupos) {
        super(tipoMensagem, sequencia);
        this.mensagemGrupo = new MensagemGrupo[quantidadeGrupos];
    }

    @Override
    protected int[] getBytes() {
        int[] resp = new int[innerSize()];
        resp[0] = getFlags();
        for(int i = 0; i < mensagemGrupo.length; i++){
            mensagemGrupo[i].fill(i,resp);
        }
        return resp;
    }

    @Override
    public int innerSize() {
        return 1 + mensagemGrupo.length * 5;
    }

    public int getFlags() {
        return mensagemGrupo.length;
    }

    public void addGrupo(int index,int grupo, boolean flagAmareloOuVermelhoIntermitente, boolean flagVerdeOuVermelho,
                         boolean flagPedestreVeicular, int tempoAtrasoDeGrupo, int tempoAmareloOuVermelhoIntermitente,
                         int tempoVermelhoLimpeza, int tempoVerdeOuVermelho){

        mensagemGrupo[index] = new MensagemGrupo(grupo,flagAmareloOuVermelhoIntermitente,flagVerdeOuVermelho,
                                                 flagPedestreVeicular,tempoAtrasoDeGrupo,
                                                 tempoAmareloOuVermelhoIntermitente,tempoVermelhoLimpeza,
                                                 tempoVerdeOuVermelho);
    }
}
