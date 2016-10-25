package models;

/**
 * Created by rodrigosol on 9/15/16.
 */
public enum EstadoGrupoSemaforico {

    DESLIGADO((byte) 0),
    VERDE((byte) 1),
    AMARELO((byte) 2),
    VERMELHO((byte) 3),
    VERMELHO_INTERMITENTE((byte) 4),
    AMARELO_INTERMITENTE((byte) 5),
    VERMELHO_LIMPEZA((byte) 6);

    private byte estado;

    EstadoGrupoSemaforico(byte estado) {
        this.estado = estado;
    }

    public byte asByte() {
        return this.estado;
    }


}
