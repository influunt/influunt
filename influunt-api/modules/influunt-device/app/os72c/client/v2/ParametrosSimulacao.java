package os72c.client.v2;

/**
 * Created by rodrigosol on 9/16/16.
 */
public class ParametrosSimulacao {


    private long dataInicio;

    private long inicioSimulacao;

    private long fimSimulacao;

    private VelocidadeSimulacao velocidadeSimulacao;

    public long getDataInicio() {
        return dataInicio;
    }

    public void setDataInicio(long dataInicio) {
        this.dataInicio = dataInicio;
    }

    public long getInicioSimulacao() {
        return inicioSimulacao;
    }

    public void setInicioSimulacao(long inicioSimulacao) {
        this.inicioSimulacao = inicioSimulacao;
    }

    public long getFimSimulacao() {
        return fimSimulacao;
    }

    public void setFimSimulacao(long fimSimulacao) {
        this.fimSimulacao = fimSimulacao;
    }

    public VelocidadeSimulacao getVelocidadeSimulacao() {
        return velocidadeSimulacao;
    }

    public void setVelocidadeSimulacao(VelocidadeSimulacao velocidadeSimulacao) {
        this.velocidadeSimulacao = velocidadeSimulacao;
    }
}
