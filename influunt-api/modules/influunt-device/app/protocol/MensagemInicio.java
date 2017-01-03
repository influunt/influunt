package protocol;


/**
 * Created by rodrigosol on 11/3/16.
 */
public class MensagemInicio extends Mensagem {


    public long grupos;

    public int aneis[] = new int[16];

    public MensagemInicio(TipoDeMensagemBaixoNivel tipoMensagem, Integer sequencia) {
        super(tipoMensagem, sequencia);
    }

    public MensagemInicio(byte[] contents) {
        super(contents);
    }

    @Override
    protected byte[] getBytes() {
        byte[] ret = new byte[0];
        return ret;
    }

    @Override
    public int innerSize() {
        return 0;
    }


}
