package models;

import com.avaje.ebean.Model;
import com.fasterxml.jackson.annotation.JsonBackReference;
import org.joda.time.DateTime;
import play.data.validation.Constraints;

import javax.persistence.*;
import java.util.List;
import java.util.UUID;

/**
 * Entidade que representa o {@link Anel} no sistema
 *
 * @author lesiopinheiro
 *
 */
@Entity
@Table(name = "aneis")
public class Anel extends Model {

    private static final long serialVersionUID = 4919501406230732757L;

    @Id
    private UUID id;

    @Constraints.Required
    @Column
    private String descricao;

    @Constraints.Required
    @Column
    private String idAnel;

    @Constraints.Required
    @Column
    private String numeroSMEE;

    @Constraints.Required
    @Column
    private Double latitude;

    @Constraints.Required
    @Column
    private Double longitude;

    @ManyToOne
    @JsonBackReference
    private Controlador controlador;

    @OneToMany(mappedBy = "anel", cascade = CascadeType.ALL)
    private List<Detector> detectores;

    @OneToMany(mappedBy = "anel", cascade = CascadeType.ALL)
    private List<GrupoSemaforico> gruposSemaforicos;

    @OneToMany(mappedBy = "anel", cascade = CascadeType.ALL)
    private List<Movimento> movimentos;

    @Column
    private DateTime dataCriacao;

    @Column
    private DateTime dataAtualizacao;

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

    public String getIdAnel() {
        return idAnel;
    }

    public void setIdAnel(String idAnel) {
        this.idAnel = idAnel;
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

    public List<Movimento> getMovimentos() {
        return movimentos;
    }

    public void setMovimentos(List<Movimento> movimentos) {
        this.movimentos = movimentos;
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
}

