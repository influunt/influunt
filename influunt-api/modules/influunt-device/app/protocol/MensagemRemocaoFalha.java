package protocol;

/**
 * Created by rodrigosol on 11/3/16.
 */
public class MensagemRemocaoFalha extends Mensagem {


    private int anel;

    private int falha;


    public MensagemRemocaoFalha(TipoDeMensagemBaixoNivel tipoMensagem, Integer sequencia, Integer anel, Integer falha) {
        super(tipoMensagem, sequencia);
        this.anel = anel;
        this.falha = falha;
    }

    public MensagemRemocaoFalha(byte[] contents) {
        super(contents);
        anel = contents[4];
        falha = contents[5];
    }

    @Override
    protected byte[] getBytes() {

        byte[] resp = new byte[innerSize()];
        resp[0] = (byte) anel;
        resp[1] = (byte) falha;
        return resp;
    }

    @Override
    public int innerSize() {
        return 2;
    }


    public Integer getAnel() {
        return anel;
    }

    public Integer getFalha() {
        return falha;
    }
}
