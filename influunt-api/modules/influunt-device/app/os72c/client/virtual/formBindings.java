package os72c.client.virtual;

public class formBindings {
    private String idControlador;

    private String chavePublica;

    private String chavePrivada;

    private String mqtt;

    private String log;

    public formBindings() {
    }

    public String getIdControlador() {
        return idControlador;
    }

    public void setIdControlador(final String idControlador) {
        this.idControlador = idControlador;
    }

    public String getChavePublica() {
        return chavePublica;
    }

    public void setChavePublica(final String chavePublica) {
        this.chavePublica = chavePublica;
    }

    public String getChavePrivada() {
        return chavePrivada;
    }

    public void setChavePrivada(final String chavePrivada) {
        this.chavePrivada = chavePrivada;
    }

    public String getMqtt() {
        return mqtt;
    }

    public void setMqtt(final String mqtt) {
        this.mqtt = mqtt;
    }

    public String getLog() {
        return log;
    }

    public void setLog(final String log) {
        this.log = log;
    }
}
