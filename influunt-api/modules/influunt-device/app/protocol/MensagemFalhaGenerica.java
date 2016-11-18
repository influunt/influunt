package protocol;

/**
 * Created by rodrigosol on 11/3/16.
 */
public class MensagemFalhaGenerica extends Mensagem {



    private int falha;


    public MensagemFalhaGenerica(TipoDeMensagemBaixoNivel tipoMensagem, Integer sequencia, Integer falha) {
        super(tipoMensagem, sequencia);
        this.falha = falha;
    }

    public MensagemFalhaGenerica(byte[] contents){
        super(contents);
        falha = contents[4];
    }

    @Override
    protected byte[] getBytes() {

        byte[] resp = new byte[innerSize()];
        resp[0] = (byte) falha;
        return resp;
    }

    @Override
    public int innerSize() {
        return 1;
    }

    public Integer getFalha() {
        return falha;
    }
}
