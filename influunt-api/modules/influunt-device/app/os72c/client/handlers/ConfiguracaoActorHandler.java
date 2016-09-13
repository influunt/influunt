package os72c.client.handlers;

import akka.actor.UntypedActor;
import akka.event.Logging;
import akka.event.LoggingAdapter;
import models.Controlador;
import protocol.Configuracao;
import protocol.Envelope;
import protocol.Sinal;
import protocol.TipoMensagem;

/**
 * Created by rodrigosol on 9/6/16.
 */
public class ConfiguracaoActorHandler extends UntypedActor {

    LoggingAdapter log = Logging.getLogger(getContext().system(), this);

    private final String idControlador;

    public ConfiguracaoActorHandler(String idControlador) {
        this.idControlador = idControlador;
    }

    @Override
    public void onReceive(Object message) throws Exception {
        if (message instanceof Envelope) {
            Envelope envelope = (Envelope) message;
            if (envelope.getTipoMensagem().equals(TipoMensagem.CONFIGURACAO)) {
                if (envelope.getEmResposta() == null) {
                    log.info("Mensagem de configuração errada: {}", envelope.getConteudo().toString());
                } else {
                    log.info("Central respondeu a configuração: {}", envelope.getConteudo().toString());
                    Envelope envelope1;
                    if(Controlador.isValido(envelope.getConteudo())){
                        log.info("Responder OK para Central: {}", envelope.getConteudo().toString());
                        envelope1 = Sinal.getMensagem(TipoMensagem.OK, idControlador, "central/configuracao");
                    }else{
                        log.info("Responder ERRO para Central: {}", envelope.getConteudo().toString());
                        envelope1 = Sinal.getMensagem(TipoMensagem.ERRO, idControlador, "central/configuracao");
                    }
                    envelope1.setEmResposta(envelope.getIdMensagem());
                    getContext().actorSelection("akka://InfluuntSystem/user/"+idControlador+"/ControladorMQTT").tell(envelope1, getSelf());
                }
            }
        } else if (message instanceof  String){
            if(message.toString().equals("VERIFICA")){
                log.info("Solicita configuração a central: {}", sender());
                //TODO
                getContext().actorSelection("akka://InfluuntSystem/user/"+idControlador+"/ControladorMQTT").tell(Sinal.getMensagem(TipoMensagem.CONFIGURACAO_INICIAL, idControlador, "central/configuracao"), getSelf());
            }
        }
    }
}
