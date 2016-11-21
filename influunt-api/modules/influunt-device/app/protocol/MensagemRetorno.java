package protocol;


/**
 * Created by rodrigosol on 11/3/16.
 */
public class MensagemRetorno extends Mensagem {


    private final Retorno retorno;

    public MensagemRetorno(TipoDeMensagemBaixoNivel tipoMensagem, Integer sequencia, Retorno retorno) {
        super(tipoMensagem, sequencia);
        this.retorno = retorno;
    }

    public MensagemRetorno(byte[] contents) {
        super(contents);
        this.retorno = Retorno.values()[contents[4]];
    }

    @Override
    protected byte[] getBytes() {
        byte[] ret = new byte[1];
        ret[0] = (byte) retorno.ordinal();
        return ret;
    }

    @Override
    public int innerSize() {
        return 1;
    }
}
