package os72c.supervisores;

import akka.actor.*;
import akka.cluster.Cluster;
import akka.cluster.ClusterEvent;
import akka.cluster.Member;
import akka.event.Logging;
import akka.event.LoggingAdapter;
import akka.japi.Function;
import com.typesafe.config.ConfigFactory;
import os72c.Application;
import com.google.common.collect.ImmutableList;
import com.typesafe.config.Config;
import os72c.Constants;
import os72c.ProxyParent;
import os72c.controladores.ControladorI2C;
import os72c.controladores.ControladorSerial;
import os72c.exceptions.HardwareFailureException;
import os72c.models.EstadoGrupo;
import os72c.models.Intervalos;
import os72c.models.Troca;
import os72c.procolos.EventoControladorSupervisor;
import os72c.procolos.TipoEvento;
import scala.concurrent.duration.Duration;

/**
 * Created by rodrigosol on 6/24/16.
 */
public class Supervisor extends UntypedActor {
    private Intervalos intervalos;
    LoggingAdapter log = Logging.getLogger(getContext().system(), this);
    Cluster cluster = Cluster.get(getContext().system());
    Long i = 0l;
    private ActorRef controlador;
    private ImmutableList<Troca> trocas;
    private Config conf;

    private static SupervisorStrategy strategy =
            new OneForOneStrategy(10, Duration.create("1 minute"),
                    new Function<Throwable, SupervisorStrategy.Directive>() {
                        @Override
                        public SupervisorStrategy.Directive apply(Throwable t) {
                            if (t instanceof HardwareFailureException) {
                                return SupervisorStrategy.restart();
                            }else{
                                return SupervisorStrategy.escalate();
                            }
                        }
                    });
    private String central = "akka.tcp://InfluuntSystem@10.0.0.9:2551/user/central";
    private ActorRef proxy;


    @Override
    public SupervisorStrategy supervisorStrategy() {
        return strategy;
    }

    @Override
    public void preStart() throws Exception {
        //#subscribe
        cluster.subscribe(getSelf(), ClusterEvent.initialStateAsEvents(),
                ClusterEvent.MemberEvent.class, ClusterEvent.UnreachableMember.class,ClusterEvent.MemberUp.class, ClusterEvent.LeaderChanged.class);
        //#subscribe

//        conf = Application.conf72c.getConfig("local.driver");
//        String args[] = new String[6];
//        if(conf.getString("type").equals("serial")){
//            setupSerial(args);
//            controlador = getContext().actorOf(Props.create(ControladorSerial.class), "controlador");
//        }else if(conf.getString("type").equals("i2c")){
//            controlador = getContext().actorOf(Props.create(ControladorI2C.class), "controlador");
//        }else{
//            //Modo de comunicação com o hardware não definido
//            throw new RuntimeException("O tipo de driver não foi especificado");
//        }
//        intervalos = new Intervalos(getContext(),controlador,getSelf());
//        controlador.tell(new EventoControladorSupervisor(TipoEvento.SUPERVISOR_PRONTO,null,args),getSelf());

    }

    @Override
    public void postStop() {
        cluster.unsubscribe(getSelf());
    }

    private void setupSerial(String[] args) {
        args[Constants.SERIAL_PORTA]       = conf.getString("serial.porta");
        args[Constants.SERIAL_BAUDRATE]    = conf.getString("serial.baudrate");
        args[Constants.SERIAL_DATABITS]    = conf.getString("serial.databits");
        args[Constants.SERIAL_STOPBITS]    = conf.getString("serial.stopbits");
        args[Constants.SERIAL_PARITY]      = conf.getString("serial.parity");
        args[Constants.SERIAL_START_DELAY] = conf.getString("serial.startdelay");
    }

    public static Props props() {
        return Props.create(Supervisor.class);
    }

    @Override
    public void onReceive(Object message) throws Exception {

        log.info("-----------------------------------------------------------");
        log.info(message.toString());
        log.info("-----------------------------------------------------------");

        if(message instanceof Long) {
            log.info("-----------------------------------------------------------");
            log.info("----> {}", message);
            log.info("-----------------------------------------------------------");
            i+=1;
            proxy.tell(i,getSelf());
        }

//        if(message instanceof EventoControladorSupervisor){
//            final EventoControladorSupervisor eventoControladorSupervisor = (EventoControladorSupervisor) message;
//            System.out.println(eventoControladorSupervisor);
//            log.debug(eventoControladorSupervisor.toString());
//            switch (eventoControladorSupervisor.tipoEvento){
//                case CONTROLADOR_PRONTO:
//                    registraControlador(eventoControladorSupervisor);
//                    break;
//                case PROXIMO_CICLO:
//                    intervalos.proximoCiclo(Integer.valueOf(eventoControladorSupervisor.argumentos[0]));
//                    break;
//            }
        else if (message instanceof ClusterEvent.MemberUp) {
            log.info("-----------------------------------------------------------");
            log.info("Faco parte!!!!");
            central = ((ClusterEvent.MemberUp) message).member().address().toString();
            log.info("Endereco:" + central);

            proxy = getContext().actorOf(Props.create(ProxyParent.class, getContext().actorSelection(central + "/user/central").anchorPath()));
            proxy.forward("Ola from pi",getContext());


//        }else if(message.equals("Obrigado")){
//            System.out.println("%%%%%%%%%%%%%%%%%%%%" + message);
//            getSender().tell(Long.valueOf(1l),getSelf());
//        }else{
//            System.out.println("DESCONHECIDA" + message);
//            getContext().actorSelection(central).tell(Long.valueOf(1), getSelf());
//        }
        }
    }

    private void register(Member member) {
        if (member.hasRole("central")) {
            getContext().actorSelection(member.address() + "/user/central").tell(
                    "SupervisorRegistration", getSelf());
        }
    }

    private void registraControlador(EventoControladorSupervisor eventoControladorSupervisor) {
        trocas = ImmutableList.of(new Troca(4000,EstadoGrupo.VERMELHO,EstadoGrupo.VERDE, EstadoGrupo.VERMELHO_INTERMITENTE),
                                  new Troca(4000,EstadoGrupo.VERMELHO,EstadoGrupo.AMARELHO, EstadoGrupo.VERMELHO_INTERMITENTE),
                                  new Troca(4000,EstadoGrupo.VERMELHO,EstadoGrupo.VERMELHO, EstadoGrupo.VERMELHO_INTERMITENTE),
                                  new Troca(12000,EstadoGrupo.VERDE,EstadoGrupo.VERMELHO, EstadoGrupo.VERMELHO),
                                  new Troca(4000,EstadoGrupo.AMARELHO,EstadoGrupo.VERMELHO, EstadoGrupo.VERMELHO),
                                  new Troca(4000,EstadoGrupo.VERMELHO,EstadoGrupo.VERMELHO, EstadoGrupo.VERMELHO),
                                  new Troca(12000,EstadoGrupo.VERMELHO,EstadoGrupo.VERMELHO, EstadoGrupo.VERDE),
                                  new Troca(8000,EstadoGrupo.VERMELHO,EstadoGrupo.VERDE, EstadoGrupo.VERDE)

        );



        intervalos.setTrocas(trocas);
        intervalos.start(0);

    }

    public static void main(String args[]){
        // Create an Akka system
        ActorSystem system = ActorSystem.create("InfluuntSystem", ConfigFactory.load());

        // Create an actor that handles cluster domain events
        system.actorOf(Props.create(Supervisor.class),
                "supervisor");
        system.awaitTermination();

    }
}
