package handlers;

import akka.actor.ActorRef;
import akka.actor.ActorSelection;
import akka.actor.Props;
import akka.actor.UntypedActor;
import akka.event.Logging;
import akka.event.LoggingAdapter;
import com.fasterxml.jackson.databind.JsonNode;
import org.fusesource.mqtt.client.QoS;
import play.libs.Json;
import protocol.*;
import status.PacoteTransacao;
import status.StatusPacoteTransacao;
import status.Transacao;
import utils.AtoresCentral;

import java.util.*;
import java.util.function.BinaryOperator;

/**
 * Created by rodrigosol on 9/6/16.
 */
public class PacoteTransacaoActorHandler extends UntypedActor {
    private LoggingAdapter log = Logging.getLogger(getContext().system(), this);
    private final ActorRef manager;

    private PacoteTransacao pacoteTransacao;

    private Map<String, ActorRef> transacoesActors = new HashMap<>();

    private Map<String, Transacao> transacoes = new HashMap<>();

    public PacoteTransacaoActorHandler(PacoteTransacao pacoteTransacao, ActorRef ref) {
        this.pacoteTransacao = pacoteTransacao;
        this.manager = ref;

        start();
    }

    private void start() {
        pacoteTransacao.getTransacoes().stream().forEach(transacao -> {
            ActorRef ref = getContext().actorOf(Props.create(TransacaoActorHandler.class, transacao, getSelf()), "transacao-" + transacao.getTransacaoId());
            transacoesActors.put(transacao.getTransacaoId(), ref);
            transacoes.put(transacao.getTransacaoId(), transacao);
        });
    }

    @Override
    public void onReceive(Object message) throws Exception {
        if (message instanceof Transacao) {
            Transacao transacao = (Transacao) message;
            transacoes.put(transacao.getTransacaoId(), transacao);
            analisaStatus();
        }
    }

    private void respostaUsuario(StatusPacoteTransacao statusPacoteTransacao) {
        switch (statusPacoteTransacao) {
            case CONTINUE:
                enviaTransacoes();
                break;
            case CANCEL:
                enviaTransacoes(EtapaTransacao.ABORT);
                break;
        }
    }

    private void analisaStatus() {
        switch (getEtapaTransacao()) {
            case NEW:
                break;
            case ABORT:
                if (existeCommit()) {
                    enviaStatusApp(StatusPacoteTransacao.PENDING);
                } else {
                    enviaTransacoes();
                }
                break;
            case COMMIT:
                enviaTransacoes();
                break;
            case COMPLETED:
                enviaStatusApp(StatusPacoteTransacao.DONE);
                break;
            case ABORTED:
                enviaStatusApp(StatusPacoteTransacao.ABORTED);
                break;
        }
    }

    private void enviaTransacoes(EtapaTransacao etapaTransacao) {
        Iterator<Map.Entry<String, Transacao>> iterator = transacoes.entrySet().iterator();
        while (iterator.hasNext()) {
            Transacao transacao = iterator.next().getValue();
            transacao.etapaTransacao = etapaTransacao;
            transacoes.put(transacao.getTransacaoId(), transacao);
        }
        enviaTransacoes();
    }

    private boolean existeCommit() {
        return transacoes.entrySet()
            .stream()
            .anyMatch(entry -> entry.getValue().getEtapaTransacao().equals(EtapaTransacao.COMMIT));
    }

    private void enviaTransacoes() {
        ActorSelection ref = getContext().actorSelection(AtoresCentral.mqttActorPath());
        transacoes.entrySet().stream().forEach(entry -> {
            ref.tell(criarEnvelope(entry.getValue()), getSelf());
        });
    }

    private void enviaStatusApp(StatusPacoteTransacao statusPacoteTransacao) {
        pacoteTransacao.setStatusPacoteTransacao(statusPacoteTransacao);

        String pacoteTransacaoJson = pacoteTransacao.toJson().toString();

        Envelope envelope = new Envelope(TipoMensagem.PACOTE_TRANSACAO,
            pacoteTransacao.getId(),
            DestinoApp.statusTransacao(pacoteTransacao.getId()),
            QoS.AT_LEAST_ONCE,
            pacoteTransacaoJson,
            null);

        getContext().actorSelection(AtoresCentral.mqttActorPath()).tell(envelope, getSelf());;
    }

    private Envelope criarEnvelope(Transacao transacao) {
        return new Envelope(TipoMensagem.TRANSACAO,
            transacao.idControlador,
            DestinoControlador.transacao(transacao.idControlador),
            QoS.EXACTLY_ONCE,
            transacao.toJson().toString(),
            null);
    }

    private EtapaTransacao getEtapaTransacao() {
        return transacoes.entrySet().stream().map(entry -> entry.getValue().getEtapaTransacao()).reduce(new BinaryOperator<EtapaTransacao>() {
            @Override
            public EtapaTransacao apply(EtapaTransacao etapaTransacao, EtapaTransacao etapaTransacao2) {
                return etapaTransacao.compareTo(etapaTransacao2) < 0 ? etapaTransacao : etapaTransacao2;
            }
        }).get();
    }


}
