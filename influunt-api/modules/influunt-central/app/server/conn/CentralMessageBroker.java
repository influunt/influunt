package server.conn;

import akka.actor.ActorRef;
import akka.actor.OneForOneStrategy;
import akka.actor.SupervisorStrategy;
import akka.actor.UntypedActor;
import akka.event.Logging;
import akka.event.LoggingAdapter;
import akka.japi.Function;
import akka.routing.Router;
import handlers.*;
import org.apache.commons.math3.util.Pair;
import play.libs.Json;
import protocol.Envelope;
import protocol.TipoMensagem;
import scala.concurrent.duration.Duration;
import status.Transacao;
import utils.AtoresCentral;

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
                    t.printStackTrace();
                    return SupervisorStrategy.resume();
                }
            }, false);

    private final ActorRef actorTransacaoManager;


    Map<TipoMensagem, Router> routers = new HashMap<>();


    LoggingAdapter log = Logging.getLogger(getContext().system(), this);

    public CentralMessageBroker(ActorRef actorTransacaoManager) {
        routers.put(TipoMensagem.CONTROLADOR_ONLINE, createRoutees(getContext(), 5, ConexaoOnlineActorHandler.class));
        routers.put(TipoMensagem.CONTROLADOR_OFFLINE, createRoutees(getContext(), 5, ConexaoOfflineActorHandler.class));
        routers.put(TipoMensagem.ECHO, createRoutees(getContext(), 5, EchoActorHandler.class));
        routers.put(TipoMensagem.CONFIGURACAO_INICIAL, createRoutees(getContext(), 5, ConfiguracaoActorHandler.class));
        routers.put(TipoMensagem.CONFIGURACAO_OK, createRoutees(getContext(), 5, ConfiguracaoActorHandler.class));
        routers.put(TipoMensagem.MUDANCA_STATUS_CONTROLADOR, createRoutees(getContext(), 5, MudancaStatusControladorActorHandler.class));
        routers.put(TipoMensagem.OK, createRoutees(getContext(), 5, OKActorHandler.class));
        routers.put(TipoMensagem.ERRO, createRoutees(getContext(), 5, ErroActorHandler.class));
        routers.put(TipoMensagem.ALARME_FALHA, createRoutees(getContext(), 5, AlarmeFalhaActorHandler.class));
        routers.put(TipoMensagem.REMOCAO_FALHA, createRoutees(getContext(), 5, RemocaoFalhaActorHandler.class));
        routers.put(TipoMensagem.TROCA_DE_PLANO, createRoutees(getContext(), 5, TrocaPlanoEfetivoActorHandler.class));
        routers.put(TipoMensagem.LER_DADOS_CONTROLADOR, createRoutees(getContext(), 5, LerDadosActorHandler.class));
        routers.put(TipoMensagem.INFO, createRoutees(getContext(), 5, InfoActorHandler.class));

        this.actorTransacaoManager = actorTransacaoManager;
    }


    @Override
    public void onReceive(Object message) throws Exception {
        if (message instanceof Envelope) {
            Envelope envelope = (Envelope) message;
            if (routers.containsKey(envelope.getTipoMensagem())) {
                log.info("CENTRAL RECEBEU: " + envelope.getTipoMensagem());
                routers.get(envelope.getTipoMensagem()).route(envelope, getSender());
            } else if (envelope.getTipoMensagem().equals(TipoMensagem.PACOTE_TRANSACAO)) {
                actorTransacaoManager.tell(envelope, getSender());
            } else if (envelope.getTipoMensagem().equals(TipoMensagem.TRANSACAO)) {
                Transacao transacao = Transacao.fromJson(Json.parse(envelope.getConteudo().toString()));
                getContext().actorSelection(AtoresCentral.transacaoActorPath(transacao.transacaoId)).tell(envelope, getSelf());
            } else {
                log.info("[CENTRAL] - MESSAGE BROKER NÃO SABER TRATAR O TIPO: {}", envelope.getTipoMensagem());
                throw new RuntimeException("[CENTRAL] - MESSAGE BROKER NÃO SABER TRATAR O TIPO " + envelope.getTipoMensagem());
            }
        } else if (message instanceof Pair) {
            actorTransacaoManager.tell(message, getSender());
        }
    }

    @Override
    public SupervisorStrategy supervisorStrategy() {
        return strategy;
    }
}
