package protocol;

/**
 * Created by rodrigosol on 11/3/16.
 */
public enum TipoDeMensagemBaixoNivel {


    RETORNO((byte)0),
    ESTAGIO((byte)1);

    private final byte tipo;

    TipoDeMensagemBaixoNivel(byte tipo){
        this.tipo = tipo;
    }

}
