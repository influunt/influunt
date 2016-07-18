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
@AoMenosUmGrupoSemaforico
@ConformidadeNumeroEstagios
@ConformidadeNumeroDetectores
@ConformidadeNumeroDetectoresEstagios
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
        return Optional.fromNullable(quantidadeGrupoVeicular).or(0);
    }

    public void setQuantidadeGrupoVeicular(Integer quantidadeGrupoVeicular) {
        this.quantidadeGrupoVeicular = quantidadeGrupoVeicular;
    }

    public Integer getQuantidadeDetectorPedestre() {
        return Optional.fromNullable(quantidadeDetectorPedestre).or(0);
    }

    public void setQuantidadeDetectorPedestre(Integer quantidadeDetectorPedestre) {
        this.quantidadeDetectorPedestre = quantidadeDetectorPedestre;
    }

    public Integer getQuantidadeDetectorVeicular() {
        return Optional.fromNullable(quantidadeDetectorVeicular).or(0);
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
                grupoSemaforico.addTabelaEntreVerdes(criaTabelaEntreVerdes(grupoSemaforico, 1));
                getGruposSemaforicos().add(grupoSemaforico);
                this.getControlador().getGruposSemaforicos().add(grupoSemaforico);
            }
        } else {
            //TODO:O que fazer se o cara alterar????
        }
    }


    public void criaDetectores() {
        if (isAtivo()) {
            if (getDetectores().isEmpty()) {
                setDetectores(new ArrayList<Detector>());
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
            }
        } else {
            //TODO:O que fazer se o cara alterar????
        }
    }

    @JsonIgnore
    @AssertTrue(groups = ControladorAssociacaoGruposSemaforicosCheck.class,
            message = "Quantidade de grupos semáforicos de pedestre diferente do definido no anel")
    public boolean isCheckQuantidadeGruposSemaforicosDePedestre() {
        if (Objects.nonNull(this.getGruposSemaforicos())) {
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
        if (Objects.nonNull(this.getGruposSemaforicos())) {
            return this.getGruposSemaforicos().stream()
                    .filter(grupoSemaforico -> grupoSemaforico.getTipo() != null && grupoSemaforico.getTipo().equals(TipoGrupoSemaforico.VEICULAR)).count() == this.getQuantidadeGrupoVeicular();
        } else {
            return true;
        }
    }

    @AssertTrue(groups = ControladorAssociacaoGruposSemaforicosCheck.class,
            message = "Deve existir detectores cadastrados para estagio de demanda prioritaria")
    public boolean isDeveExistirDetectoresCasoExistaEstatigioDemandaPrioritaria() {
        if (!this.getEstagios().isEmpty()) {
            if (this.getEstagios().stream().filter(estagio -> estagio.getDemandaPrioritaria()).count() > 0) {
                return this.getDetectores().size() > 0;
            }
        }
        return true;
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

    private TabelaEntreVerdes criaTabelaEntreVerdes(GrupoSemaforico grupoSemaforico, int posicao) {
        TabelaEntreVerdes tabelaEntreVerdes = new TabelaEntreVerdes(grupoSemaforico, posicao);
        for (Transicao transicao : grupoSemaforico.getTransicoes()) {
            TabelaEntreVerdesTransicao tevTransicao = new TabelaEntreVerdesTransicao(tabelaEntreVerdes, transicao);
            tabelaEntreVerdes.addTabelaEntreVerdesTransicao(tevTransicao);
        }
        return tabelaEntreVerdes;
    }
}

