package models;

import checks.*;
import com.avaje.ebean.Expr;
import com.avaje.ebean.Model;
import com.avaje.ebean.annotation.ChangeLog;
import com.avaje.ebean.annotation.CreatedTimestamp;
import com.avaje.ebean.annotation.PrivateOwned;
import com.avaje.ebean.annotation.UpdatedTimestamp;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import json.deserializers.InfluuntDateTimeDeserializer;
import json.serializers.InfluuntDateTimeSerializer;
import org.joda.time.DateTime;

import javax.persistence.*;
import javax.validation.Valid;
import javax.validation.constraints.AssertTrue;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.*;

/**
 * Entidade que representa o {@link Anel} no sistema
 *
 * @author lesiopinheiro
 */
@Entity
@Table(name = "aneis")
@ChangeLog
@AoMenosUmGrupoSemaforico(groups = ControladorGruposSemaforicosCheck.class)
@ConformidadeNumeroEstagios(groups = ControladorAneisCheck.class)
@ConformidadeNumeroDetectores(groups = ControladorAneisCheck.class)
@ConformidadeNumeroDetectoresEstagios(groups = ControladorAneisCheck.class)
public class Anel extends Model implements Cloneable, Serializable {

    private static final long serialVersionUID = -6992146830358479832L;

    public static Finder<UUID, Anel> find = new Finder<UUID, Anel>(Anel.class);

    @Id
    private UUID id;

    @Column
    private String idJson;

    @Column
    @NotNull
    private Boolean ativo = false;

    @Column
    private String descricao;

    @Column
    private Integer posicao;

    @Column
    private String numeroSMEE;

    @ManyToOne
    private Controlador controlador;

    @OneToOne
    private Imagem croqui;

    @OneToMany(mappedBy = "anel", cascade = CascadeType.ALL)
    @Valid
    private List<Detector> detectores;

    @OneToMany(mappedBy = "anel", cascade = CascadeType.ALL)
    @Valid
    @PrivateOwned
    private List<GrupoSemaforico> gruposSemaforicos;

    @OneToMany(mappedBy = "anel", cascade = CascadeType.ALL)
    @Valid
    private List<Estagio> estagios;

    @JsonIgnore
    @Transient
    private VersaoPlano versaoPlanoAtivo;

    @JsonIgnore
    @Transient
    private VersaoPlano versaoPlanoEdicao;

    @OneToMany(mappedBy = "anel", cascade = CascadeType.ALL)
    @Valid
    private List<VersaoPlano> versoesPlanos;


    @OneToOne(mappedBy = "anel", cascade = CascadeType.ALL)
    @Valid
    private Endereco endereco;

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

    @Transient
    private boolean isDestroy = false;

    public Anel(Controlador controlador, int posicao) {
        super();
        this.idJson = UUID.randomUUID().toString();
        this.controlador = controlador;
        this.posicao = posicao;
        this.versoesPlanos = new ArrayList<VersaoPlano>();
    }

    public Anel() {
        super();
        this.idJson = UUID.randomUUID().toString();
    }


