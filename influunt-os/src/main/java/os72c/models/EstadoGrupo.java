package os72c.models;

public enum EstadoGrupo {
    DESLIGADO((byte)0),
    VERDE((byte)1),
    AMARELHO((byte)2),
    VERMELHO((byte)3),
    VERMELHO_INTERMITENTE((byte)4);

    private final byte status;

    EstadoGrupo(byte status){
        this.status = status;
    }

    byte value(){
        return status;
    }
}




