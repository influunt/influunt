package models;

import checks.ControladorGruposSemaforicosCheck;
import checks.ControladorTabelaEntreVerdesCheck;
import checks.ControladorVerdesConflitantesCheck;
import com.avaje.ebean.Model;
import com.avaje.ebean.annotation.CreatedTimestamp;
import com.avaje.ebean.annotation.PrivateOwned;
import com.avaje.ebean.annotation.UpdatedTimestamp;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import json.deserializers.InfluuntDateTimeDeserializer;
import json.serializers.InfluuntDateTimeSerializer;
import org.apache.commons.collections.ListUtils;
import org.joda.time.DateTime;
import utils.RangeUtils;

import javax.persistence.*;
import javax.validation.Valid;
import javax.validation.constraints.AssertTrue;
import javax.validation.constraints.NotNull;
import java.util.*;
import java.util.stream.Collectors;

/**
 * Entidade que representa o {@link GrupoSemaforico} no sistema
 *
 * @author lesiopinheiro
 */
@Entity
@Table(name = "grupos_semaforicos")

public class GrupoSemaforico extends Model implements Cloneable {

    public static Finder<UUID, GrupoSemaforico> find = new Finder<UUID, GrupoSemaforico>(GrupoSemaforico.class);

    private static final long serialVersionUID = 7439393568357903233L;

    @Id
    private UUID id;

    @Column
    private String idJson;

    @Enumerated(EnumType.STRING)
    @Column
    @NotNull(groups = ControladorGruposSemaforicosCheck.class, message = "não pode ficar em branco")
    private TipoGrupoSemaforico tipo;

    @Column
    private String descricao;

    @ManyToOne
    @JoinColumn(name = "anel_id")
    private Anel anel;

    @OneToMany(mappedBy = "grupoSemaforico", cascade = CascadeType.ALL)
    private List<EstagioGrupoSemaforico> estagiosGruposSemaforicos;

    @ManyToOne
    private Controlador controlador;

