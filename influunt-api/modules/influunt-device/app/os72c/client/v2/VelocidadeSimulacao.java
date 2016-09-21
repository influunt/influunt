package os72c.client.v2;

/**
 * Created by rodrigosol on 9/16/16.
 */
public enum VelocidadeSimulacao {

    TEMPO_REAL(1),
    DOBRADA(2),
    QUADUPLICADA(4),
    OCTUPLICADA(8),
    RESULTADO_FINAL(-1);

    private final int velocidade;

    VelocidadeSimulacao(int velocidade){
        this.velocidade = velocidade;
    }

    public int getVelocidade() {
        return velocidade;
    }


}
