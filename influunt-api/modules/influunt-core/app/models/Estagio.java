package models;

import checks.ControladorAssociacaoDetectoresCheck;
import checks.ControladorAssociacaoGruposSemaforicosCheck;
import checks.ControladorTransicoesProibidasCheck;
import checks.PlanosCheck;
import com.avaje.ebean.Model;
import com.avaje.ebean.annotation.ChangeLog;
import com.avaje.ebean.annotation.CreatedTimestamp;
import com.avaje.ebean.annotation.UpdatedTimestamp;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import json.deserializers.InfluuntDateTimeDeserializer;
import json.serializers.InfluuntDateTimeSerializer;
import org.joda.time.DateTime;
import utils.DBUtils;
import utils.RangeUtils;

import javax.persistence.*;
import javax.validation.Valid;
import javax.validation.constraints.AssertTrue;
import javax.validation.constraints.NotNull;
import java.io.File;
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
@ChangeLog
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

    @Column
    private Integer tempoVerdeDemandaPrioritaria;

    @OneToMany(mappedBy = "estagio", cascade = CascadeType.ALL)
    private List<EstagioGrupoSemaforico> estagiosGruposSemaforicos;

    @OneToMany(mappedBy = "estagio", cascade = CascadeType.REMOVE)
    private List<EstagioPlano> estagiosPlanos;

    @OneToMany(mappedBy = "origem", cascade = CascadeType.REMOVE)
    private List<Transicao> origemDeTransicoes;

    @OneToMany(mappedBy = "destino", cascade = CascadeType.REMOVE)
    private List<Transicao> destinoDeTransicoes;

    @OneToMany(mappedBy = "origem", cascade = CascadeType.ALL)
    @Valid
    private List<TransicaoProibida> origemDeTransicoesProibidas;

    @OneToMany(mappedBy = "destino", cascade = CascadeType.REMOVE)
    private List<TransicaoProibida> destinoDeTransicoesProibidas;

    @OneToMany(mappedBy = "alternativo", cascade = CascadeType.REMOVE)
    private List<TransicaoProibida> alternativaDeTransicoesProibidas;

    @ManyToOne
    private Anel anel;

    @ManyToOne
    private Controlador controlador;

    @OneToOne(mappedBy = "estagio", cascade = CascadeType.REMOVE)
    private Detector detector;

    @Transient
    private boolean destroy = false;

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

    public boolean delete(File rootPath) {
        return DBUtils.executeWithTransaction(() -> {
            this.setDestroy(true);
            Anel anel = this.getAnel();
            List<Estagio> estagios = anel.getEstagios();
            estagios.sort((anterior, proximo) -> {
                if (anterior.getPosicao() != null && proximo.getPosicao() != null) {
                    return anterior.getPosicao().compareTo(proximo.getPosicao());
                }
                return anterior.getPosicao() == null ? 1 : -1;
            });
            int posicao = 1;
            for (Estagio e : estagios) {
                if (!e.isDestroy()) {
                    e.setPosicao(posicao++);
                    e.update();
                }
            }

            Imagem imagem = getImagem();
            if (this.delete() && imagem != null) {
                imagem.apagar(rootPath);
            }
        });
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

    public Integer getTempoVerdeDemandaPrioritaria() {
        return tempoVerdeDemandaPrioritaria;
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
        return getEstagiosGruposSemaforicos().stream()
                .filter(estagioGrupoSemaforico -> !estagioGrupoSemaforico.isDestroy())
                .map(EstagioGrupoSemaforico::getGrupoSemaforico).collect(Collectors.toList());
    }

    private boolean isEstagiosGrupoSemaforicosNotEmpty() {
        return getEstagiosGruposSemaforicos() != null
                && !getEstagiosGruposSemaforicos().isEmpty()
                && !getEstagiosGruposSemaforicos().stream()
                .filter(estagioGrupoSemaforico -> !estagioGrupoSemaforico.isDestroy())
                .collect(Collectors.toList()).isEmpty();
    }

    @AssertTrue(groups = ControladorAssociacaoGruposSemaforicosCheck.class,
            message = "Este estágio deve ser associado a pelo menos 1 grupo semafórico")
    public boolean isAoMenosUmEstagioGrupoSemaforico() {
        return isEstagiosGrupoSemaforicosNotEmpty();
    }

    @AssertTrue(groups = ControladorAssociacaoGruposSemaforicosCheck.class,
            message = "Estágio de demanda prioritária deve ser associado a somente 1 grupo semafórico.")
    public boolean isSomenteUmEstagioGrupoSemaforicoEmDemandaPrioritaria() {
        if (isEstagiosGrupoSemaforicosNotEmpty() && isDemandaPrioritaria()) {
            return getEstagiosGruposSemaforicos().size() <= 1;
        }
        return true;
    }

    @AssertTrue(groups = ControladorAssociacaoGruposSemaforicosCheck.class,
            message = "Estágio de demanda prioritária deve ser associado a um grupo semafórico veicular.")
    public boolean isUmGrupoSemaforicoVeicularEmDemandaPrioritaria() {
        if (isEstagiosGrupoSemaforicosNotEmpty() && isDemandaPrioritaria() && isSomenteUmEstagioGrupoSemaforicoEmDemandaPrioritaria()) {
            return getEstagiosGruposSemaforicos().get(0).getGrupoSemaforico().isVeicular();
        }
        return true;
    }

    @AssertTrue(groups = ControladorAssociacaoGruposSemaforicosCheck.class,
            message = "Existem grupos semafóricos conflitantes associados a esse estágio.")
    public boolean isNaoDevePossuirGruposSemaforicosConflitantes() {
        if (isEstagiosGrupoSemaforicosNotEmpty()) {
            return !getEstagiosGruposSemaforicos().stream().anyMatch(estagioGrupoSemaforico -> !estagioGrupoSemaforico.isDestroy() && estagioGrupoSemaforico.getGrupoSemaforico().conflitaCom(this.getGruposSemaforicos()));
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

    @AssertTrue(groups = ControladorTransicoesProibidasCheck.class,
            message = "Um estágio de demanda prioritária não pode ter transição proibida.")
    public boolean isNaoPossuiTransicaoProibidaCasoDemandaPrioritaria() {
        if (isDemandaPrioritaria()) {
            return getOrigemDeTransicoesProibidas().size() == 0 && getDestinoDeTransicoesProibidas().size() == 0 && getAlternativaDeTransicoesProibidas().size() == 0;
        } else return true;
    }

    @AssertTrue(groups = ControladorAssociacaoDetectoresCheck.class,
            message = "Esse estágio deve estar associado a pelo menos um detector.")
    public boolean isAssociadoDetectorCasoDemandaPrioritaria() {
        if (Boolean.TRUE.equals(getDemandaPrioritaria())) {
            return Objects.nonNull(getDetector());
        }
        return true;
    }

    @AssertTrue(groups = PlanosCheck.class, message = "Tempo máximo de permanência deve estar entre {min} e {max}")
    public boolean istempoMaximoPermanencia() {
        return isTempoMaximoPermanenciaOk();
    }

    @AssertTrue(groups = ControladorAssociacaoGruposSemaforicosCheck.class, message = "Tempo máximo de permanência deve estar entre {min} e {max}")
    public boolean isTempoMaximoPermanenciaOk() {
        if (getTempoMaximoPermanenciaAtivado()) {
            return getTempoMaximoPermanencia() != null &&
                    RangeUtils.getInstance().TEMPO_MAXIMO_PERMANENCIA_ESTAGIO.contains(getTempoMaximoPermanencia());
        }
        return true;
    }

    @AssertTrue(groups = ControladorAssociacaoGruposSemaforicosCheck.class, message = "O Tempo de verde do estágio de demanda priortária deve estar entre {min} e {max}")
    public boolean isTempoVerdeDemandaPrioritaria() {
        if (isDemandaPrioritaria()) {
            return getTempoVerdeDemandaPrioritaria() != null &&
                    RangeUtils.getInstance().TEMPO_VERDE.contains(getTempoVerdeDemandaPrioritaria());
        }
        return true;
    }

    public void setTempoVerdeDemandaPrioritaria(Integer tempoVerdeDemandaPrioritaria) {
        this.tempoVerdeDemandaPrioritaria = tempoVerdeDemandaPrioritaria;
    }

    @AssertTrue(groups = ControladorTransicoesProibidasCheck.class,
            message = "Esse estágio deve possuir ao menos uma transição válida para outro estágio.")
    public boolean isEstagioPossuiAoMenosUmaTransicaoOrigemValida() {
        for (Estagio estagio : getAnel().getEstagios()) {
            if (!Objects.equals(getIdJson(), estagio.getIdJson()) && !temTransicaoProibidaParaEstagio(estagio)) {
                return true;
            }
        }
        return false;
    }

    @AssertTrue(groups = ControladorTransicoesProibidasCheck.class,
            message = "Pelo menos um estágio deve ter uma transição válida para esse estágio.")
    public boolean isEstagioPossuiAoMenosUmaTransicaoDestinoValida() {
        for (Estagio estagio : getAnel().getEstagios()) {
            if (!Objects.equals(getIdJson(), estagio.getIdJson()) && !estagio.temTransicaoProibidaParaEstagio(this)) {
                return true;
            }
        }
        return false;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Estagio estagio = (Estagio) o;

        return id != null ? id.equals(estagio.id) : idJson != null ? idJson.equals(estagio.idJson) : estagio.id == null;
    }

    @Override
    public int hashCode() {
        return id != null ? id.hashCode() : 0;
    }

    public boolean temTransicaoProibidaParaEstagio(Estagio estagio) {
        return getTransicaoProibidaPara(estagio) != null;
    }

    public TransicaoProibida getTransicaoProibidaPara(final Estagio destino) {
        return getOrigemDeTransicoesProibidas().stream().filter(transicaoProibida -> {
            return destino.equals(transicaoProibida.getDestino());
        }).findFirst().orElse(null);
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

    public void addEstagioPlano(EstagioPlano estagioPlano) {
        if (getEstagiosPlanos() == null) {
            setEstagiosPlanos(new ArrayList<EstagioPlano>());
        }

        getEstagiosPlanos().add(estagioPlano);
    }

    public boolean temDetectorVeicular() {
        return this.getDetector() != null && getDetector().isVeicular();
    }

    public boolean isAssociadoAGrupoSemaforicoVeicular() {
        return this.getGruposSemaforicos().stream().anyMatch(grupoSemaforico -> grupoSemaforico.isVeicular());
    }

    public List<Transicao> getOrigemDeTransicoes() {
        return origemDeTransicoes;
    }

    public void setOrigemDeTransicoes(List<Transicao> origemDeTransicoes) {
        this.origemDeTransicoes = origemDeTransicoes;
    }

    public void addOrigemDeTransicoes(Transicao transicao) {
        if (getOrigemDeTransicoes() == null) {
            setOrigemDeTransicoes(new ArrayList<Transicao>());
        }
        getOrigemDeTransicoes().add(transicao);
    }

    public List<Transicao> getDestinoDeTransicoes() {
        return destinoDeTransicoes;
    }

    public void setDestinoDeTransicoes(List<Transicao> destinoDeTransicoes) {
        this.destinoDeTransicoes = destinoDeTransicoes;
    }

    public void addDestinoDeTransicoes(Transicao transicao) {
        if (getDestinoDeTransicoes() == null) {
            setDestinoDeTransicoes(new ArrayList<Transicao>());
        }
        getDestinoDeTransicoes().add(transicao);
    }

    public boolean isDestroy() {
        return destroy;
    }

    public void setDestroy(boolean destroy) {
        this.destroy = destroy;
    }

    public boolean isDemandaPrioritaria() {
        return demandaPrioritaria;
    }

    public void setDemandaPrioritaria(Boolean demandaPrioritaria) {
        this.demandaPrioritaria = demandaPrioritaria;
    }
}
