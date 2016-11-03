package protocol;



/**
 * Created by rodrigosol on 11/3/16.
 */
public class MensagemRetorno extends Mensagem{


    private final Retorno retorno;

    public MensagemRetorno(TipoDeMensagemBaixoNivel tipoMensagem, Integer sequencia, Retorno retorno) {
        super(tipoMensagem, sequencia);
        this.retorno = retorno;
    }

    @Override
    protected int[] getBytes() {
        int[] ret =  new int[1];
        ret[0] = retorno.ordinal();
        return ret;
    }

    @Override
    public int innerSize() {
        return 1;
    }
}
