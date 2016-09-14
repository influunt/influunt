package models;

import be.objectify.deadbolt.java.models.Subject;
import com.avaje.ebean.Model;
import com.avaje.ebean.annotation.CreatedTimestamp;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import json.deserializers.InfluuntDateTimeDeserializer;
import json.serializers.InfluuntDateTimeSerializer;
import org.joda.time.DateTime;

import javax.persistence.*;
import java.io.Serializable;
import java.util.UUID;

@Entity
@Table(name = "sessoes")
public class Sessao extends Model implements Serializable {

    private static final long serialVersionUID = -1684155418845418381L;

    public static Finder<UUID, Sessao> find = new Finder<UUID, Sessao>(Sessao.class);

    @Id
    private UUID id;

    @Column
    private String idJson;

    @ManyToOne
    private Usuario usuario;

    @Column
    private Boolean ativa = true;

    @Column
    private String ip;

    @Column
    @JsonDeserialize(using = InfluuntDateTimeDeserializer.class)
    @JsonSerialize(using = InfluuntDateTimeSerializer.class)
    @CreatedTimestamp
    private DateTime dataCriacao;

    @Column
    @JsonDeserialize(using = InfluuntDateTimeDeserializer.class)
    @JsonSerialize(using = InfluuntDateTimeSerializer.class)
    private DateTime dataSaida;

    public Sessao() {super();}

    public Sessao(final Usuario usuario) {
        this.usuario = usuario;
    }

    public String getIdJson() {
        return idJson;
    }

    public void setIdJson(String idJson) {
        this.idJson = idJson;
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public Boolean getAtiva() {
        return ativa;
    }

    public void setAtiva(Boolean ativa) {
        this.ativa = ativa;
    }

    public String getToken() {
        return this.id.toString();
    }

    public Subject getSubject() {
        return usuario;
    }

    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    public DateTime getDataCriacao() {
        return dataCriacao;
    }

    public DateTime getDataSaida() {
        return dataSaida;
    }

    public void setDataSaida(DateTime dataSaida) {
        this.dataSaida = dataSaida;
    }

    public void setDataCriacao(DateTime dataCriacao) {
        this.dataCriacao = dataCriacao;
    }

    public void desativar() {
        this.ativa = false;
        this.dataSaida = new DateTime();
        this.update();
    }
}
