package models;

import checks.ControladorAssociacaoDetectoresCheck;
import checks.ControladorAssociacaoGruposSemaforicosCheck;
import checks.ControladorTransicoesProibidasCheck;
import checks.PlanosCheck;
import com.avaje.ebean.Model;
import com.avaje.ebean.annotation.CreatedTimestamp;
import com.avaje.ebean.annotation.UpdatedTimestamp;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import json.deserializers.InfluuntDateTimeDeserializer;
import json.serializers.InfluuntDateTimeSerializer;
import org.apache.commons.lang3.Range;
import org.joda.time.DateTime;

import javax.persistence.*;
import javax.validation.Valid;
import javax.validation.constraints.AssertTrue;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.UUID;
import java.util.stream.Collectors;

/**
 * Entidade que representa o {@link Estagio} no sistema
 *
 * @author lesiopinheiro
 */
@Entity
@Table(name = "estagios")
public class Estagio extends Model implements Serializable, Cloneable {

    private static final long serialVersionUID = 5984122994022833262L;

    public static Finder<UUID, Estagio> find = new Finder<UUID, Estagio>(Estagio.class);

    @Id
    private UUID id;

    @Column
    private String idJson;

    @OneToOne
    private Imagem imagem;

    @Column
    private String descricao;

    @Column
    private Integer tempoMaximoPermanencia;

    @Column
    private Boolean tempoMaximoPermanenciaAtivado = true;

    @Column
    @NotNull(message = "não pode ficar em branco", groups = ControladorAssociacaoGruposSemaforicosCheck.class)
    private Integer posicao;

    @Column
    private Boolean demandaPrioritaria = false;

    @OneToMany(mappedBy = "estagio", cascade = CascadeType.ALL)
    private List<EstagioGrupoSemaforico> estagiosGruposSemaforicos;

    @OneToMany(mappedBy = "estagio", cascade = CascadeType.REMOVE)
    private List<EstagioPlano> estagiosPlanos;

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

    @OneToOne(mappedBy = "estagio")
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

    public Estagio() {
        super();
        this.idJson = UUID.randomUUID().toString();
    }

    public Estagio(Integer posicao) {
        super();
        this.idJson = UUID.randomUUID().toString();
        this.posicao = posicao;
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

    public Boolean getTempoMaximoPermanenciaAtivado() {
        return tempoMaximoPermanenciaAtivado;
    }

    public void setTempoMaximoPermanenciaAtivado(Boolean tempoMaximoPermanenciaAtivado) {
        this.tempoMaximoPermanenciaAtivado = tempoMaximoPermanenciaAtivado;
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

    public Detector getDetector() {
        return detector;
    }

    public void setDetector(Detector detector) {
        this.detector = detector;
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

    public List<GrupoSemaforico> getGruposSemaforicos() {
        return getEstagiosGruposSemaforicos().stream().map(estagioGrupoSemaforico -> estagioGrupoSemaforico.getGrupoSemaforico()).collect(Collectors.toList());
    }

    @AssertTrue(groups = ControladorAssociacaoGruposSemaforicosCheck.class,
            message = "Este estágio deve ser associado a pelo menos 1 grupo semafórico")
    public boolean isAoMenosUmEstagioGrupoSemaforico() {
        return getEstagiosGruposSemaforicos() != null && !getEstagiosGruposSemaforicos().isEmpty();
    }

    @AssertTrue(groups = ControladorAssociacaoGruposSemaforicosCheck.class,
            message = "Existem grupos semafóricos conflitantes associados a esse estágio.")
    public boolean isNaoDevePossuirGruposSemaforicosConflitantes() {
        if (getEstagiosGruposSemaforicos() != null && !getEstagiosGruposSemaforicos().isEmpty()) {
            return !getEstagiosGruposSemaforicos().stream().anyMatch(estagioGrupoSemaforico -> estagioGrupoSemaforico.getGrupoSemaforico().conflitaCom(this.getGruposSemaforicos()));
        }
        return true;
    }


    @AssertTrue(groups = ControladorTransicoesProibidasCheck.class,
            message = "Esse estágio não pode ter um estágio de destino e alternativo ao mesmo tempo.")
    public boolean isAoMesmoTempoDestinoEAlternativo() {
        if (!getDestinoDeTransicoesProibidas().isEmpty() && !getAlternativaDeTransicoesProibidas().isEmpty()) {
            return getDestinoDeTransicoesProibidas().stream().filter(estagio -> getAlternativaDeTransicoesProibidas().contains(estagio)).count() == 0;
        } else return true;
    }

    @AssertTrue(groups = ControladorAssociacaoDetectoresCheck.class,
            message = "Esse estagio deve estar associado a pelo menos um detector.")
    public boolean isAssociadoDetectorCasoDemandaPrioritaria() {
        if (Boolean.TRUE.equals(getDemandaPrioritaria())) {
            return Objects.nonNull(getDetector());
        }
        return true;
    }

    @AssertTrue(groups = PlanosCheck.class, message = "deve estar entre 60 e 255")
    public boolean istempoMaximoPermanencia() {
        if (getTempoMaximoPermanenciaAtivado()) {
            return !(getTempoMaximoPermanencia() == null || !Range.between(60, 255).contains(getTempoMaximoPermanencia()));
        }
        return true;
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


    public boolean temTransicaoProibidaParaEstagio(Estagio estagio) {
        return getOrigemDeTransicoesProibidas().stream().filter(transicaoProibida -> transicaoProibida.getDestino().equals(estagio)).count() > 0;
    }


    @Override
    public Object clone() throws CloneNotSupportedException {
        return super.clone();
    }

    public Integer getPosicao() {
        return posicao;
    }

    public void setPosicao(Integer posicao) {
        this.posicao = posicao;
    }

    public void addTransicaoProibidaOrigem(TransicaoProibida transicaoProibida) {
        if (getOrigemDeTransicoesProibidas() == null) {
            setOrigemDeTransicoesProibidas(new ArrayList<TransicaoProibida>());
        }
        getOrigemDeTransicoesProibidas().add(transicaoProibida);
    }

    public void addTransicaoProibidaDestino(TransicaoProibida transicaoProibida) {
        if (getDestinoDeTransicoesProibidas() == null) {
            setDestinoDeTransicoesProibidas(new ArrayList<TransicaoProibida>());
        }
        getDestinoDeTransicoesProibidas().add(transicaoProibida);
    }

    public void addTransicaoProibidaAlternativa(TransicaoProibida transicaoProibida) {
        if (getAlternativaDeTransicoesProibidas() == null) {
            setAlternativaDeTransicoesProibidas(new ArrayList<TransicaoProibida>());
        }
        getAlternativaDeTransicoesProibidas().add(transicaoProibida);
    }

    public List<EstagioPlano> getEstagiosPlanos() {
        return estagiosPlanos;
    }

    public void setEstagiosPlanos(List<EstagioPlano> estagiosPlanos) {
        this.estagiosPlanos = estagiosPlanos;
    }

    public boolean temDetectorVeicular() {
        return this.getDetector() != null && getDetector().isVeicular();
    }

    public boolean isAssociadoAGrupoSemaforicoVeicular() {
        return this.getGruposSemaforicos().stream().anyMatch(grupoSemaforico -> grupoSemaforico.isVeicular());
    }
}
