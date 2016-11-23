package protocol;

/**
 * Created by rodrigosol on 11/3/16.
 */
public class MensagemInsercaoPlug extends Mensagem {



    public MensagemInsercaoPlug(TipoDeMensagemBaixoNivel tipoMensagem, Integer sequencia) {
        super(tipoMensagem, sequencia);
    }

    public MensagemInsercaoPlug(byte[] contents) {
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
