package models;

import be.objectify.deadbolt.java.models.Permission;
import com.avaje.ebean.Model;
import com.avaje.ebean.annotation.ChangeLog;
import com.avaje.ebean.annotation.CreatedTimestamp;
import com.avaje.ebean.annotation.UpdatedTimestamp;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import json.deserializers.InfluuntDateTimeDeserializer;
import json.serializers.InfluuntDateTimeSerializer;
import org.hibernate.validator.constraints.NotBlank;
import org.joda.time.DateTime;

import javax.persistence.*;
import java.io.Serializable;
import java.util.List;
import java.util.UUID;

/**
 * Created by rodrigosol on 6/29/16.
 */
@Entity
@Table(name = "disparos_alarmes")
@ChangeLog
public class DisparoAlarme extends Model implements Serializable {

    private static final long serialVersionUID = -1771456944137012241L;

    public static Finder<UUID, DisparoAlarme> find = new Finder<UUID, DisparoAlarme>(DisparoAlarme.class);

    @Id
    private UUID id;

    @Column
    private String idJson;

    @Column
    @NotBlank(message = "não pode ficar em branco")
    private String chave;

    @Column
    @JsonDeserialize(using = InfluuntDateTimeDeserializer.class)
    @JsonSerialize(using = InfluuntDateTimeSerializer.class)
    @CreatedTimestamp
    private DateTime dataCriacao;

    @Column
    @JsonDeserialize(using = InfluuntDateTimeDeserializer.class)
    @JsonSerialize(using = InfluuntDateTimeSerializer.class)
    @UpdatedTimestamp
    private DateTime dataAtualizacao;

    @ManyToOne(fetch = FetchType.EAGER)
    private Usuario usuario;

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public String getIdJson() {
        return idJson;
    }

    public void setIdJson(String idJson) {
        this.idJson = idJson;
    }

    public DateTime getDataCriacao() {
        return dataCriacao;
    }

    public void setDataCriacao(DateTime dataCriacao) {
        this.dataCriacao = dataCriacao;
    }

    public DateTime getDataAtualizacao() {
        return dataAtualizacao;
    }

    public void setDataAtualizacao(DateTime dataAtualizacao) {
        this.dataAtualizacao = dataAtualizacao;
    }

    public void setChave(String chave) {
        this.chave = chave;
    }

    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }

    public String getChave() {
        return chave;
    }
}
