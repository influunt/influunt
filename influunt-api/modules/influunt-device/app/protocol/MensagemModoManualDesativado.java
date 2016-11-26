package protocol;

/**
 * Created by rodrigosol on 11/3/16.
 */
public class MensagemModoManualDesativado extends Mensagem {


    public MensagemModoManualDesativado(TipoDeMensagemBaixoNivel tipoMensagem, Integer sequencia) {
        super(tipoMensagem, sequencia);
    }

    public MensagemModoManualDesativado(byte[] contents) {
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
