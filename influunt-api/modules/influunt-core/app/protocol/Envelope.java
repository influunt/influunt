package protocol;

import com.google.gson.Gson;
import com.google.inject.Guice;
import models.StatusControlador;

import java.util.UUID;

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

    public Envelope(TipoMensagem tipoMensagem,String idControlador,String destino,int qos,
                    Object conteudo,String emResposta){
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


    public String toJson(){
        Gson gson = new Gson();
        return gson.toJson(this);
    }

}
