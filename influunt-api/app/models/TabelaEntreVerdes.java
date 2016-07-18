package models;

import checks.ControladorTabelaEntreVerdesCheck;
import com.avaje.ebean.Model;
import com.avaje.ebean.annotation.CreatedTimestamp;
import com.avaje.ebean.annotation.UpdatedTimestamp;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import json.deserializers.InfluuntDateTimeDeserializer;
import json.deserializers.TabelaEntreVerdesDeserializer;
import json.serializers.InfluuntDateTimeSerializer;
import json.serializers.TabelaEntreVerdesSerializer;
import org.joda.time.DateTime;

import javax.persistence.*;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 * Created by rodrigosol on 6/22/16.
 */
@Entity
@JsonSerialize(using = TabelaEntreVerdesSerializer.class)
@JsonDeserialize(using = TabelaEntreVerdesDeserializer.class)
public class TabelaEntreVerdes extends Model implements Cloneable {

    @Id
    private UUID id;

    @Column
    private String descricao = "PADRÃO";

    @ManyToOne
    private GrupoSemaforico grupoSemaforico;

    @OneToMany(mappedBy = "tabelaEntreVerdes")
    private List<TabelaEntreVerdesTransicao> transicoes;

    @Valid
    @NotNull(message = "não pode ficar em branco.", groups = ControladorTabelaEntreVerdesCheck.class)
    @Column
    private Integer posicao;

    @Column
    @JsonDeserialize(using= InfluuntDateTimeDeserializer.class)
    @JsonSerialize(using= InfluuntDateTimeSerializer.class)
    @CreatedTimestamp
    private DateTime dataCriacao;

    @Column
    @JsonDeserialize(using= InfluuntDateTimeDeserializer.class)
    @JsonSerialize(using= InfluuntDateTimeSerializer.class)
    @UpdatedTimestamp
    private DateTime dataAtualizacao;


    public TabelaEntreVerdes(GrupoSemaforico grupoSemaforico) {
        super();
        this.posicao = 1;
        this.grupoSemaforico = grupoSemaforico;
    }

    public TabelaEntreVerdes() {
        super();
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

    public List<TabelaEntreVerdesTransicao> getTransicoes() {
        return transicoes;
    }

    public void setTransicoes(List<TabelaEntreVerdesTransicao> transicoes) {
        this.transicoes = transicoes;
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

    public void addTransicao(TabelaEntreVerdesTransicao tabelaEntreVerdesTransicao) {
        if(getTransicoes() == null) {
            setTransicoes(new ArrayList<TabelaEntreVerdesTransicao>());
        }

        getTransicoes().add(tabelaEntreVerdesTransicao);
    }

    @Override
    public Object clone() throws CloneNotSupportedException {
        return super.clone();
    }

}
