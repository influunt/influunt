package protocol;

/**
 * Created by rodrigosol on 11/3/16.
 */
public class MensagemFalhaGrupoSemaforico extends Mensagem {


    private int posicao;
    private int falha;

    public MensagemFalhaGrupoSemaforico(TipoDeMensagemBaixoNivel tipoMensagem,
                                        Integer sequencia, Integer posicao, Integer falha) {
        super(tipoMensagem, sequencia);
        this.posicao = posicao;
        this.falha = falha;
    }

    public MensagemFalhaGrupoSemaforico(byte[] contents) {
        super(contents);
        posicao = contents[4];
        falha = contents[5];
    }

    @Override
    protected byte[] getBytes() {

        byte[] resp = new byte[innerSize()];
        resp[0] = (byte) posicao;
        resp[1] = (byte) falha;
        return resp;
    }

    @Override
    public int innerSize() {
        return 2;
    }


    public Integer getPosicao() {
        return posicao;
    }

    public Integer getFalha() {
        return falha;
    }
}
