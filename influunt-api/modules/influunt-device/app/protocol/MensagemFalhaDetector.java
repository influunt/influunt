package protocol;

/**
 * Created by rodrigosol on 11/3/16.
 */
public class MensagemFalhaDetector extends Mensagem {


    private Integer posicao;

    private  Boolean pedestre;

    private int falha;

    public MensagemFalhaDetector(TipoDeMensagemBaixoNivel tipoMensagem,
                                 Integer sequencia, Boolean pedestre, Integer posicao,Integer falha) {
        super(tipoMensagem, sequencia);
        this.pedestre = pedestre;
        this.posicao = posicao;
        this.falha = falha;
    }

    public MensagemFalhaDetector(byte[] contents){
        super(contents);
        pedestre = contents[4] >> 5 == 1;
        posicao = contents[4] & 0x1F;
        falha = contents[5];
    }

    @Override
    protected byte[] getBytes() {

        byte[] resp = new byte[innerSize()];
        resp[0] = (byte) (pedestre ? 1 : 0);
        resp[0] = (byte) (resp[0] << 5);
        resp[0] = (byte) (resp[0] | posicao);
        resp[1] = (byte) falha;
        return resp;
    }

    @Override
    public int innerSize() {
        return 2;
    }


    public boolean isPedestre() {
        return pedestre;
    }

    public Integer getPosicao() {
        return posicao;
    }

    public Integer getFalha() {return  falha;}
}
