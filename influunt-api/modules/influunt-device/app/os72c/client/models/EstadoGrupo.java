package os72c.client.models;

public enum EstadoGrupo {
    DESLIGADO((byte) 0),
    VERDE((byte) 1),
    AMARELO((byte) 2),
    VERMELHO((byte) 3),
    VERMELHO_INTERMITENTE((byte) 4),
    AMARELHO_INTERMITENTE((byte) 5),
    VERMELHO_LIMPEZA((byte) 6);

    private final byte status;

    EstadoGrupo(byte status) {
        this.status = status;
    }

    byte value() {
        return status;
    }
}




