package handlers;

import akka.actor.Cancellable;
import akka.actor.UntypedActor;
import akka.event.Logging;
import akka.event.LoggingAdapter;
import org.fusesource.mqtt.client.QoS;
import protocol.DestinoApp;
import protocol.Envelope;
import protocol.LerDadosControlador;
import protocol.TipoMensagem;
import scala.concurrent.duration.Duration;
import utils.AtoresCentral;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by lesiopinheiro on 06/12/16.
 */
public class LerDadosActorHandler extends UntypedActor {

    private LoggingAdapter log = Logging.getLogger(getContext().system(), this);

    private static Map<String, LerDadosTimeout> timeout = new HashMap<>();

    private class LerDadosTimeout {
        private String controladorId;
        private Cancellable timeoutId;
        private int timeoutInSeconds = 60;

        public LerDadosTimeout(Envelope mensagem) {
            this.controladorId = mensagem.getIdControlador();
            this.timeoutId = getContext().system().scheduler().scheduleOnce(Duration.create(timeoutInSeconds, TimeUnit.SECONDS), getSelf(), mensagem.getIdMensagem(), getContext().system().dispatcher(), getSelf());
        }

        public String getControladorId() {
            return controladorId;
        }

        public void cancel() {
            timeoutId.cancel();
        }
    }

    @Override
    public void onReceive(Object message) throws Exception {
        if (message instanceof Envelope) {
            Envelope envelope = (Envelope) message;
            if (envelope.getTipoMensagem().equals(TipoMensagem.LER_DADOS_CONTROLADOR) && envelope.getEmResposta() == null) {
                Envelope envelopeLerDados = LerDadosControlador.getMensagem(envelope);
                getContext().actorSelection(AtoresCentral.mqttActorPath()).tell(envelopeLerDados, getSelf());
                setTimeout(envelopeLerDados);
            } else {
                clearTimeout(envelope.getEmResposta());
                envelope.setDestino(DestinoApp.dadosControlador(envelope.getIdControlador()));
                envelope.setCriptografado(false);
                log.info("CENTRAL ENVIANDO DADOS DO CONTROLADOR PARA APP...");
                getContext().actorSelection(AtoresCentral.mqttActorPath()).tell(envelope, getSelf());
            }
        } else if (message instanceof String) {
            String mensagemId = (String) message;
            Pattern pathPattern = Pattern.compile("[0-9a-f]{8}-[0-9a-f]{4}-[1-5][0-9a-f]{3}-[89ab][0-9a-f]{3}-[0-9a-f]{12}");
            Matcher matcher = pathPattern.matcher(mensagemId);
            if (matcher.matches()) {
                handleTimeout(mensagemId);
            }
        }
    }

    private void setTimeout(Envelope mensagem) {
        LerDadosTimeout t = new LerDadosTimeout(mensagem);
        timeout.put(mensagem.getIdMensagem(), t);
    }

    private void clearTimeout(String mensagemId) {
        if (timeout.containsKey(mensagemId)) {
            timeout.get(mensagemId).cancel();
            timeout.remove(mensagemId);
        }
    }

    private void handleTimeout(String mensagemId) {
        if (timeout.containsKey(mensagemId)) {
            String controladorId = timeout.get(mensagemId).getControladorId();
            clearTimeout(mensagemId);
            String destino = DestinoApp.dadosControlador(controladorId);
            Envelope envelope = new Envelope(TipoMensagem.LER_DADOS_CONTROLADOR, controladorId, destino, QoS.AT_LEAST_ONCE.ordinal(), "TIMEOUT", mensagemId);
            envelope.setCriptografado(false);
            log.info("CENTRAL ENVIANDO TIMEOUT DE LER DADOS CONTROLADOR...");
            getContext().actorSelection(AtoresCentral.mqttActorPath()).tell(envelope, getSelf());
            clearTimeout(mensagemId);
        }
    }
}
