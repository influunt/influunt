package os72c.client.controladores;

import akka.actor.ActorRef;
import akka.actor.UntypedActor;
import jssc.SerialPortException;
import os72c.client.models.EstadoGrupo;
import os72c.client.models.Interrupcao;
import os72c.client.models.TipoEvento;
import os72c.client.models.TipoInterrupcao;
import os72c.client.procolos.MensagemControladorSupervisor;
import os72c.client.procolos.MensagemInterrupcao;

import java.util.Date;
import java.util.Set;
import java.util.TreeSet;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by rodrigosol on 6/24/16.
 */
public abstract class Controlador extends UntypedActor {
    private static final int ID_CONTROLADOR = 0;
    private static Pattern pattern = Pattern.compile("([P|V|E|N|M]\\d+)");
    protected ActorRef supervisor;
    private String idControlador;


    protected abstract void supervisorPronto(ActorRef supervisor, String[] argumentos);

    protected void pronto(String fabricante, String modelo, String firmware) {
        supervisor.tell(new MensagemControladorSupervisor(TipoEvento.CONTROLADOR_PRONTO, null, idControlador, fabricante, modelo, firmware), getSelf());
    }

    @Override
    public void onReceive(Object message) throws Exception {
        System.out.println("Recebida:" + message);
        if (message instanceof MensagemControladorSupervisor) {
            MensagemControladorSupervisor mensagemControladorSupervisor = (MensagemControladorSupervisor) message;
            switch (mensagemControladorSupervisor.tipoEvento) {
                case MUDANCA_GRUPO:
                    MensagemControladorSupervisor msg = new MensagemControladorSupervisor(TipoEvento.MUDANCA_GRUPO, mensagemControladorSupervisor.estadoDosGrupos,
                            String.valueOf(new Date().getTime() - Long.valueOf(mensagemControladorSupervisor.argumentos[0])),
                            mensagemControladorSupervisor.argumentos[1]);
                    getContext().actorFor("akka://InfluuntSystem/user/cliente/ControladorMQTT").tell(msg, getSelf());
                    onChange(mensagemControladorSupervisor.estadoDosGrupos, Integer.valueOf(mensagemControladorSupervisor.argumentos[1]));

                    break;
                case SUPERVISOR_PRONTO:
                    supervisor = getSender();
                    supervisorPronto(supervisor, mensagemControladorSupervisor.argumentos);
                    break;
            }
        } else if (message instanceof Interrupcao) {
            Interrupcao i = (Interrupcao) message;
            switch (i.tipoInterrupcao) {
                case ERRO:
                    entrarEmModoAmarelhoIntermitente();
                    break;

            }
        }
    }

    protected void interruption(String msg) {

        Set<Interrupcao> interrupcaoSet = new TreeSet<Interrupcao>();
        Matcher matcher = pattern.matcher(msg);
        while (matcher.find()) {
            TipoInterrupcao ti = TipoInterrupcao.fromString(matcher.group().substring(0, 1));
            Integer indice = Integer.valueOf(matcher.group().substring(1));
            interrupcaoSet.add(new Interrupcao(ti, indice));
            System.out.println(matcher.group());
        }

        if (interrupcaoSet.size() > 0) {
            supervisor.tell(new MensagemInterrupcao(interrupcaoSet), getSelf());
        }
    }

    protected abstract void entrarEmModoAmarelhoIntermitente() throws SerialPortException;

    protected abstract void onChange(EstadoGrupo[] estadoDosGrupos, int tempoRestante) throws Exception;

}
