package models;

import com.avaje.ebean.Model;
import com.avaje.ebean.annotation.CreatedTimestamp;
import com.avaje.ebean.annotation.UpdatedTimestamp;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import json.deserializers.EnderecoDeserializer;
import json.deserializers.InfluuntDateTimeDeserializer;
import json.serializers.EnderecoSerializer;
import json.serializers.InfluuntDateTimeSerializer;
import org.hibernate.validator.constraints.NotBlank;
import org.joda.time.DateTime;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.UUID;

/**
 * Created by lesiopinheiro on 7/29/16.
 */
@Entity
@Table(name = "enderecos")
@JsonSerialize(using = EnderecoSerializer.class)
@JsonDeserialize(using = EnderecoDeserializer.class)
public class Endereco extends Model implements Serializable {

    private static final long serialVersionUID = -3456679290170824322L;

    @Id
    private UUID id;

    @Column
    private String idJson;

    @ManyToOne
    private Controlador controlador;

    @ManyToOne
    private Anel anel;

    @Column
    @NotBlank(message = "não pode ficar em branco")
    private String localizacao;

    @Column
    @NotNull(message = "não pode ficar em branco")
    private Double latitude;

    @Column
    @NotNull(message = "não pode ficar em branco")
    private Double longitude;

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

    public Endereco() {
        super();
        this.setIdJson(UUID.randomUUID().toString());
    }

    public Endereco(Double latitude, Double longitude, String localizacao) {
        super();
        this.setIdJson(UUID.randomUUID().toString());
        this.latitude = latitude;
        this.longitude = longitude;
        this.localizacao = localizacao;
    }

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

    public Controlador getControlador() {
        return controlador;
    }

    public void setControlador(Controlador controlador) {
        this.controlador = controlador;
    }

    public String getLocalizacao() {
        return localizacao;
    }

    public void setLocalizacao(String localizacao) {
        this.localizacao = localizacao;
    }

    public Double getLatitude() {
        return latitude;
    }

    public void setLatitude(Double latitude) {
        this.latitude = latitude;
    }

    public Double getLongitude() {
        return longitude;
    }

    public void setLongitude(Double longitude) {
        this.longitude = longitude;
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

    public Anel getAnel() {
        return anel;
    }

    public void setAnel(Anel anel) {
        this.anel = anel;
    }
}
