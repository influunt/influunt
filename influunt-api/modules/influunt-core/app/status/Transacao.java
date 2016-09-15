package status;

import models.Estagio;
import models.StatusDevice;
import org.jongo.Aggregate;
import org.jongo.MongoCollection;
import org.jongo.MongoCursor;
import play.api.Play;
import protocol.EtapaTransacao;
import uk.co.panaxiom.playjongo.PlayJongo;

import java.util.*;

/**
 * Created by lesiopinheiro on 9/2/16.
 */
public class Transacao {

    public static final String COLLECTION = "transacoes";

    public static PlayJongo jongo = Play.current().injector().instanceOf(PlayJongo.class);

    public String _id;
    public EtapaTransacao etapaTransacao;
    public String transacaoId;
    public String idControlador;
    public Long timestamp;
    public transient Object payload;

    public Transacao(){
    }

    public Transacao(String idControlador, Object payload) {
        this.etapaTransacao = EtapaTransacao.NEW;
        this.transacaoId = UUID.randomUUID().toString();
        this.idControlador = idControlador;
        this.payload = payload;
        this.timestamp = System.currentTimeMillis();
    }

    public static MongoCollection transacoes() {
        return jongo.getCollection(COLLECTION);
    }

    public static Transacao findByTransacaoId(String transacaoId){
        return transacoes().findOne("{ transacaoId: # }", transacaoId).as(Transacao.class);
    }

    public void setPayload(Object payload) {
        this.payload = payload;
    }

    public void create() {
        transacoes().insert(this);
    }

    public void update() {
        transacoes().save(this);
    }

    public void updateStatus(EtapaTransacao etapaTransacao) {
        this.etapaTransacao = etapaTransacao;
        this.timestamp = System.currentTimeMillis();
    }

    @Override
    public String toString() {
        return "Transacao{" +
                "etapaTransacao=" + etapaTransacao +
                ", transacaoId='" + transacaoId + '\'' +
                ", idControlador='" + idControlador + '\'' +
                '}';
    }
}
