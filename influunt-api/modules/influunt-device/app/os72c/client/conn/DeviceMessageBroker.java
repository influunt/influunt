package os72c.client.conn;

import akka.actor.*;
import akka.event.Logging;
import akka.event.LoggingAdapter;
import akka.japi.Function;
import akka.routing.Router;
import os72c.client.handlers.ConfiguracaoActorHandler;
import os72c.client.handlers.EchoActorHandler;
import os72c.client.handlers.ErroActorHandler;
import os72c.client.handlers.LerDadosControladorActorHandler;
import os72c.client.protocols.Mensagem;
import os72c.client.protocols.MensagemVerificaConfiguracao;
import os72c.client.storage.Storage;
import protocol.Envelope;
import protocol.TipoMensagem;
import scala.concurrent.duration.Duration;

import java.util.HashMap;
import java.util.Map;

import static utils.MessageBrokerUtils.createRoutees;


/**
 * Created by rodrigosol on 9/6/16.
 */
public class DeviceMessageBroker extends UntypedActor {

    private static SupervisorStrategy strategy =
        new OneForOneStrategy(1000, Duration.Undefined(),
            new Function<Throwable, SupervisorStrategy.Directive>() {
                @Override
                public SupervisorStrategy.Directive apply(Throwable t) {
                    System.out.println("[CentralMessageBroker] Um ator falhou");
                    t.printStackTrace();
                    return SupervisorStrategy.resume();
                }
            }, false);

    private final ActorRef actorTransacao;

    private Router routerEcho;

    private ActorRef actorConfiguracao;

    private Map<TipoMensagem, Router> routers = new HashMap<>();

    private LoggingAdapter log = Logging.getLogger(getContext().system(), this);

    public DeviceMessageBroker(String idControlador, Storage storage, ActorRef actorTransacao) {

        routers.put(TipoMensagem.ECHO, createRoutees(getContext(), 5, EchoActorHandler.class));
        routers.put(TipoMensagem.CONFIGURACAO, createRoutees(getContext(), 1, ConfiguracaoActorHandler.class, idControlador, storage));
        routers.put(TipoMensagem.ERRO, createRoutees(getContext(), 1, ErroActorHandler.class));
        routers.put(TipoMensagem.LER_DADOS_CONTROLADOR, createRoutees(getContext(), 1, LerDadosControladorActorHandler.class, idControlador, storage));
        actorConfiguracao = getContext().actorOf(Props.create(ConfiguracaoActorHandler.class, idControlador, storage), "actorConfig");
        this.actorTransacao = actorTransacao;

    }

    @Override
    public void onReceive(Object message) throws Exception {
        if (message instanceof Envelope) {

            Envelope envelope = (Envelope) message;
            System.out.println("DEVICE RECEBEU: " + envelope.getTipoMensagem());
            if (routers.containsKey(envelope.getTipoMensagem())) {
                routers.get(envelope.getTipoMensagem()).route(envelope, getSender());
            } else if (envelope.getTipoMensagem().equals(TipoMensagem.TRANSACAO)) {
                actorTransacao.tell(envelope, getSender());
            } else {
                log.error("[DEVICE] - MESSAGE BROKER NÃO SABER TRATAR O TIPO: {}", envelope.getTipoMensagem());
                throw new RuntimeException("[DEVICE] - MESSAGE BROKER NÃO SABER TRATAR A MENSAGEM: " + envelope.getConteudo());
            }
        } else if (message instanceof Mensagem) {
            if (message instanceof MensagemVerificaConfiguracao) {
                actorConfiguracao.tell("VERIFICA", getSender());
            }
        }
    }

    @Override
    public SupervisorStrategy supervisorStrategy() {
        return strategy;
    }
}
