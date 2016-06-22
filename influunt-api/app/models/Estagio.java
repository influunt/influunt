package models;

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
public class Estagio extends Model {

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

    @OneToMany(mappedBy = "origem")
    private List<TransicaoProibida> origemDeTransacoesProibidas;

    @OneToMany(mappedBy = "destino")
    private List<TransicaoProibida> destinoDeTransacoesProibidas;

    @OneToMany(mappedBy = "alternativo")
    private List<TransicaoProibida> alternativaDeTransacoesProibidas;

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


//    @AssertTrue(groups = ControladorAssociacaoGruposSemaforicosCheck.class,
//            message = "Este estágio deve ser associado a pelo menos 1 grupo semafórico"
//    )
//    public boolean isAoMenosUmEstagioGrupoSemaforico(){
//        return getEstagiosGruposSemaforicos() != null && !getEstagiosGruposSemaforicos().isEmpty();
//    }

}
