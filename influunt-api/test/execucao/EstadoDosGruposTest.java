package execucao;

import engine.EstadoGrupoBaixoNivel;
import org.junit.Test;

import static org.junit.Assert.assertEquals;


/**
 * Created by rodrigosol on 9/8/16.
 */
public class EstadoDosGruposTest {

    @Test
    public void coversaoBinariaTest() {
        EstadoGrupoBaixoNivel cm = new EstadoGrupoBaixoNivel();
        assertEquals((byte) 0, cm.ler()[0]);

        cm.mudar(1, EstadoGrupoBaixoNivel.AMARELO);
        assertEquals((byte) 48, cm.ler()[0]);

        cm.mudar(2, EstadoGrupoBaixoNivel.AMARELO_INTERMITENTE);
        assertEquals((byte) 53, cm.ler()[0]);

        cm.mudar(3, EstadoGrupoBaixoNivel.VERDE);
        cm.mudar(4, EstadoGrupoBaixoNivel.VERMELHO);

        assertEquals((byte) 53, cm.ler()[0]);
        assertEquals((byte) 18, cm.ler()[1]);

        cm.mudar(15, EstadoGrupoBaixoNivel.VERMELHO_INTERMITENTE);
        cm.mudar(16, EstadoGrupoBaixoNivel.DESLIGADO);

        assertEquals((byte) 53, cm.ler()[0]);
        assertEquals((byte) 18, cm.ler()[1]);
        assertEquals((byte) 64, cm.ler()[7]);

    }
}
