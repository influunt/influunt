package execucao;

import config.WithInfluuntApplicationNoAuthentication;
import engine.GerenciadorDeEventos;
import engine.Motor;
import engine.MotorCallback;
import integracao.ControladorHelper;
import models.*;
import org.joda.time.DateTime;
import org.joda.time.LocalTime;
import org.junit.Test;
import utils.CustomCalendar;

import java.util.Calendar;
import java.util.Date;

import static org.awaitility.Awaitility.await;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;


/**
 * Created by rodrigosol on 9/8/16.
 */
public class MotorTest  extends WithInfluuntApplicationNoAuthentication{

    private boolean iniciado = false;
    private boolean finalizado = false;

    @Test
    public void motorTest(){


        Controlador controlador = new ControladorHelper().setPlanos(new ControladorHelper().getControlador());

//        criarEvento(tabelaHoraria, 1, TipoEvento.NORMAL, DiaDaSemana.TODOS_OS_DIAS, LocalTime.parse("00:00:00"), 1);
//        criarEvento(tabelaHoraria, 2, TipoEvento.NORMAL, DiaDaSemana.SEGUNDA_A_SABADO, LocalTime.parse("08:00:00"), 1);
//        criarEvento(tabelaHoraria, 1, TipoEvento.NORMAL, DiaDaSemana.SEGUNDA_A_SEXTA, LocalTime.parse("18:00:00"), 1);
//        criarEvento(tabelaHoraria, 3, TipoEvento.ESPECIAL_RECORRENTE, DiaDaSemana.SEGUNDA_A_SABADO, LocalTime.parse("18:00:00"), 1);
//        criarEvento(tabelaHoraria, 4, TipoEvento.ESPECIAL_NAO_RECORRENTE, DiaDaSemana.DOMINGO, LocalTime.parse("08:00:00"), 1);

        Motor motor = new Motor(controlador, new MotorCallback() {
            @Override
            public void onStart() {
                iniciado = true;
            }

            @Override
            public void onStop() {
                finalizado = true;
            }

            @Override
            public void onChangeEvento(Evento eventoAntigo, Evento eventoNovo) {

            }

            @Override
            public void onGrupoChange(EstadoGrupoSemaforico estadoAntigo, EstadoGrupoSemaforico estadoNovo) {

            }
        });

        DateTime inicio = new DateTime(2016,9,18,0,0,0);
        DateTime fim = new DateTime(2016,9,25,23,59,59);

        motor.start();
        await().until(() -> iniciado);
        assertTrue(iniciado);

        while(inicio.getMillis() <= fim.getMillis()){
            motor.tick(inicio);
            inicio = inicio.plusSeconds(1);
        }

        motor.stop();
        await().until(() -> finalizado);
        assertTrue(finalizado);


    }

}
