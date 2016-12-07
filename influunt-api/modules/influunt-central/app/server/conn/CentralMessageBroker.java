package server.conn;

import akka.actor.OneForOneStrategy;
import akka.actor.SupervisorStrategy;
import akka.actor.UntypedActor;
import akka.event.Logging;
import akka.event.LoggingAdapter;
import akka.japi.Function;
import akka.routing.Router;
import handlers.*;
import protocol.Envelope;
import protocol.TipoMensagem;
import scala.concurrent.duration.Duration;

import java.util.HashMap;
import java.util.Map;

import static utils.MessageBrokerUtils.createRoutees;


/**
 * Created by rodrigosol on 9/6/16.
 */
public class CentralMessageBroker extends UntypedActor {

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


    Map<TipoMensagem, Router> routers = new HashMap<>();


    LoggingAdapter log = Logging.getLogger(getContext().system(), this);

    {
        routers.put(TipoMensagem.CONTROLADOR_ONLINE, createRoutees(getContext(), 5, ConexaoOnlineActorHandler.class));
        routers.put(TipoMensagem.CONTROLADOR_OFFLINE, createRoutees(getContext(), 5, ConexaoOfflineActorHandler.class));
        routers.put(TipoMensagem.ECHO, createRoutees(getContext(), 5, EchoActorHandler.class));
        routers.put(TipoMensagem.CONFIGURACAO_INICIAL, createRoutees(getContext(), 5, ConfiguracaoActorHandler.class));
        routers.put(TipoMensagem.CONFIGURACAO_OK, createRoutees(getContext(), 5, ConfiguracaoActorHandler.class));
        routers.put(TipoMensagem.MUDANCA_STATUS_CONTROLADOR, createRoutees(getContext(), 5, MudancaStatusControladorActorHandler.class));
        routers.put(TipoMensagem.TRANSACAO, createRoutees(getContext(), 5, TransacaoActorHandler.class));
        routers.put(TipoMensagem.OK, createRoutees(getContext(), 5, OKActorHandler.class));
        routers.put(TipoMensagem.ERRO, createRoutees(getContext(), 5, ErroActorHandler.class));
        routers.put(TipoMensagem.ALARME_FALHA, createRoutees(getContext(), 5, AlarmeFalhaActorHandler.class));
        routers.put(TipoMensagem.REMOCAO_FALHA, createRoutees(getContext(), 5, RemocaoFalhaActorHandler.class));
        routers.put(TipoMensagem.TROCA_DE_PLANO, createRoutees(getContext(), 5, TrocaPlanoEfetivoActorHandler.class));
        routers.put(TipoMensagem.LER_DADOS_CONTROLADOR, createRoutees(getContext(), 5, LerDadosActorHandler.class));
    }


    @Override
    public void onReceive(Object message) throws Exception {
        if (message instanceof Envelope) {
            Envelope envelope = (Envelope) message;
            if (routers.containsKey(envelope.getTipoMensagem())) {
                log.info("CENTRAL RECEBEU: " + envelope.getTipoMensagem());
                routers.get(envelope.getTipoMensagem()).route(envelope, getSender());
            } else {
                log.info("[CENTRAL] - MESSAGE BROKER NÃO SABER TRATAR O TIPO: {}", envelope.getTipoMensagem());
                throw new RuntimeException("[CENTRAL] - MESSAGE BROKER NÃO SABER TRATAR O TIPO " + envelope.getTipoMensagem());
            }
        }
    }

    @Override
    public SupervisorStrategy supervisorStrategy() {
        return strategy;
    }

    @Override
    public void postStop() throws Exception {
        System.out.println("CentralMessageBroker parou");
        super.postStop();
    }
}
