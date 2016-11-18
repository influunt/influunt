package protocol;



/**
 * Created by rodrigosol on 11/3/16.
 */
public class MensagemInicio extends Mensagem{


    public long grupos;
    public int aneis[] = new int[16];

    public MensagemInicio(TipoDeMensagemBaixoNivel tipoMensagem, Integer sequencia) {
        super(tipoMensagem, sequencia);
    }

    public MensagemInicio(byte[] contents){
        super(contents);
        int index = 0;
        for(int i = 4; i <= 20; i++,index+=2){
            aneis[index] = contents[i] >> 4;
            aneis[index+1] = contents[i] & 0xF;
        }
    }

    @Override
    protected byte[] getBytes() {
        byte[] ret =  new byte[8];
        for(int i =0; i < 16; i+=2){
            ret[i] = (byte) ((aneis[i] << 4) | aneis[i+1]);
        }
        return ret;
    }

    @Override
    public int innerSize() {
        return 8;
    }
    public int[] getAneis(){
        return aneis;
    }
}
