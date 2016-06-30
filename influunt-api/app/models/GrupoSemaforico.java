package models;

import checks.ControladorVerdesConflitantesCheck;
import com.avaje.ebean.Model;
import com.avaje.ebean.annotation.CreatedTimestamp;
import com.avaje.ebean.annotation.UpdatedTimestamp;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import json.deserializers.GrupoSemaforicoDeserializer;
import json.deserializers.InfluuntDateTimeDeserializer;
import json.serializers.GrupoSemaforicoSerializer;
import json.serializers.InfluuntDateTimeSerializer;
import org.joda.time.DateTime;

import javax.persistence.*;
import javax.validation.constraints.AssertTrue;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 * Entidade que representa o {@link GrupoSemaforico} no sistema
 *
 * @author lesiopinheiro
 */
@Entity
@Table(name = "grupos_semaforicos")
@JsonSerialize(using = GrupoSemaforicoSerializer.class)
@JsonDeserialize(using = GrupoSemaforicoDeserializer.class)
public class GrupoSemaforico extends Model {
    public static Finder<UUID, GrupoSemaforico> find = new Finder<UUID, GrupoSemaforico>(GrupoSemaforico.class);
    private static final long serialVersionUID = 7439393568357903233L;

    @Id
    private UUID id;

    @Enumerated(EnumType.STRING)
    @Column
    private TipoGrupoSemaforico tipo;

    @Column
    private String descricao;

    @ManyToOne
    @JoinColumn(name = "anel_id")
    private Anel anel;

    @OneToMany(mappedBy = "grupoSemaforico", fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    private List<EstagioGrupoSemaforico> estagioGrupoSemaforicos;

    @ManyToOne
    private Controlador controlador;

    @ManyToOne(cascade = CascadeType.ALL)
    private GrupoSemaforico grupoConflito;

    @OneToMany(mappedBy = "grupoConflito", cascade = CascadeType.ALL)
    private List<GrupoSemaforico> verdesConflitantes;

    @OneToMany
    private List<TabelaEntreVerdes> tabelasEntreVerdes;


    @Column
    private Integer posicao;

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

    public GrupoSemaforico getGrupoConflito() {
        return grupoConflito;
    }

    public void setGrupoConflito(GrupoSemaforico grupoConflito) {
        this.grupoConflito = grupoConflito;
    }

    public List<GrupoSemaforico> getVerdesConflitantes() {
        return verdesConflitantes;
    }

    public void setVerdesConflitantes(List<GrupoSemaforico> verdesConflitantes) {
        this.verdesConflitantes = verdesConflitantes;
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

    public String getDescricao() { return descricao; }

    public void setDescricao(String descricao) { this.descricao = descricao; }

    public void addEstagioGrupoSemaforico(EstagioGrupoSemaforico estagioGrupoSemaforico) {
        if (getEstagioGrupoSemaforicos() == null) {
            setEstagioGrupoSemaforicos(new ArrayList<EstagioGrupoSemaforico>());
        }
        getEstagioGrupoSemaforicos().add(estagioGrupoSemaforico);
    }

    @JsonIgnore
    @AssertTrue(groups = ControladorVerdesConflitantesCheck.class, message = "Esse grupo semafórico deve ter ao menos um verde conflitante")
    public boolean isAoMenosUmVerdeConflitante() {
        if (this.getAnel() != null && this.getAnel().isAtivo() && this.getEstagioGrupoSemaforicos() != null && !this.getEstagioGrupoSemaforicos().isEmpty()) {
            return this.getVerdesConflitantes() != null && !this.getVerdesConflitantes().isEmpty();
        } else {
            return true;
        }
    }

    @JsonIgnore
    @AssertTrue(groups = ControladorVerdesConflitantesCheck.class, message = "Esse grupo semafórico não pode ter verde conflitante com ele mesmo")
    public boolean isNaoConflitaComEleMesmo() {
        if (this.getVerdesConflitantes() != null && !this.getVerdesConflitantes().isEmpty()) {
            return this.getVerdesConflitantes().stream().filter(grupoSemaforico -> grupoSemaforico.getId().equals(this.getId())).count() == 0;
        } else {
            return true;
        }
    }

    @JsonIgnore
    @AssertTrue(groups = ControladorVerdesConflitantesCheck.class, message = "Esse grupo semafórico não pode ter verde conflitante com grupo semafórico de outro anel")
    public boolean isNaoConflitaComGruposDeOutroAnel() {
        if (this.getVerdesConflitantes() != null && !this.getVerdesConflitantes().isEmpty()) {
            return this.getVerdesConflitantes().stream().filter(grupoSemaforico -> !grupoSemaforico.getAnel().getId().equals(this.getAnel().getId())).count() == 0;
        } else {
            return true;
        }
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
}
