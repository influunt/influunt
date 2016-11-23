package protocol;

import com.fasterxml.jackson.databind.node.ObjectNode;
import com.google.gson.Gson;
import org.apache.commons.codec.DecoderException;
import org.apache.commons.codec.binary.Hex;
import utils.EncryptionUtil;

import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.SecretKey;
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
public class Envelope {

    private String idMensagem;

    private TipoMensagem tipoMensagem;

    private String idControlador;

    private String destino;

    private int qos;

    private long carimboDeTempo;

    private Object conteudo;

    private String emResposta;

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

    public Envelope replayWithSameMenssage(String detino) {
        return new Envelope(this.tipoMensagem, this.idControlador, detino, this.qos, this.conteudo, this.idMensagem);
    }


    public String toJsonCriptografado(String publicKey) {
        try {
            PublicKey pk =
                KeyFactory.getInstance("RSA").generatePublic(new X509EncodedKeySpec(Hex.decodeHex(publicKey.toCharArray())));
            SecretKey secretKey = EncryptionUtil.generateAESKey();
            String key = Hex.encodeHexString(EncryptionUtil.encryptRSA(secretKey.getEncoded(), pk));
            String json = Hex.encodeHexString(EncryptionUtil.encryptAES(toJson(), secretKey));
            ObjectNode root = newObject();
            root.put("key", key);
            root.put("idControlador", this.getIdControlador());
            root.put("content", json);
            return root.toString();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (BadPaddingException e) {
            e.printStackTrace();
        } catch (IllegalBlockSizeException e) {
            e.printStackTrace();
        } catch (NoSuchPaddingException e) {
            e.printStackTrace();
        } catch (InvalidKeyException e) {
            e.printStackTrace();
        } catch (DecoderException e) {
            e.printStackTrace();
        } catch (InvalidKeySpecException e) {
            e.printStackTrace();
        }
        return null;
    }

//    public void decriptografar(PrivateKey privateKey){
//        try {
//            byte[] encriptedAesKey = Hex.decodeHex(this.key.toCharArray());
//            byte decriptedAesKey[] = EncryptionUtil.decryptRSA(encriptedAesKey,privateKey);
//            this.conteudo = EncryptionUtil.decryptAES(this.conteudo.toString(),decriptedAesKey);
//        } catch (NoSuchAlgorithmException e) {
//            e.printStackTrace();
//        } catch (BadPaddingException e) {
//            e.printStackTrace();
//        } catch (IllegalBlockSizeException e) {
//            e.printStackTrace();
//        } catch (NoSuchPaddingException e) {
//            e.printStackTrace();
//        } catch (InvalidKeyException e) {
//            e.printStackTrace();
//        } catch (DecoderException e) {
//            e.printStackTrace();
//        }
//    }

}
