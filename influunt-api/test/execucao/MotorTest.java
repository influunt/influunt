package execucao;


import config.WithInfluuntApplicationNoAuthentication;
import integracao.ControladorHelper;
import models.Controlador;
import models.EstadoGrupoSemaforico;
import models.Evento;
import org.joda.time.DateTime;
import org.junit.Test;
import simulacao.Simulador;
import simulacao.TipoEventoLog;
import simulacao.VisualLog;

import java.io.IOException;
import java.util.List;


/**
 * Created by rodrigosol on 9/8/16.
 */
public class MotorTest extends WithInfluuntApplicationNoAuthentication {

    private boolean iniciado = false;

    private boolean finalizado = false;

    private Evento eventoAnterior = null;

    private Evento eventoAtual = null;

    private List<EstadoGrupoSemaforico> estadoAnterior = null;

    private List<EstadoGrupoSemaforico> estadoAtual = null;

    @Test
    public void motorTest() throws IOException {

        Simulador simulador = new Simulador();

        Controlador controlador = new ControladorHelper().setPlanos(new ControladorHelper().getControlador());
        simulador.setControlador(controlador);
        simulador.setDataInicioControlador(new DateTime(2016, 9, 19, 7, 59, 0));
        simulador.setInicio(new DateTime(2016, 9, 19, 7, 55, 0));
        simulador.setFim(new DateTime(2016, 9, 19, 8, 1, 0));
        simulador.simular();
        simulador.getLogSimulacao().print(TipoEventoLog.AGENDAMENTO_TROCA_DE_PLANO);
        VisualLog visualLog = new VisualLog(simulador);
        visualLog.setVisible(true);


    }

}