package protocol;

/**
 * Created by rodrigosol on 11/3/16.
 */
public class MensagemDetector extends Mensagem {


    private Integer posicao;

    private Boolean pedestre;

    public MensagemDetector(TipoDeMensagemBaixoNivel tipoMensagem, Integer sequencia, Boolean pedestre, Integer codigo) {
        super(tipoMensagem, sequencia);
        this.pedestre = pedestre;
        this.posicao = codigo;
    }

    public MensagemDetector(byte[] contents) {
        super(contents);
        pedestre = contents[4] >> 5 == 1;
        posicao = contents[4] & 0x1F;
    }

    @Override
    protected byte[] getBytes() {

        byte[] resp = new byte[innerSize()];
        resp[0] = (byte) (pedestre ? 1 : 0);
        resp[0] = (byte) (resp[0] << 5);
        resp[0] = (byte) (resp[0] | posicao);
        return resp;
    }

    @Override
    public int innerSize() {
        return 1;
    }


    public boolean isPedestre() {
        return pedestre;
    }

    public Integer getPosicao() {
        return posicao;
    }
}
