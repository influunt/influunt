package handlers;

import akka.actor.*;
import akka.event.Logging;
import akka.event.LoggingAdapter;
import com.fasterxml.jackson.databind.JsonNode;
import org.apache.commons.math3.util.Pair;
import org.fusesource.mqtt.client.QoS;
import play.libs.Json;
import protocol.*;
import scala.concurrent.duration.Duration;
import status.PacoteTransacao;
import status.StatusPacoteTransacao;
import status.Transacao;
import utils.AtoresCentral;

import java.util.*;
import java.util.concurrent.TimeUnit;
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
    private Map<String, Cancellable> individualTimeout = new HashMap<>();

    private Cancellable globalTimeout;

    public PacoteTransacaoActorHandler(PacoteTransacao pacoteTransacao, ActorRef ref) {
        this.pacoteTransacao = pacoteTransacao;
        this.manager = ref;

        start();
    }

    private void start() {

        globalTimeout = getContext().system().scheduler().scheduleOnce(Duration.create(pacoteTransacao.getTempoMaximo(), TimeUnit.MILLISECONDS),
            new Runnable() {
                @Override
                public void run() {
                    getSelf().tell("GLOBAL_TIMEOUT",getSelf());
                }
            }, getContext().system().dispatcher());

        pacoteTransacao.getTransacoes().stream().forEach(transacao -> {
            ActorRef ref = getContext().actorOf(Props.create(TransacaoActorHandler.class, transacao, getSelf()), "transacao-" + transacao.getTransacaoId());
            transacoesActors.put(transacao.getTransacaoId(), ref);
            transacoes.put(transacao.getTransacaoId(), transacao);
            setIndividualTimeout(transacao.getTransacaoId(),transacao.etapaTransacao);
        });

        enviaStatusApp(StatusPacoteTransacao.NEW);
    }

    @Override
    public void onReceive(Object message) throws Exception {
        if (message instanceof Transacao) {
            Transacao transacao = (Transacao) message;
            transacoes.put(transacao.getTransacaoId(), transacao);
            individualTimeout.get(transacao.getTransacaoId()).cancel();
            analisaStatus();
        }else if(message instanceof String && message.equals("GLOBAL_TIMEOUT")){
            finalizaPorTimeoutGlobal();
        }else if(message instanceof Pair<?,?>){
            Pair<?,?> pair = (Pair<?, ?>) message;

            if(pair.getSecond() instanceof EtapaTransacao) {
                if (transacoes.get(pair.getFirst()).getEtapaTransacao().equals(pair.getSecond())) {
                    registraTimeoutIndividual((String) pair.getFirst());
                }
            }else if(pair.getSecond() instanceof StatusPacoteTransacao) {
                respostaUsuario((StatusPacoteTransacao) pair.getSecond());
            }
        }
    }

    private void registraTimeoutIndividual(String transacaoId) {
        transacoes.get(transacaoId).etapaTransacao = EtapaTransacao.ABORT;
        analisaStatus();
    }

    private void setIndividualTimeout(String transacaoId,EtapaTransacao etapaTransacao){
        individualTimeout.put(transacaoId,getContext().system().scheduler().scheduleOnce(Duration.create(15, TimeUnit.SECONDS),
            new Runnable() {
                @Override
                public void run() {
                    getSelf().tell(new Pair<String,EtapaTransacao>(transacaoId,etapaTransacao),getSelf());
                }
            }, getContext().system().dispatcher()));

    }

    private void finalizaPorTimeoutGlobal() {
        switch (getEtapaTransacao()) {
            case NEW:
            case ABORT:
            case COMMIT:
                enviaTransacoes(EtapaTransacao.ABORT);
                enviaStatusApp(StatusPacoteTransacao.ABORTED);
                break;
        }
        getContext().system().stop(getSelf());
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
            setIndividualTimeout(transacao.getTransacaoId(),transacao.etapaTransacao);
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
            setIndividualTimeout(entry.getKey(),entry.getValue().getEtapaTransacao());
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

        envelope.setCriptografado(false);

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