    @OneToMany(mappedBy = "origem", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @Valid
    private List<VerdesConflitantes> verdesConflitantesOrigem;

    @OneToMany(mappedBy = "destino", cascade = CascadeType.ALL)
    @Valid
    private List<VerdesConflitantes> verdesConflitantesDestino;

    @OneToMany(mappedBy = "grupoSemaforico", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private List<TabelaEntreVerdes> tabelasEntreVerdes;

    @OneToMany(mappedBy = "grupoSemaforico", cascade = CascadeType.ALL)
    @PrivateOwned
    @Valid
    private List<Transicao> transicoes;

    @Column
    private Integer posicao;

    /**
     * Campo que define o procedimento quando a fase vermelha está sempre apagada
     * {@code Boolean.TRUE} - colocar o cruzamento em Amarelo intermitente
     * {@code Boolean.FALSE} - nada é feito
     */
    @Column
    private Boolean faseVermelhaApagadaAmareloIntermitente = false;

    @Column
    @NotNull(groups = ControladorGruposSemaforicosCheck.class, message = "não pode ficar em branco")
    private Integer tempoVerdeSeguranca;

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

    public GrupoSemaforico() {
        super();
        this.setIdJson(UUID.randomUUID().toString());
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

    public TipoGrupoSemaforico getTipo() {
        return tipo;
    }

    public void setTipo(TipoGrupoSemaforico tipo) {
        this.tipo = tipo;
    }

    public Anel getAnel() {
        return anel;
    }

    public void setAnel(Anel anel) {
        this.anel = anel;
    }

    public List<EstagioGrupoSemaforico> getEstagiosGruposSemaforicos() {
        return estagiosGruposSemaforicos;
    }

    public void setEstagiosGruposSemaforicos(List<EstagioGrupoSemaforico> estagiosGruposSemaforicos) {
        this.estagiosGruposSemaforicos = estagiosGruposSemaforicos;
    }

    public Controlador getControlador() {
        return controlador;
    }

    public void setControlador(Controlador controlador) {
        this.controlador = controlador;
    }

    public List<VerdesConflitantes> getVerdesConflitantesOrigem() {
        return verdesConflitantesOrigem;
    }

    public void setVerdesConflitantesOrigem(List<VerdesConflitantes> verdesConflitantesOrigem) {
        this.verdesConflitantesOrigem = verdesConflitantesOrigem;
    }

    public List<VerdesConflitantes> getVerdesConflitantesDestino() {
        return verdesConflitantesDestino;
    }

    public void setVerdesConflitantesDestino(List<VerdesConflitantes> verdesConflitantesDestino) {
        this.verdesConflitantesDestino = verdesConflitantesDestino;
    }

    public Integer getPosicao() {
        return posicao;
    }

    public void setPosicao(Integer posicao) {
        this.posicao = posicao;
    }

    public Boolean getFaseVermelhaApagadaAmareloIntermitente() {
        return faseVermelhaApagadaAmareloIntermitente;
    }

    public void setFaseVermelhaApagadaAmareloIntermitente(Boolean faseVermelhaApagadaAmareloIntermitente) {
        this.faseVermelhaApagadaAmareloIntermitente = faseVermelhaApagadaAmareloIntermitente;
    }

    public Integer getTempoVerdeSeguranca() {
        return tempoVerdeSeguranca;
    }

    public void setTempoVerdeSeguranca(Integer tempoVerdeSeguranca) {
        this.tempoVerdeSeguranca = tempoVerdeSeguranca;
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

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public void addEstagioGrupoSemaforico(EstagioGrupoSemaforico estagioGrupoSemaforico) {
        if (getEstagiosGruposSemaforicos() == null) {
            setEstagiosGruposSemaforicos(new ArrayList<EstagioGrupoSemaforico>());
        }
        getEstagiosGruposSemaforicos().add(estagioGrupoSemaforico);
    }

    public List<TabelaEntreVerdes> getTabelasEntreVerdes() {
        return tabelasEntreVerdes;
    }

    public void setTabelasEntreVerdes(List<TabelaEntreVerdes> tabelasEntreVerdes) {
        this.tabelasEntreVerdes = tabelasEntreVerdes;
    }

    public List<Transicao> getTransicoes() {
        return transicoes;
    }

    public List<Transicao> getTransicoesComGanhoDePassagem() {
        return transicoes.stream().filter(Transicao::isGanhoDePassagem).collect(Collectors.toList());
    }

    public List<Transicao> getTransicoesComPerdaDePassagem() {
        return transicoes.stream().filter(Transicao::isPerdaDePassagem).collect(Collectors.toList());
    }

    public void setTransicoes(List<Transicao> transicoes) {
        this.transicoes = transicoes;
    }

    public TabelaEntreVerdes findByTabelaEntreVerdesPadrao() {
        if (getTabelasEntreVerdes().isEmpty()) {
            this.addTabelaEntreVerdes(new TabelaEntreVerdes(this, 1));
        }
        return getTabelasEntreVerdes().stream().filter(tabelaEntreVerdes -> tabelaEntreVerdes.getPosicao().equals(1)).findFirst().get();
    }

    @JsonIgnore
    @AssertTrue(groups = ControladorVerdesConflitantesCheck.class, message = "Esse grupo semafórico deve ter ao menos um verde conflitante")
    public boolean isAoMenosUmVerdeConflitante() {
        if (this.getAnel() != null) {
            return this.getVerdesConflitantes() != null && !this.getVerdesConflitantes().isEmpty();
        } else {
            return true;
        }
    }

    @JsonIgnore
    @AssertTrue(groups = ControladorVerdesConflitantesCheck.class, message = "Esse grupo semafórico não pode ter verde conflitante com ele mesmo")
    public boolean isNaoConflitaComEleMesmo() {
        if (this.getVerdesConflitantes() != null && !this.getVerdesConflitantes().isEmpty()) {
            return !this.getVerdesConflitantes().stream().anyMatch(grupoSemaforico -> grupoSemaforico.conflitaComEleMesmo(this));
        } else {
            return true;
        }
    }

    @JsonIgnore
    @AssertTrue(groups = ControladorVerdesConflitantesCheck.class, message = "Esse grupo semafórico não pode ter verde conflitante com grupo semafórico de outro anel")
    public boolean isNaoConflitaComGruposDeOutroAnel() {
        if (this.getAnel() != null && this.getVerdesConflitantes() != null && !this.getVerdesConflitantes().isEmpty()) {
            return !this.getVerdesConflitantes().stream().anyMatch(grupoSemaforico -> grupoSemaforico.conflitaGrupoSemaforicoOutroAnel(this));
        } else {
            return true;
        }
    }

    @JsonIgnore
    @AssertTrue(groups = ControladorTabelaEntreVerdesCheck.class, message = "Esse grupo semafórico deve ter no máximo o número de tabelas entre-verdes definido na configuração do controlador.")
    public boolean isNumeroCorretoTabelasEntreVerdes() {
        if (this.getAnel() != null && this.getAnel().isAtivo()) {
            Set<Integer> posicoes = new HashSet<>();
            for (Transicao transicao : this.getTransicoes()) {
                for (TabelaEntreVerdesTransicao tevTransicao : transicao.getTabelaEntreVerdesTransicoes()) {
                    posicoes.add(tevTransicao.getTabelaEntreVerdes().getPosicao());
                }
            }
            return !(getTabelasEntreVerdes().size() > getAnel().getControlador().getLimiteTabelasEntreVerdes());
        }
        return true;
    }

    @JsonIgnore
    @Transient
    public boolean isPedestre() {
        return this.getTipo() != null && TipoGrupoSemaforico.PEDESTRE.equals(this.getTipo());
    }

    @JsonIgnore
    @Transient
    public boolean isVeicular() {
        return this.getTipo() != null && TipoGrupoSemaforico.VEICULAR.equals(this.getTipo());
    }

    @AssertTrue(groups = ControladorGruposSemaforicosCheck.class, message = "deve estar entre 10 e 30")
    public boolean isTempoVerdeSegurancaFieldVeicular() {
        if (this.getTempoVerdeSeguranca() != null && this.getTipo().equals(TipoGrupoSemaforico.VEICULAR)) {
            return RangeUtils.getInstance().TEMPO_VERDE_SEGURANCA_VEICULAR.contains(getTempoVerdeSeguranca());
        }
        return true;
    }

    @AssertTrue(groups = ControladorGruposSemaforicosCheck.class, message = "deve estar entre 4 e 10")
    public boolean isTempoVerdeSegurancaFieldPedestre() {
        if (this.getTempoVerdeSeguranca() != null && this.getTipo().equals(TipoGrupoSemaforico.PEDESTRE)) {
            return RangeUtils.getInstance().TEMPO_VERDE_SEGURANCA_PEDESTRE.contains(getTempoVerdeSeguranca());
        }
        return true;
    }

    public void addTabelaEntreVerdes(TabelaEntreVerdes tabelaEntreVerdes) {
        if (getTabelasEntreVerdes() == null) {
            setTabelasEntreVerdes(new ArrayList<>());
        }

        getTabelasEntreVerdes().add(tabelaEntreVerdes);
    }

    public void criarPossiveisTransicoes() {
        getTransicoes().forEach(transicao -> transicao.setDestroy(true));

        getEstagiosGruposSemaforicos().forEach(estagioGrupoSemaforico ->
                this.getAnel().getEstagios().stream()
                        .filter(estagio ->
                                !estagio.equals(estagioGrupoSemaforico.getEstagio()) && !estagioGrupoSemaforico.getEstagio().temTransicaoProibidaParaEstagio(estagio))
                        .forEach(estagio ->
                                this.addTransicao(new Transicao(this, estagioGrupoSemaforico.getEstagio(), estagio, TipoTransicao.GANHO_DE_PASSAGEM))));

        getEstagiosGruposSemaforicos().forEach(estagioGrupoSemaforico ->
                this.getAnel().getEstagios().stream()
                        .filter(estagio ->
                                !estagio.equals(estagioGrupoSemaforico.getEstagio()) && !estagio.temTransicaoProibidaParaEstagio(estagioGrupoSemaforico.getEstagio()))
                        .forEach(estagio ->
                                this.addTransicao(new Transicao(this, estagio, estagioGrupoSemaforico.getEstagio(), TipoTransicao.PERDA_DE_PASSAGEM))));

        getTransicoes().forEach(transicao -> {
            if (transicao.isDestroy()) transicao.delete();
        });
        getTransicoes().removeIf(Transicao::isDestroy);

        getTransicoes();
    }

    private void addTransicao(Transicao transicao) {
        if (getTransicoes() == null) {
            setTransicoes(new ArrayList<Transicao>());
        }
        Transicao transicaoAux = getTransicoes().stream()
                .filter(t ->
                        t.getOrigem().equals(transicao.getOrigem()) &&
                                t.getDestino().equals(transicao.getDestino()) &&
                                ((t.isGanhoDePassagem() && transicao.isGanhoDePassagem()) ||
                                        (t.isPerdaDePassagem() && transicao.isPerdaDePassagem())))
                .findFirst().orElse(null);

        if (transicaoAux != null) {
            transicaoAux.setDestroy(false);
        } else {
            if (transicao.isGanhoDePassagem()) {
                TabelaEntreVerdes tabelaEntreVerdes = this.findByTabelaEntreVerdesPadrao();
                TabelaEntreVerdesTransicao tevTransicao = new TabelaEntreVerdesTransicao(tabelaEntreVerdes, transicao);
                tabelaEntreVerdes.addTabelaEntreVerdesTransicao(tevTransicao);
                transicao.addTabelaEntreVerdesTransicao(tevTransicao);
            }
            getTransicoes().add(transicao);
        }
    }

    public List<VerdesConflitantes> getVerdesConflitantes() {
        return ListUtils.union(verdesConflitantesOrigem, verdesConflitantesDestino);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        GrupoSemaforico that = (GrupoSemaforico) o;

        return id != null ? id.equals(that.id) : that.id == null;

    }

    @Override
    public int hashCode() {
        return id != null ? id.hashCode() : 0;
    }

    @Override
    public Object clone() throws CloneNotSupportedException {
        return super.clone();
    }

    public void addVerdeConflitante(GrupoSemaforico grupoSemaforico) {
        VerdesConflitantes verdesConflitantes = new VerdesConflitantes(this, grupoSemaforico);
        addVerdesConflitantesList(verdesConflitantes);
    }

    public boolean conflitaCom(GrupoSemaforico grupoSemaforico) {
        return getVerdesConflitantes().stream().anyMatch(verdesConflitantes -> verdesConflitantes.getVerdeConflitante(this).equals(grupoSemaforico));
    }

    public boolean conflitaCom(List<GrupoSemaforico> gruposSemaforicos) {
        return getVerdesConflitantesOrigem().stream().anyMatch(verdesConflitantes -> gruposSemaforicos.contains(verdesConflitantes.getDestino()));
    }

    private void addVerdesConflitantesList(VerdesConflitantes verdesConflitantes) {
        if (getVerdesConflitantesOrigem() == null) {
            setVerdesConflitantesOrigem(new ArrayList<VerdesConflitantes>());
        }
        getVerdesConflitantesOrigem().add(verdesConflitantes);
    }

    public Transicao findTransicaoByOrigemDestino(Estagio origem, Estagio destino) {
        return getTransicoes().stream().filter(transicao -> transicao.getOrigem().equals(origem) && transicao.getDestino().equals(destino)).findFirst().orElse(null);
    }
}

