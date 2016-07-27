package models;

import checks.*;
import com.avaje.ebean.Model;
import com.avaje.ebean.annotation.CreatedTimestamp;
import com.avaje.ebean.annotation.UpdatedTimestamp;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.google.common.base.Optional;
import json.deserializers.AnelDeserializer;
import json.deserializers.InfluuntDateTimeDeserializer;
import json.serializers.AnelSerializer;
import json.serializers.InfluuntDateTimeSerializer;
import org.joda.time.DateTime;

import javax.persistence.*;
import javax.validation.Valid;
import javax.validation.constraints.AssertTrue;
import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.UUID;

/**
 * Entidade que representa o {@link Anel} no sistema
 *
 * @author lesiopinheiro
 * @author lesiopinheiro
 */
@Entity
@Table(name = "aneis")
@AoMenosUmGrupoSemaforico(groups = ControladorGruposSemaforicosCheck.class)
@ConformidadeNumeroEstagios(groups = ControladorAneisCheck.class)
@ConformidadeNumeroDetectores(groups = ControladorAneisCheck.class)
@ConformidadeNumeroDetectoresEstagios(groups = ControladorAneisCheck.class)
@JsonSerialize(using = AnelSerializer.class)
@JsonDeserialize(using = AnelDeserializer.class)
public class Anel extends Model implements Cloneable {

    private static final long serialVersionUID = 4919501406230732757L;

    public static Finder<UUID, Anel> find = new Finder<UUID, Anel>(Anel.class);

    @Id
    private UUID id;

    @Column
    @NotNull
    private Boolean ativo = false;

    @Column
    private String descricao;


    @Column
    private Integer posicao;

    @Column
    private String numeroSMEE;

    @Column
    private Double latitude;

    @Column
    private Double longitude;

    @ManyToOne
    private Controlador controlador;

    @OneToMany(mappedBy = "anel", cascade = CascadeType.ALL)
    @Valid
    private List<Detector> detectores;

    @OneToMany(mappedBy = "anel", cascade = CascadeType.ALL)
    @Valid
    private List<GrupoSemaforico> gruposSemaforicos;

    @OneToMany(mappedBy = "anel", cascade = CascadeType.ALL)
    @Valid
    private List<Estagio> estagios;

    @OneToMany(mappedBy = "anel", cascade = CascadeType.ALL)
    @Valid
    private List<Plano> planos;

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


    public Anel(Controlador controlador, int posicao) {
        super();
        this.controlador = controlador;
        this.posicao = posicao;
    }

    public Anel() {
        super();
    }

    public Anel(String descricao) {
        this.descricao = descricao;
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

    public Double getLatitude() {
        return latitude;
    }

    public void setLatitude(Double latitude) {
        this.latitude = latitude;
    }

    public Double getLongitude() {
        return longitude;
    }

    public void setLongitude(Double longitude) {
        this.longitude = longitude;
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

    public List<Plano> getPlanos() {
        return planos;
    }

    public void setPlanos(List<Plano> planos) {
        this.planos = planos;
    }

    @JsonIgnore
    @AssertTrue(message = "Posição deve ser informada")
    public boolean isPosicaoOk() {
        return !this.ativo || this.posicao != null;
    }

    @JsonIgnore
    @AssertTrue(message = "Latitude deve ser informada")
    public boolean isLatitudeOk() {
        return !this.ativo || this.latitude != null;
    }

    @JsonIgnore
    @AssertTrue(message = "Longitude deve ser informada")
    public boolean isLongitudeOk() {
        return !this.ativo || this.longitude != null;
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
}

