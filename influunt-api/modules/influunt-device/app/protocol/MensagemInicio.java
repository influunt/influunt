package protocol;


/**
 * Created by rodrigosol on 11/3/16.
 */
public class MensagemInicio extends Mensagem {


    public long grupos;

    public int aneis[];

    public MensagemInicio(TipoDeMensagemBaixoNivel tipoMensagem, Integer sequencia, int[] aneis) {
        super(tipoMensagem, sequencia);
        this.aneis = aneis;
    }

    @Override
    protected byte[] getBytes() {
        byte[] ret = new byte[8];
        for (int i = 0; i < 8; i++) {
            ret[i] = (byte) ((aneis[i * 2] << 4) + (aneis[i * 2 + 1]));
        }

        return ret;
    }

    @Override
    public int innerSize() {
        return 8;
    }


}
