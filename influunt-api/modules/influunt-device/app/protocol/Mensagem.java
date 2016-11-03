package protocol;

/**
 * Created by rodrigosol on 11/3/16.
 */
public abstract class Mensagem {

    private TipoDeMensagemBaixoNivel tipoMensagem;
    private int sequencia;
    private int checksum;

    public Mensagem(TipoDeMensagemBaixoNivel tipoMensagem, Integer sequencia){
        this.tipoMensagem = tipoMensagem;
        this.sequencia = sequencia;
    }


    protected abstract int[] getBytes();

    public int size(){
        return 3 + innerSize();
    }

    public abstract int innerSize();

    public byte[] toByteArray(){
        byte[] resp = new byte[size()];
        resp[0] = (byte) tipoMensagem.ordinal();
        resp[1] = (byte) sequencia;
        int[] innerMsg = getBytes();

        for(int i =0; i < innerMsg.length; i++){
            resp[i+2] = (byte) innerMsg[i];
        }

        resp[resp.length - 1] = (byte) checksum;
        return resp;
    }

}
