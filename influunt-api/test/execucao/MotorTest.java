package execucao;

import config.WithInfluuntApplicationNoAuthentication;
import engine.Motor;
import engine.MotorCallback;
import integracao.ControladorHelper;
import models.Controlador;
import models.EstadoGrupoSemaforico;
import models.Evento;
import org.joda.time.DateTime;
import org.junit.Test;
import simulacao.*;

import java.util.List;

import static org.junit.Assert.assertTrue;


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
    public void motorTest() {

        LogSimulacao logSimulacao = new LogSimulacao();
        Controlador controlador = new ControladorHelper().setPlanos(new ControladorHelper().getControlador());

//        criarEvento(tabelaHoraria, 1, TipoEvento.NORMAL, DiaDaSemana.TODOS_OS_DIAS, LocalTime.parse("00:00:00"), 1);
//        criarEvento(tabelaHoraria, 2, TipoEvento.NORMAL, DiaDaSemana.SEGUNDA_A_SABADO, LocalTime.parse("08:00:00"), 1);
//        criarEvento(tabelaHoraria, 1, TipoEvento.NORMAL, DiaDaSemana.SEGUNDA_A_SEXTA, LocalTime.parse("18:00:00"), 1);
//        criarEvento(tabelaHoraria, 3, TipoEvento.ESPECIAL_RECORRENTE, DiaDaSemana.SEGUNDA_A_SABADO, LocalTime.parse("18:00:00"), 1);
//        criarEvento(tabelaHoraria, 4, TipoEvento.ESPECIAL_NAO_RECORRENTE, DiaDaSemana.DOMINGO, LocalTime.parse("08:00:00"), 1);

        Motor motor = new Motor(controlador, new MotorCallback() {
            @Override
            public void onStart(DateTime timestamp) {
                iniciado = true;
                logSimulacao.log(new DefaultLog(timestamp, TipoEventoLog.INICIO_EXECUCAO));
            }

            @Override
            public void onStop(DateTime timestamp) {
                finalizado = true;
                logSimulacao.log(new DefaultLog(timestamp, TipoEventoLog.INICIO_EXECUCAO));
            }

            @Override
            public void onChangeEvento(DateTime timeStamp, Evento eventoAntigo, Evento eventoNovo) {
                eventoAnterior = eventoAntigo;
                eventoAtual = eventoNovo;
                logSimulacao.log(new AlteracaoEventoLog(timeStamp, eventoAntigo, eventoNovo));
            }

            @Override
            public void onGrupoChange(DateTime timeStamp, List<EstadoGrupoSemaforico> estadoAntigo, List<EstadoGrupoSemaforico> estadoNovo) {
                estadoAnterior = estadoAntigo;
                estadoAtual = estadoNovo;
                logSimulacao.log(new AlteracaoEstadoLog(timeStamp, estadoAntigo, estadoNovo));
            }
        });

        DateTime inicio = new DateTime(2016, 9, 18, 0, 0, 0);
        DateTime fim = new DateTime(2016, 10, 9, 18, 0, 0);

        motor.start(inicio);
        //await().until(() -> iniciado);
        assertTrue(iniciado);

        while (inicio.getMillis() / 1000 <= fim.getMillis() / 1000) {
            motor.tick(inicio);
            inicio = inicio.plusSeconds(1);
        }
        motor.stop(fim);
        //await().until(() -> finalizado);
        assertTrue(finalizado);

        logSimulacao.print(TipoEventoLog.ALTERACAO_EVENTO);


    }

}
