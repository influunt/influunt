package protocol;

/**
 * Created by rodrigosol on 11/3/16.
 */
public class MensagemAlarme extends Mensagem {



    private int alarme;


    public MensagemAlarme(TipoDeMensagemBaixoNivel tipoMensagem, Integer sequencia, Integer alarme) {
        super(tipoMensagem, sequencia);
        this.alarme = alarme;
    }

    public MensagemAlarme(byte[] contents){
        super(contents);
        alarme = contents[4];
    }

    @Override
    protected byte[] getBytes() {

        byte[] resp = new byte[innerSize()];
        resp[0] = (byte) alarme;
        return resp;
    }

    @Override
    public int innerSize() {
        return 1;
    }

    public Integer getAlarme() {
        return alarme;
    }
}
