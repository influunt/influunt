package models;

import checks.ControladorAssociacaoGruposSemaforicosCheck;
import com.avaje.ebean.Model;
import com.avaje.ebean.annotation.CreatedTimestamp;
import com.avaje.ebean.annotation.UpdatedTimestamp;
import com.fasterxml.jackson.annotation.*;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import models.deserializers.EstagioDeserializer;
import models.serializers.EstagioSerializer;
import org.joda.time.DateTime;
import models.deserializers.InfluuntDateTimeDeserializer;
import models.serializers.InfluuntDateTimeSerializer;

import javax.persistence.*;
import javax.validation.Valid;
import javax.validation.constraints.AssertTrue;
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
public class Estagio extends Model {

    private static final long serialVersionUID = 5984122994022833262L;

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

    @OneToOne
    private Movimento movimento;

    @OneToMany(mappedBy = "estagio", fetch = FetchType.EAGER)
//    @JoinColumn(name = "estagio_id")
    @Valid
    private List<EstagioGrupoSemaforico> estagiosGruposSemaforicos;


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

    public Movimento getMovimento() {
        return movimento;
    }

    public void setMovimento(Movimento movimento) {
        this.movimento = movimento;
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

//    @AssertTrue(groups = ControladorAssociacaoGruposSemaforicosCheck.class,
//            message = "Este estágio deve ser associado a pelo menos 1 grupo semafórico"
//    )
//    public boolean isAoMenosUmEstagioGrupoSemaforico(){
//        return getEstagiosGruposSemaforicos() != null && !getEstagiosGruposSemaforicos().isEmpty();
//    }
}