    public Anel(String descricao) {
        this.descricao = descricao;
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

    public Integer getPosicao() {
        return posicao;
    }

    public void setPosicao(Integer posicao) {
        this.posicao = posicao;
    }

    public String getNumeroSMEE() {
        return numeroSMEE;
    }

    public void setNumeroSMEE(String numeroSMEE) {
        this.numeroSMEE = numeroSMEE;
    }

    public Controlador getControlador() {
        return controlador;
    }

    public void setControlador(Controlador controlador) {
        this.controlador = controlador;
    }

    public List<Detector> getDetectores() {
        return detectores;
    }

    public void setDetectores(List<Detector> detectores) {
        this.detectores = detectores;
    }

    public List<GrupoSemaforico> getGruposSemaforicos() {
        return gruposSemaforicos;
    }

    public void setGruposSemaforicos(List<GrupoSemaforico> gruposSemaforicos) {
        this.gruposSemaforicos = gruposSemaforicos;
    }

    public List<Estagio> getEstagios() {
        return estagios;
    }

    public void setEstagios(List<Estagio> estagios) {
        this.estagios = estagios;
    }

    public Boolean isAtivo() {
        return ativo;
    }

    public void setAtivo(Boolean ativo) {
        this.ativo = ativo;
    }

    public String getIdAnel() {
        return String.format("%s-%d", this.controlador.getCLC(), this.posicao);
    }


    public List<VersaoPlano> getVersoesPlanos() {
        return versoesPlanos;
    }

    public void setVersoesPlanos(List<VersaoPlano> versoesPlanos) {
        this.versoesPlanos = versoesPlanos;
    }


    @Transient
    public VersaoPlano getVersaoPlanoAtivo() {
        if (versaoPlanoAtivo == null) {
            if(getVersoesPlanos().isEmpty() || getVersoesPlanos() == null) {
                VersaoPlano versaoPlano = VersaoPlano.find.fetch("planos").where()
                        .and(Expr.eq("anel_id", this.id.toString()), Expr.eq("status_versao", StatusVersao.ATIVO)).findUnique();
                this.versaoPlanoAtivo = versaoPlano;
            } else {
                this.versaoPlanoAtivo = getVersoesPlanos().stream().filter(versaoPlano -> versaoPlano.isAtivo()).findFirst().orElse(null);
            }
        }
        return versaoPlanoAtivo;
    }

    @Transient
    public VersaoPlano getVersaoPlanoEmEdicao() {
        if (versaoPlanoEdicao == null) {
            if(getVersoesPlanos().isEmpty() || getVersoesPlanos() == null) {
                VersaoPlano versaoPlano = VersaoPlano.find.fetch("planos").where()
                        .and(Expr.eq("anel_id", this.id.toString()), Expr.eq("status_versao", StatusVersao.EDITANDO)).findUnique();
                this.versaoPlanoEdicao = versaoPlano;
            } else {
                this.versaoPlanoEdicao = getVersoesPlanos().stream().filter(versaoPlano -> versaoPlano.isEditando()).findFirst().orElse(null);
            }
        }
        return versaoPlanoEdicao;
    }


    @Transient
    public List<Plano> getPlanos() {
        if(getVersaoPlanoEmEdicao() != null) {
            return getVersaoPlanoEmEdicao().getPlanos();
        }
        return getVersaoPlanoAtivo() != null ? getVersaoPlanoAtivo().getPlanos() : Arrays.asList();
    }

    @JsonIgnore
    @AssertTrue(message = "Posição deve ser informada")
    public boolean isPosicaoOk() {
        return !this.ativo || this.posicao != null;
    }

    @JsonIgnore
    @AssertTrue(message = "Anel deve ter endereço")
    public boolean isEnderecosOk() {
        return !isAtivo() || getEndereco() != null;
    }

    @AssertTrue(groups = PlanosCheck.class,
            message = "O anel ativo deve ter pelo menos 1 plano configurado.")
    public boolean isAoMenosUmPlanoConfigurado() {
        if (this.isAtivo()) {
            return !this.getPlanos().isEmpty();
        }
        return true;
    }


    public GrupoSemaforico findGrupoSemaforicoByDescricao(String descricao) {
        if (Objects.nonNull(descricao)) {
            return getGruposSemaforicos().stream().filter(grupoSemaforico -> descricao.equals(grupoSemaforico.getDescricao())).findFirst().orElse(null);
        }
        return null;
    }

    public GrupoSemaforico findGrupoSemaforicoByPosicao(Integer posicao) {
        if (Objects.nonNull(posicao)) {
            return getGruposSemaforicos().stream().filter(grupoSemaforico -> posicao.equals(grupoSemaforico.getPosicao())).findFirst().orElse(null);
        }
        return null;
    }

    public Estagio findEstagioByDescricao(String descricao) {
        if (Objects.nonNull(descricao)) {
            return getEstagios().stream().filter(estagio -> descricao.equals(estagio.getDescricao())).findFirst().orElse(null);
        }
        return null;
    }

    public Detector findDetectorByDescricao(String descricao) {
        if (Objects.nonNull(descricao)) {
            return getDetectores().stream().filter(detector -> descricao.equals(detector.getDescricao())).findFirst().orElse(null);
        }
        return null;
    }

    public boolean temDetectorVeicular() {
        return getDetectores().stream().filter(detector -> detector.isVeicular()).count() > 0;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Anel anel = (Anel) o;

        return id != null ? id.equals(anel.id) : anel.id == null;

    }

    @Override
    public int hashCode() {
        return id != null ? id.hashCode() : 0;
    }

    @Override
    public Object clone() throws CloneNotSupportedException {
        getDataCriacao();
        return super.clone();
    }

    public void addGruposSemaforicos(GrupoSemaforico grupoSemaforico) {
        if (getGruposSemaforicos() == null) {
            setGruposSemaforicos(new ArrayList<GrupoSemaforico>());
        }
        getGruposSemaforicos().add(grupoSemaforico);
    }

    public void addDetectores(Detector detector) {
        if (getDetectores() == null) {
            setDetectores(new ArrayList<Detector>());
        }
        getDetectores().add(detector);
    }

    public String getCLA() {
        if (this.controlador != null) {
            return String.format("%s.%01d", this.controlador.getCLC(), this.posicao);
        } else {
            return String.format("%s.%01d", "sem-controlador", this.posicao);
        }
    }

    public Imagem getCroqui() {
        return croqui;
    }

    public void setCroqui(Imagem croqui) {
        this.croqui = croqui;
    }

    public Endereco getEndereco() {
        return endereco;
    }

    public void setEndereco(Endereco endereco) {
        this.endereco = endereco;
    }

    public void addEstagio(Estagio estagio) {
        if (getEstagios() == null) {
            setEstagios(new ArrayList<Estagio>());
        }
        getEstagios().add(estagio);
    }

    public void addPlano(Plano plano) {
        getVersaoPlanoAtivo().addPlano(plano);
    }

    public List<Estagio> ordenarEstagiosPorPosicao() {
        List<Estagio> listaEstagio = this.getEstagios();
        listaEstagio.sort((anterior, proximo) -> anterior.getPosicao().compareTo(proximo.getPosicao()));
        return listaEstagio;
    }


    public boolean isDestroy() {
        return isDestroy;
    }

    public void setDestroy(boolean destroy) {
        isDestroy = destroy;
    }

    public void addVersaoPlano(VersaoPlano versaoPlano) {
        if (getVersoesPlanos() == null) {
            setVersoesPlanos(new ArrayList<VersaoPlano>());
        }

        getVersoesPlanos().add(versaoPlano);
    }

    @Transient
    public VersaoPlano getVersaoPlano() {
        if (getVersaoPlanoEmEdicao() != null) {
            return getVersaoPlanoEmEdicao();
        } else if (getVersaoPlanoAtivo() != null) {
            return getVersaoPlanoAtivo();
        }
        return null;
    }
}

