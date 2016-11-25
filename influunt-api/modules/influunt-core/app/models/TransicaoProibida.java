package models;

import checks.ControladorTransicoesProibidasCheck;
import com.avaje.ebean.Model;
import com.avaje.ebean.annotation.ChangeLog;
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
import java.io.Serializable;
import java.util.UUID;

@Entity
@Table(name = "transicoes_proibidas")
@ChangeLog
public class TransicaoProibida extends Model implements Serializable {

    private static final long serialVersionUID = -3825474482773695751L;

    public static Finder<UUID, TransicaoProibida> find = new Finder<UUID, TransicaoProibida>(TransicaoProibida.class);

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

    @Transient
    private boolean destroy;

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
        this.idJson = UUID.randomUUID().toString();
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

    @AssertTrue(groups = ControladorTransicoesProibidasCheck.class,
        message = "O Estágio de origem não pode ter transição proibida para estágio alternativo.")
    public boolean isOrigemNaoPossuiTransicaoProibidaParaAlternativo() {
        if (!isDestroy() && getAlternativo() != null && getOrigem() != null) {
            return !getOrigem().temTransicaoProibidaParaEstagio(getAlternativo());
        }
        return true;
    }

    public boolean isDestroy() {
        return destroy;
    }

    public void setDestroy(boolean destroy) {
        this.destroy = destroy;
    }
}
