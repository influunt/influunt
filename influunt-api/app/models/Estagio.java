package models;

import checks.ControladorAssociacaoGruposSemaforicosCheck;
import checks.ControladorTransicoesProibidasCheck;
import com.avaje.ebean.Model;
import com.avaje.ebean.annotation.CreatedTimestamp;
import com.avaje.ebean.annotation.UpdatedTimestamp;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import json.deserializers.EstagioDeserializer;
import json.deserializers.InfluuntDateTimeDeserializer;
import json.serializers.EstagioSerializer;
import json.serializers.InfluuntDateTimeSerializer;
import org.joda.time.DateTime;

import javax.persistence.*;
import javax.validation.Valid;
import javax.validation.constraints.AssertTrue;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 * Entidade que representa o {@link Estagio} no sistema
 *
 * @author lesiopinheiro
 */
@Entity
@Table(name = "estagios")
@JsonSerialize(using = EstagioSerializer.class)
@JsonDeserialize(using = EstagioDeserializer.class)
public class Estagio extends Model implements Serializable {

    private static final long serialVersionUID = 5984122994022833262L;

    public static Finder<UUID, Estagio> find = new Finder<UUID, Estagio>(Estagio.class);

    @Id
    private UUID id;

    @OneToOne
    private Imagem imagem;

    @Column
    private String descricao;

    @Column
    private Integer tempoMaximoPermanencia;

    @Column
    private Boolean demandaPrioritaria = false;

    @OneToMany(mappedBy = "estagio")
    private List<EstagioGrupoSemaforico> estagiosGruposSemaforicos;

    @OneToMany(mappedBy = "origem", cascade = CascadeType.ALL)
    @Valid
    private List<TransicaoProibida> origemDeTransicoesProibidas;

    @OneToMany(mappedBy = "destino")
    private List<TransicaoProibida> destinoDeTransicoesProibidas;

    @OneToMany(mappedBy = "alternativo")
    private List<TransicaoProibida> alternativaDeTransicoesProibidas;

    @ManyToOne
    private Anel anel;

    @ManyToOne
    private Controlador controlador;

    private Detector detector;

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

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public Imagem getImagem() {
        return imagem;
    }

    public void setImagem(Imagem imagem) {
        this.imagem = imagem;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public Integer getTempoMaximoPermanencia() {
        return tempoMaximoPermanencia;
    }

    public void setTempoMaximoPermanencia(Integer tempoMaximoPermanencia) {
        this.tempoMaximoPermanencia = tempoMaximoPermanencia;
    }

    public Boolean getDemandaPrioritaria() {
        return demandaPrioritaria;
    }

    public void setDemandaPrioritaria(Boolean demandaPrioritaria) {
        this.demandaPrioritaria = demandaPrioritaria;
    }

    public List<EstagioGrupoSemaforico> getEstagiosGruposSemaforicos() {
        return estagiosGruposSemaforicos;
    }

    public void setEstagiosGruposSemaforicos(List<EstagioGrupoSemaforico> estagiosGruposSemaforicos) {
        this.estagiosGruposSemaforicos = estagiosGruposSemaforicos;
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

    public void addEstagioGrupoSemaforico(EstagioGrupoSemaforico estagioGrupoSemaforico) {
        if (getEstagiosGruposSemaforicos() == null) {
            setEstagiosGruposSemaforicos(new ArrayList<EstagioGrupoSemaforico>());
        }
        getEstagiosGruposSemaforicos().add(estagioGrupoSemaforico);
    }

    public Anel getAnel() {
        return anel;
    }

    public void setAnel(Anel anel) {
        this.anel = anel;
    }

    public List<TransicaoProibida> getOrigemDeTransicoesProibidas() {
        return origemDeTransicoesProibidas;
    }

    public void setOrigemDeTransicoesProibidas(List<TransicaoProibida> origemDeTransicoesProibidas) {
        this.origemDeTransicoesProibidas = origemDeTransicoesProibidas;
    }

    public List<TransicaoProibida> getDestinoDeTransicoesProibidas() {
        return destinoDeTransicoesProibidas;
    }

    public void setDestinoDeTransicoesProibidas(List<TransicaoProibida> destinoDeTransicoesProibidas) {
        this.destinoDeTransicoesProibidas = destinoDeTransicoesProibidas;
    }

    public List<TransicaoProibida> getAlternativaDeTransicoesProibidas() {
        return alternativaDeTransicoesProibidas;
    }

    public void setAlternativaDeTransicoesProibidas(List<TransicaoProibida> alternativaDeTransicoesProibidas) {
        this.alternativaDeTransicoesProibidas = alternativaDeTransicoesProibidas;
    }

    @AssertTrue(groups = ControladorAssociacaoGruposSemaforicosCheck.class,
            message = "Este estágio deve ser associado a pelo menos 1 grupo semafórico")
    public boolean isAoMenosUmEstagioGrupoSemaforico(){
        return getEstagiosGruposSemaforicos() != null && !getEstagiosGruposSemaforicos().isEmpty();
    }

    @AssertTrue(groups = ControladorTransicoesProibidasCheck.class,
            message = "Esse estágio não pode ter um estágio de destino e alternativo ao mesmo tempo.")
    public boolean isAoMesmoTempoDestinoEAlternativo(){
        if(!getDestinoDeTransicoesProibidas().isEmpty() && !getAlternativaDeTransicoesProibidas().isEmpty()) {
            return  getDestinoDeTransicoesProibidas().stream().filter(estagio -> getAlternativaDeTransicoesProibidas().contains(estagio)).count() == 0;
        }
        else return true;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Estagio estagio = (Estagio) o;

        return id != null ? id.equals(estagio.id) : estagio.id == null;

    }

    @Override
    public int hashCode() {
        return id != null ? id.hashCode() : 0;
    }


}
