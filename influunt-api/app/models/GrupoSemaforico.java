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
import json.deserializers.GrupoSemaforicoDeserializer;
import json.deserializers.InfluuntDateTimeDeserializer;
import json.serializers.GrupoSemaforicoSerializer;
import json.serializers.InfluuntDateTimeSerializer;
import org.apache.commons.collections.ListUtils;
import org.joda.time.DateTime;

import javax.persistence.*;
import javax.validation.Valid;
import javax.validation.constraints.AssertTrue;
import javax.validation.constraints.NotNull;
import java.util.*;

/**
 * Entidade que representa o {@link GrupoSemaforico} no sistema
 *
 * @author lesiopinheiro
 */
@Entity
@Table(name = "grupos_semaforicos")
@JsonSerialize(using = GrupoSemaforicoSerializer.class)
@JsonDeserialize(using = GrupoSemaforicoDeserializer.class)
public class GrupoSemaforico extends Model implements Cloneable {
    private static final long serialVersionUID = 7439393568357903233L;
    public static Finder<UUID, GrupoSemaforico> find = new Finder<UUID, GrupoSemaforico>(GrupoSemaforico.class);
    @Id
    private UUID id;

    @Column
    private String idJson;

    public String getIdJson() {
        return idJson;
    }

    public void setIdJson(String idJson) {
        this.idJson = idJson;
    }

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
    private List<EstagioGrupoSemaforico> estagioGrupoSemaforicos;

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

    public List<EstagioGrupoSemaforico> getEstagioGrupoSemaforicos() {
        return estagioGrupoSemaforicos;
    }

    public void setEstagioGrupoSemaforicos(List<EstagioGrupoSemaforico> estagioGrupoSemaforicos) {
        this.estagioGrupoSemaforicos = estagioGrupoSemaforicos;
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
        if (getEstagioGrupoSemaforicos() == null) {
            setEstagioGrupoSemaforicos(new ArrayList<EstagioGrupoSemaforico>());
        }
        getEstagioGrupoSemaforicos().add(estagioGrupoSemaforico);
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
            int totalTabelasEntreVerdes = posicoes.size();
            @SuppressWarnings("ConstantConditions")
            int limiteTabelasEntreVerdes = Anel.find.byId(this.getAnel().getId()).getControlador().getModelo().getConfiguracao().getLimiteTabelasEntreVerdes();
            return totalTabelasEntreVerdes <= limiteTabelasEntreVerdes;
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

    public void addTabelaEntreVerdes(TabelaEntreVerdes tabelaEntreVerdes) {
        if (getTabelasEntreVerdes() == null) {
            setTabelasEntreVerdes(new ArrayList<>());
        }

        getTabelasEntreVerdes().add(tabelaEntreVerdes);
    }

    public void criarPossiveisTransicoes() {
        getTransicoes().forEach(transicao -> transicao.setDestroy(true));

        getEstagioGrupoSemaforicos().forEach(estagioGrupoSemaforico -> getAnel().getEstagios().stream()
                .filter(estagio -> !estagio.equals(estagioGrupoSemaforico.getEstagio()) && !estagioGrupoSemaforico.getEstagio().temTransicaoProibidaComEstagio(estagio))
                .forEach(estagio -> this.addTransicoes(new Transicao(this, estagioGrupoSemaforico.getEstagio(), estagio))));


        getTransicoes().removeIf(Transicao::isDestroy);

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

    private void addTransicoes(Transicao transicao) {
        if (getTransicoes() == null) {
            setTransicoes(new ArrayList<Transicao>());
        }
        Transicao transicaoAux = getTransicoes().stream().filter(t -> t.getOrigem().equals(transicao.getOrigem()) && t.getDestino().equals(transicao.getDestino())).findFirst().orElse(null);
        if (transicaoAux != null) {
            transicaoAux.setDestroy(false);
        } else {
            TabelaEntreVerdes tabelaEntreVerdes = this.findByTabelaEntreVerdesPadrao();
            TabelaEntreVerdesTransicao tevTransicao = new TabelaEntreVerdesTransicao(tabelaEntreVerdes, transicao);
            tabelaEntreVerdes.addTabelaEntreVerdesTransicao(tevTransicao);
            transicao.addTabelaEntreVerdesTransicao(tevTransicao);
            getTransicoes().add(transicao);
        }
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
