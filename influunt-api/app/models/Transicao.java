package models;

import checks.ControladorTabelaEntreVerdesCheck;
import com.avaje.ebean.Model;
import com.avaje.ebean.annotation.CreatedTimestamp;
import com.avaje.ebean.annotation.PrivateOwned;
import com.avaje.ebean.annotation.UpdatedTimestamp;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import json.deserializers.InfluuntDateTimeDeserializer;
import json.deserializers.TransicaoDeserializer;
import json.serializers.InfluuntDateTimeSerializer;
import json.serializers.TransicaoSerializer;
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
@JsonSerialize(using = TransicaoSerializer.class)
@JsonDeserialize(using = TransicaoDeserializer.class)
public class Transicao extends Model implements Cloneable{

    @Id
    private UUID id;

    @ManyToOne
    private GrupoSemaforico grupoSemaforico;

    @ManyToOne
    private Estagio origem;

    @ManyToOne
    private Estagio destino;

    @OneToMany(mappedBy = "transicao", cascade = CascadeType.ALL)
    @PrivateOwned
    @Valid
    private List<TabelaEntreVerdesTransicao> tabelaEntreVerdes;

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

    public Transicao(){
        super();
    }

    public Transicao(GrupoSemaforico grupoSemaforico, Estagio origem, Estagio destino) {
        super();
        this.grupoSemaforico = grupoSemaforico;
        this.origem = origem;
        this.destino = destino;
        this.destroy = false;
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

    public List<TabelaEntreVerdesTransicao> getTabelaEntreVerdes() {
        return tabelaEntreVerdes;
    }

    public void setTabelaEntreVerdes(List<TabelaEntreVerdesTransicao> tabelaEntreVerdes) {
        this.tabelaEntreVerdes = tabelaEntreVerdes;
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
        return !getTabelaEntreVerdes().isEmpty();

    }

    public void addTabelaEntreVerdes(TabelaEntreVerdesTransicao tabelaEntreVerdesTransicao) {
        if(getTabelaEntreVerdes() == null) {
            setTabelaEntreVerdes(new ArrayList<TabelaEntreVerdesTransicao>());
        }

        getTabelaEntreVerdes().add(tabelaEntreVerdesTransicao);
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
