package execucao.controladorVirtual;


import engine.EventoMotor;
import engine.Motor;
import engine.TipoEvento;
import execucao.GerenciadorDeTrocasTest;
import models.EstadoGrupoSemaforico;
import models.ModoOperacaoPlano;
import org.joda.time.DateTime;
import org.junit.Test;
import protocol.MensagemEstagio;
import protocol.MensagemGrupo;
import protocol.TipoDeMensagemBaixoNivel;

import java.io.IOException;

import static org.junit.Assert.*;


/**
 * Created by rodrigosol on 9/8/16.
 */
public class ImposicaoDePlanosTest extends GerenciadorDeTrocasTest {


    @Test
    public void imporModoIntermitente() throws IOException {
        inicioExecucao = new DateTime(2016, 10, 20, 0, 0, 0);
        Motor motor = new Motor(controlador, inicioExecucao, this);


        avancarSegundos(motor, 10);
        motor.onEvento(new EventoMotor(inicioExecucao.plusSeconds(60),
            TipoEvento.IMPOSICAO_MODO,
            ModoOperacaoPlano.INTERMITENTE.toString(),
            1,
            240,
            inicioExecucao.plusSeconds(60).getMillis()));

        avancarSegundos(motor, 110);

        MensagemEstagio mensagemEstagio = new MensagemEstagio(TipoDeMensagemBaixoNivel.ESTAGIO, 1, 5);
        mensagemEstagio.addIntervalos(listaEstagios.get(inicioExecucao.plusSeconds(70)).get(1));

        System.out.println(mensagemEstagio.print());

        MensagemGrupo[] mensagemGrupo = mensagemEstagio.getMensagemGrupo();

        checkGrupo(mensagemGrupo, 1, "V", 3, 0, 3000, 3000, 249000);
        checkGrupo(mensagemGrupo, 2, "V", 3, 8000, 0, 0, 247000);
        checkGrupo(mensagemGrupo, 3, "V", 0, 8000, 0, 0, 247000);
        checkGrupo(mensagemGrupo, 4, "V", 0, 8000, 0, 0, 247000);
        checkGrupo(mensagemGrupo, 5, "P", 0, 0, 5000, 3000, 247000);
    }

    private void checkGrupo(MensagemGrupo[] mensagemGrupo, int grupo, String tipo, int flag,
                            int tempo1, int tempo2, int tempo3, int tempo4) {
        assertEquals(mensagemGrupo[grupo - 1].getGrupo(), grupo);
        assertEquals(mensagemGrupo[grupo - 1].isFlagPedestreVeicular(), tipo == "P");
        assertEquals(mensagemGrupo[grupo - 1].getFlagUltimoTempo().ordinal(), flag);
        assertEquals(mensagemGrupo[grupo - 1].getTempoAtrasoDeGrupo(), tempo1);
        assertEquals(mensagemGrupo[grupo - 1].getTempoAmareloOuVermelhoIntermitente(), tempo2);
        assertEquals(mensagemGrupo[grupo - 1].getTempoVermelhoLimpeza(), tempo3);
        assertEquals(mensagemGrupo[grupo - 1].getTempoVerdeOuVermelho(), tempo4);
    }

}
