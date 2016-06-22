package models;

import checks.AoMenosUmGrupoSemaforico;
import checks.ConformidadeNumeroEstagios;
import checks.ControladorAssociacaoGruposSemaforicosCheck;
import com.avaje.ebean.Model;
import com.avaje.ebean.annotation.CreatedTimestamp;
import com.avaje.ebean.annotation.UpdatedTimestamp;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
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
import java.util.UUID;

/**
 * Entidade que representa o {@link Anel} no sistema
 *
 * @author lesiopinheiro
 * @author lesiopinheiro
 */
@Entity
@Table(name = "aneis")
@AoMenosUmGrupoSemaforico
@ConformidadeNumeroEstagios
@JsonSerialize(using = AnelSerializer.class)
@JsonDeserialize(using = AnelDeserializer.class)
public class Anel extends Model {

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

    @Column
    private Integer quantidadeGrupoPedestre = 0;

    @Column
    private Integer quantidadeGrupoVeicular = 0;

    @Column
    private Integer quantidadeDetectorPedestre = 0;

    @Column
    private Integer quantidadeDetectorVeicular = 0;

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

    public Integer getQuantidadeGrupoPedestre() {
        if (quantidadeGrupoPedestre == null) {
            return 0;
        }
        return quantidadeGrupoPedestre;
    }

    public void setQuantidadeGrupoPedestre(Integer quantidadeGrupoPedestre) {
        this.quantidadeGrupoPedestre = quantidadeGrupoPedestre;
    }

    public Integer getQuantidadeGrupoVeicular() {
        if (quantidadeGrupoVeicular == null) {
            return 0;
        }
        return quantidadeGrupoVeicular;
    }

    public void setQuantidadeGrupoVeicular(Integer quantidadeGrupoVeicular) {
        this.quantidadeGrupoVeicular = quantidadeGrupoVeicular;
    }

    public Integer getQuantidadeDetectorPedestre() {
        if (quantidadeDetectorPedestre == null) {
            return 0;
        }
        return quantidadeDetectorPedestre;
    }

    public void setQuantidadeDetectorPedestre(Integer quantidadeDetectorPedestre) {
        this.quantidadeDetectorPedestre = quantidadeDetectorPedestre;
    }

    public Integer getQuantidadeDetectorVeicular() {
        if (quantidadeDetectorVeicular == null) {
            return 0;
        }
        return quantidadeDetectorVeicular;
    }

    public void setQuantidadeDetectorVeicular(Integer quantidadeDetectorVeicular) {
        this.quantidadeDetectorVeicular = quantidadeDetectorVeicular;
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


    public int getQuantidadeGrupoSemaforico() {
        return getQuantidadeGrupoPedestre() + getQuantidadeGrupoVeicular();
    }


    public void criaGruposSemaforicos() {
        if (isAtivo()) {
            if (getGruposSemaforicos() == null) {
                setGruposSemaforicos(new ArrayList<GrupoSemaforico>(this.getQuantidadeGrupoSemaforico()));
            }
            for (int i = this.getGruposSemaforicos().size(); i < this.getQuantidadeGrupoSemaforico(); i++) {
                GrupoSemaforico grupoSemaforico = new GrupoSemaforico();
                grupoSemaforico.setAnel(this);
                grupoSemaforico.setPosicao(this.getControlador().getGruposSemaforicos().size() + 1);
                grupoSemaforico.setControlador(this.getControlador());
//                grupoSemaforico.save();
                getGruposSemaforicos().add(grupoSemaforico);
                this.getControlador().getGruposSemaforicos().add(grupoSemaforico);
            }
        } else {
            //TODO:O que fazer se o cara alterar????
        }
    }


    public void criaDetectores() {
        if (this.id == null && isAtivo()) {
            this.detectores = new ArrayList<Detector>(this.getQuantidadeDetectorPedestre() + this.getQuantidadeDetectorVeicular());

            for (int i = this.getQuantidadeDetectorPedestre(); i > 0; i--) {
                Detector detector = new Detector();
                detector.setTipo(TipoDetector.PEDESTRE);
                detector.setAnel(this);
                detector.setControlador(this.getControlador());
                this.detectores.add(detector);
            }
            for (int i = this.getQuantidadeDetectorVeicular(); i > 0; i--) {
                Detector detector = new Detector();
                detector.setTipo(TipoDetector.VEICULAR);
                detector.setAnel(this);
                detector.setControlador(this.getControlador());
                this.detectores.add(detector);
            }
        } else {
            //TODO:O que fazer se o cara alterar????
        }
    }

    @JsonIgnore
    @AssertTrue(groups = ControladorAssociacaoGruposSemaforicosCheck.class,
                message = "Quantidade de grupos semáforicos de pedestre diferente do definido no anel")
    public boolean isCheckQuantidadeGruposSemaforicosDePedestre() {
        if (this.getGruposSemaforicos() != null) {
            return this.getGruposSemaforicos().stream()
                    .filter(grupoSemaforico -> grupoSemaforico.getTipo() != null && grupoSemaforico.getTipo().equals(TipoGrupoSemaforico.PEDESTRE)).count() == this.getQuantidadeGrupoPedestre();
        } else {
            return true;
        }
    }

    @JsonIgnore
    @AssertTrue(groups = ControladorAssociacaoGruposSemaforicosCheck.class,
                message = "Quantidade de grupos semáforicos veiculares diferente do definido no anel")
    public boolean isCheckQuantidadeGruposSemaforicosVeiculares() {
        if (this.getGruposSemaforicos() != null) {
            return this.getGruposSemaforicos().stream()
                    .filter(grupoSemaforico -> grupoSemaforico.getTipo() != null && grupoSemaforico.getTipo().equals(TipoGrupoSemaforico.VEICULAR)).count() == this.getQuantidadeGrupoVeicular();
        } else {
            return true;
        }
    }


}

