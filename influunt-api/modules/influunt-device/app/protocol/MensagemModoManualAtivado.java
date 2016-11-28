package protocol;

/**
 * Created by rodrigosol on 11/3/16.
 */
public class MensagemModoManualAtivado extends Mensagem {



    public MensagemModoManualAtivado(TipoDeMensagemBaixoNivel tipoMensagem, Integer sequencia) {
        super(tipoMensagem, sequencia);
    }

    public MensagemModoManualAtivado(byte[] contents) {
        super(contents);
    }

    @Override
    protected byte[] getBytes() {

        byte[] resp = new byte[0];
        return resp;
    }

    @Override
    public int innerSize() {
        return 0;
    }

}
