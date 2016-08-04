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
import javax.validation.constraints.AssertTrue;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 * Created by rodrigosol on 6/22/16.
 */
@Entity

public class Transicao extends Model implements Cloneable {

    private static final long serialVersionUID = -6578371832958671414L;

    public static Finder<UUID, Transicao> find = new Finder<UUID, Transicao>(Transicao.class);

    @Id
    private UUID id;

    @Column
    private String idJson;
    @ManyToOne
    private GrupoSemaforico grupoSemaforico;
    @ManyToOne
    private Estagio origem;
    @ManyToOne
    private Estagio destino;
    @OneToMany(mappedBy = "transicao", cascade = CascadeType.ALL)
    @PrivateOwned
    @Valid
    private List<TabelaEntreVerdesTransicao> tabelaEntreVerdesTransicoes;
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
    @Column
    private boolean destroy;

    public Transicao() {
        super();
        this.setIdJson(UUID.randomUUID().toString());
    }

    public Transicao(GrupoSemaforico grupoSemaforico, Estagio origem, Estagio destino) {
        super();
        this.setIdJson(UUID.randomUUID().toString());
        this.grupoSemaforico = grupoSemaforico;
        this.origem = origem;
        this.destino = destino;
        this.destroy = false;
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

    public GrupoSemaforico getGrupoSemaforico() {
        return grupoSemaforico;
    }

    public void setGrupoSemaforico(GrupoSemaforico grupoSemaforico) {
        this.grupoSemaforico = grupoSemaforico;
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

    public List<TabelaEntreVerdesTransicao> getTabelaEntreVerdesTransicoes() {
        return tabelaEntreVerdesTransicoes;
    }

    public void setTabelaEntreVerdesTransicoes(List<TabelaEntreVerdesTransicao> tabelaEntreVerdes) {
        this.tabelaEntreVerdesTransicoes = tabelaEntreVerdes;
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

    public boolean isDestroy() {
        return destroy;
    }

    public void setDestroy(boolean destroy) {
        this.destroy = destroy;
    }

    @AssertTrue(groups = ControladorTabelaEntreVerdesCheck.class, message = "Essa transição deve ter pelo menos uma tabela de entreverdes.")
    public boolean isAoMenosUmaTabelaEntreVerdesTransicao() {
        return !getTabelaEntreVerdesTransicoes().isEmpty();

    }

    public void addTabelaEntreVerdesTransicao(TabelaEntreVerdesTransicao tabelaEntreVerdesTransicao) {
        if (getTabelaEntreVerdesTransicoes() == null) {
            setTabelaEntreVerdesTransicoes(new ArrayList<TabelaEntreVerdesTransicao>());
        }

        getTabelaEntreVerdesTransicoes().add(tabelaEntreVerdesTransicao);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Transicao transicao = (Transicao) o;

        return id != null ? id.equals(transicao.id) : transicao.id == null;

    }

    @Override
    public int hashCode() {
        return id != null ? id.hashCode() : 0;
    }

    @Override
    public Object clone() throws CloneNotSupportedException {
        return super.clone();
    }
}
