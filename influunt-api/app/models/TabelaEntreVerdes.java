package models;

import checks.ControladorTabelaEntreVerdesCheck;
import com.avaje.ebean.Model;
import com.avaje.ebean.annotation.CreatedTimestamp;
import com.avaje.ebean.annotation.PrivateOwned;
import com.avaje.ebean.annotation.UpdatedTimestamp;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import json.deserializers.InfluuntDateTimeDeserializer;
import json.serializers.InfluuntDateTimeSerializer;
import org.joda.time.DateTime;

import javax.persistence.*;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 * Created by rodrigosol on 6/22/16.
 */
@Entity

public class TabelaEntreVerdes extends Model implements Cloneable, Serializable {

    private static final long serialVersionUID = -5424393072628559818L;

    public static Finder<UUID, TabelaEntreVerdes> find = new Finder<UUID, TabelaEntreVerdes>(TabelaEntreVerdes.class);

    @Id
    private UUID id;

    @Column
    private String idJson;

    @Column
    private String descricao = "PADRÃO";

    @ManyToOne
    private GrupoSemaforico grupoSemaforico;

    @OneToMany(mappedBy = "tabelaEntreVerdes", cascade = CascadeType.ALL)
    @PrivateOwned
    private List<TabelaEntreVerdesTransicao> tabelaEntreVerdesTransicoes;

    @Valid
    @NotNull(message = "não pode ficar em branco.", groups = ControladorTabelaEntreVerdesCheck.class)
    @Column
    private Integer posicao;

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

    @Transient
    private boolean destroy;

    public TabelaEntreVerdes(GrupoSemaforico grupoSemaforico, Integer posicao) {
        super();
        this.idJson = UUID.randomUUID().toString();
        this.grupoSemaforico = grupoSemaforico;
        this.posicao = posicao;
    }

    public TabelaEntreVerdes() {
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

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public GrupoSemaforico getGrupoSemaforico() {
        return grupoSemaforico;
    }

    public void setGrupoSemaforico(GrupoSemaforico grupoSemaforico) {
        this.grupoSemaforico = grupoSemaforico;
    }

    public List<TabelaEntreVerdesTransicao> getTabelaEntreVerdesTransicoes() {
        return tabelaEntreVerdesTransicoes;
    }

    public void setTabelaEntreVerdesTransicoes(List<TabelaEntreVerdesTransicao> transicoes) {
        this.tabelaEntreVerdesTransicoes = transicoes;
    }

    public Integer getPosicao() {
        return posicao;
    }

    public void setPosicao(Integer posicao) {
        this.posicao = posicao;
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

    public void addTabelaEntreVerdesTransicao(TabelaEntreVerdesTransicao tabelaEntreVerdesTransicao) {
        if (getTabelaEntreVerdesTransicoes() == null) {
            setTabelaEntreVerdesTransicoes(new ArrayList<TabelaEntreVerdesTransicao>());
        }

        getTabelaEntreVerdesTransicoes().add(tabelaEntreVerdesTransicao);
    }

    @Override
    public Object clone() throws CloneNotSupportedException {
        return super.clone();
    }

    public boolean isDestroy() {
        return destroy;
    }

    public void setDestroy(boolean destroy) {
        this.destroy = destroy;
    }
}
