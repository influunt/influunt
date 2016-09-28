package os72c.client.v2;

import models.Plano;

import java.util.List;

/**
 * Created by rodrigosol on 9/16/16.
 */
public class SimulacaoBuilder {

    private ParametrosSimulacao params = new ParametrosSimulacao();

    private List<Plano> planos;

    public SimulacaoBuilder dataInicio(long momentoInicio) {
        params.setDataInicio(momentoInicio);
        return this;
    }

    public SimulacaoBuilder inicioSimulacao(long inicioSimulacao) {
        params.setInicioSimulacao(inicioSimulacao);
        return this;
    }

    public SimulacaoBuilder fimSimulacao(long fimSimulacao) {
        params.setFimSimulacao(fimSimulacao);
        return this;
    }


    public SimulacaoBuilder velocidadeSimulacao(VelocidadeSimulacao velocidadeSimulacao) {
        params.setVelocidadeSimulacao(velocidadeSimulacao);
        return this;
    }

    public Simulador build() {
        if (params.getFimSimulacao() - params.getInicioSimulacao() < 0) {
            throw new RuntimeException("Simulacao com parametros invalidos");
        }
        return new Simulador(planos, params);
    }

    public SimulacaoBuilder planos(List<Plano> planos) {
        this.planos = planos;
        return this;

    }
}
