package execucao;

import engine.EstadoEstagioBaixoNivel;
import org.junit.Test;


/**
 * Created by rodrigosol on 9/8/16.
 */
public class EstadoDosEstagiosTest {

    @Test
    public void coversaoBinariaTest() {
        EstadoEstagioBaixoNivel estadoEstagioBaixoNivel = EstadoEstagioBaixoNivel.VERDE;
        estadoEstagioBaixoNivel.mudar(2, 14, EstadoEstagioBaixoNivel.ENTREVERDE);
        estadoEstagioBaixoNivel.mudar(1, 13, EstadoEstagioBaixoNivel.ENTREVERDE);
        estadoEstagioBaixoNivel.mudar(3, 12, EstadoEstagioBaixoNivel.ENTREVERDE);
        estadoEstagioBaixoNivel.mudar(0, 10, EstadoEstagioBaixoNivel.VERDE);
        System.out.println(estadoEstagioBaixoNivel.getValue());
        System.out.println(estadoEstagioBaixoNivel.decode());
    }
}
