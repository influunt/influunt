package protocol;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.google.gson.Gson;
import org.apache.commons.codec.DecoderException;
import org.apache.commons.codec.binary.Hex;
import org.fusesource.mqtt.client.QoS;
import play.libs.Json;
import utils.EncryptionUtil;

import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.SecretKey;
import java.io.Serializable;
import java.security.InvalidKeyException;
import java.security.KeyFactory;
import java.security.NoSuchAlgorithmException;
import java.security.PublicKey;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.X509EncodedKeySpec;
import java.util.UUID;

import static play.libs.Json.newObject;

/**
 * Created by rodrigosol on 9/6/16.
 */

//
public class Envelope implements Serializable {

    private static final long serialVersionUID = -6992146830358479831L;

    private String idMensagem;

    private TipoMensagem tipoMensagem;

    private String idControlador;

    private String destino;

    private int qos;

    private long carimboDeTempo;

    private Object conteudo;

    private String emResposta;

    private Boolean criptografado = true;

    public Envelope(TipoMensagem tipoMensagem, String idControlador, String destino, int qos,
                    Object conteudo, String emResposta) {
        this.tipoMensagem = tipoMensagem;
        this.idControlador = idControlador;
        this.destino = destino;
        this.qos = qos;
        this.conteudo = conteudo;
        this.emResposta = emResposta;
        this.carimboDeTempo = System.currentTimeMillis();
        this.idMensagem = UUID.randomUUID().toString();
    }

    public Envelope(TipoMensagem tipoMensagem, String idControlador, String destino, QoS qos,
                    Object conteudo, String emResposta) {
        this(tipoMensagem, idControlador, destino, qos.ordinal(), conteudo, emResposta);
    }

    public Boolean isCriptografado() {
        return criptografado;
    }

    public String getIdMensagem() {
        return idMensagem;
    }


    public TipoMensagem getTipoMensagem() {
        return tipoMensagem;
    }

    public String getIdControlador() {
        return idControlador;
    }

    public String getDestino() {
        return destino;
    }

    public void setDestino(String destino) {
        this.destino = destino;
    }

    public int getQos() {
        return qos;
    }

    public long getCarimboDeTempo() {
        return carimboDeTempo;
    }

    public Object getConteudo() {
        return conteudo;
    }

    public void setConteudo(Object conteudo) {
        this.conteudo = conteudo;
    }

    public JsonNode getConteudoParsed() {
        return Json.parse(getConteudo().toString());
    }

    public String getEmResposta() {
        return emResposta;
    }

    public void setEmResposta(String emResposta) {
        this.emResposta = emResposta;
    }

    @Override
    public String toString() {
        return "Envelope{" +
            "idMensagem='" + idMensagem + '\'' +
            ", tipoMensagem=" + tipoMensagem +
            ", idControlador='" + idControlador + '\'' +
            ", destino='" + destino + '\'' +
            ", qos=" + qos +
            ", carimboDeTempo=" + carimboDeTempo +
            ", conteudo=" + conteudo +
            ", emResposta='" + emResposta + '\'' +
            '}';
    }

    public String toJson() {
        Gson gson = new Gson();
        return gson.toJson(this);
    }

    public Envelope replayWithSameMessage(String destino) {
        return new Envelope(this.tipoMensagem, this.idControlador, destino, this.qos, this.conteudo, this.idMensagem);
    }


    public String toJsonCriptografado(String publicKey) {
        if (!criptografado) {
            return toJson();
        }

        try {
            PublicKey pk = KeyFactory.getInstance("RSA").generatePublic(new X509EncodedKeySpec(Hex.decodeHex(publicKey.toCharArray())));
            SecretKey secretKey = EncryptionUtil.generateAESKey();
            String key = Hex.encodeHexString(EncryptionUtil.encryptRSA(secretKey.getEncoded(), pk));
            String json = Hex.encodeHexString(EncryptionUtil.encryptAES(toJson(), secretKey));
            ObjectNode root = newObject();
            root.put("key", key);
            root.put("idControlador", this.getIdControlador());
            root.put("content", json);
            return root.toString();
        } catch (NoSuchAlgorithmException | InvalidKeySpecException | BadPaddingException | NoSuchPaddingException | InvalidKeyException | DecoderException | IllegalBlockSizeException e) {
            e.printStackTrace();
        }
        return null;
    }

    public void setCriptografado(Boolean criptografado) {
        this.criptografado = criptografado;
    }

}
