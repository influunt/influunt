package protocol;

/**
 * Created by rodrigosol on 11/3/16.
 */
public abstract class Mensagem {

    protected byte[] contents;

    private TipoDeMensagemBaixoNivel tipoMensagem;
    private int sequencia;
    private int checksum;
    private int tamanho;

    private Integer codigo;

    public Mensagem(TipoDeMensagemBaixoNivel tipoMensagem, Integer sequencia){
        this.tipoMensagem = tipoMensagem;
        this.sequencia = sequencia;
    }

    public Mensagem(byte[] contents){
        this.contents = contents;
        tamanho = 0xFF & contents[0];
        tipoMensagem = TipoDeMensagemBaixoNivel.values()[contents[1]];
        sequencia = contents[2] << 8;
        sequencia = sequencia | contents[3];
    }


    protected abstract byte[] getBytes();

    public int size(){
        return 5 + innerSize();
    }

    public abstract int innerSize();

    public byte[] toByteArray(){
        byte[] resp = new byte[size()];
        resp[0] = (byte) size();
        resp[1] = (byte) tipoMensagem.ordinal();

        resp[2] = (byte) ((sequencia & 0xff) >> 8 );
        resp[3] = (byte) ((sequencia & 0xff) & 0x00FF);

        byte[] innerMsg = getBytes();
        for(int i = 0; i < innerMsg.length; i++){
            resp[i+4] = innerMsg[i];
        }

        resp[resp.length - 1] = (byte) LRC.calculateLRC(resp);
        return resp;
    }

    public static Mensagem toMensagem(byte[] contents) throws Exception{

        return TipoDeMensagemBaixoNivel.values()[contents[1]].getInstance(contents);
    }

    public int getSequencia() {
        return sequencia;
    }

    public TipoDeMensagemBaixoNivel getTipoMensagem() {
        return tipoMensagem;
    }

    public Integer getCodigo() {
        return codigo;
    }
}
