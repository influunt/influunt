package models;

import checks.ControladorTransicoesProibidasCheck;
import com.avaje.ebean.Model;
import com.avaje.ebean.annotation.CreatedTimestamp;
import com.avaje.ebean.annotation.UpdatedTimestamp;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import json.deserializers.InfluuntDateTimeDeserializer;
import json.serializers.InfluuntDateTimeSerializer;
import org.joda.time.DateTime;

import javax.persistence.*;
import javax.validation.constraints.AssertTrue;
import javax.validation.constraints.NotNull;
import java.util.UUID;

@Entity
@Table(name = "transicoes_proibidas")

public class TransicaoProibida extends Model {

    @Id
    private UUID id;

    @Column
    private String idJson;

    @NotNull(message = "não pode ficar em branco")
    @ManyToOne
    private Estagio origem;

    @NotNull(message = "não pode ficar em branco")
    @ManyToOne
    private Estagio destino;

    @NotNull(message = "não pode ficar em branco")
    @ManyToOne
    private Estagio alternativo;

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

    public TransicaoProibida() {
        super();
        this.setIdJson(UUID.randomUUID().toString());
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

    public Estagio getOrigem() {
        return origem;
    }

    public void setOrigem(Estagio origem) {
        this.origem = origem;
    }

    public Estagio getDestino() {
        return destino;
    }

    public void setDestino(Estagio destino) {
        this.destino = destino;
    }

    public Estagio getAlternativo() {
        return alternativo;
    }

    public void setAlternativo(Estagio alternativo) {
        this.alternativo = alternativo;
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

    @AssertTrue(groups = ControladorTransicoesProibidasCheck.class,
            message = "O estágio de origem deve ser diferente do estágio de destino.")
    public boolean isOrigemEDestinoDiferentes() {
        if (getOrigem() != null && getDestino() != null) {
            return (getOrigem() != getDestino());
        } else return true;
    }

    @AssertTrue(groups = ControladorTransicoesProibidasCheck.class,
            message = "Esse estágio só pode ser proibido com estágios do mesmo anel.")
    public boolean isOrigemEDestinoPertencemAoMesmoAnel() {
        if (getOrigem() != null && getDestino() != null) {
            return (getOrigem().getAnel() == getDestino().getAnel());
        } else return true;
    }

    @AssertTrue(groups = ControladorTransicoesProibidasCheck.class,
            message = "O Estágio alternativo deve ser diferente do destino.")
    public boolean isEstagioAlternativoDiferenteOrigemEDestino() {
        if (getAlternativo() != null && getOrigem() != null && getDestino() != null)
            return (getAlternativo() != getDestino());
        else return true;

    }

}
