package models;

import checks.*;
import com.avaje.ebean.Model;
import com.avaje.ebean.annotation.CreatedTimestamp;
import com.avaje.ebean.annotation.UpdatedTimestamp;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import org.hibernate.validator.constraints.NotBlank;
import org.joda.time.DateTime;
import utils.InfluuntDateTimeDeserializer;
import utils.InfluuntDateTimeSerializer;

import javax.persistence.*;
import javax.validation.Valid;
import javax.validation.constraints.AssertTrue;
import javax.validation.constraints.NotNull;
import java.util.List;
import java.util.UUID;

/**
 * Entidade que representa o {@link Controlador} no sistema
 *
 * @author lesiopinheiro
 */
@Entity
@Table(name = "controladores")
@NumeroDeAneisIgualAoModelo(groups = ControladorAneisCheck.class)
@ConformidadeDeNumeroDeGruposSemaforicos(groups = ControladorAneisCheck.class)
@ConformidadeDeNumeroDeDetectoresDePedestre(groups = ControladorAneisCheck.class)
@ConformidadeDeNumeroDeDetectoresVeicular(groups = ControladorAneisCheck.class)
@AoMenosUmAnelAtivo(groups = ControladorAneisCheck.class)
public class Controlador extends Model {

    private static final long serialVersionUID = 521560643019927963L;
    public static Finder<Long, Controlador> find = new Finder<Long, Controlador>(Controlador.class);

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column
    @JsonDeserialize(using= InfluuntDateTimeDeserializer.class)
    @JsonSerialize(using= InfluuntDateTimeSerializer.class)
    @CreatedTimestamp
    private DateTime dataCriacao;

    @Column
    @JsonDeserialize(using= InfluuntDateTimeDeserializer.class)
    @JsonSerialize(using= InfluuntDateTimeSerializer.class)
    @UpdatedTimestamp
    private DateTime dataAtualizacao;

    @Column
    @NotBlank
    private String descricao;

    @Column
    @NotNull
    private String numeroSMEE;

    @Column
    private String numeroSMEEConjugado1;

    @Column
    private String numeroSMEEConjugado2;

    @Column
    private String numeroSMEEConjugado3;

    @Column
    @NotNull
    private String firmware;

    @Column
    @NotNull
    private Double latitude;

    @Column
    @NotNull
    private Double longitude;

    @ManyToOne
    @Valid
    @NotNull
    private ModeloControlador modelo;

    @ManyToOne
    @Valid
    @NotNull
    private Area area;

    @OneToMany(mappedBy = "controlador", cascade = CascadeType.ALL)
    @JsonManagedReference
    @Valid
    private List<Anel> aneis;

    @OneToMany(mappedBy = "controlador", cascade = CascadeType.ALL)
    @JsonManagedReference
    @Valid
    private List<GrupoSemaforico> gruposSemaforicos;

    @OneToMany(mappedBy = "controlador", cascade = CascadeType.ALL)
    @JsonManagedReference
    @Valid
    private List<Detector> detectores;

    @OneToMany(mappedBy = "controlador", cascade = CascadeType.ALL)
    @JsonManagedReference
    @Valid
    private List<Movimento> movimentos;


    @Override
    public void save(){
        if(getAneis() != null){
            getAneis().stream().forEach(anel -> {
                anel.criaGruposSemaforicos();
                anel.criaDetectores();
                if(anel.getMovimentos()!=null){
                    anel.getMovimentos().stream().forEach(movimento -> movimento.criarEstagio());
                }
            });
        }


        super.save();
    }


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public String getNumeroSMEE() {
        return numeroSMEE;
    }

    public void setNumeroSMEE(String numeroSMEE) {
        this.numeroSMEE = numeroSMEE;
    }

    public String getIdControlador() {
        if(this.id != null && this.area != null){
           return String.format("%01d.%03d.%04d", this.area.getDescricao(), 0, this.id);
        }
        return "";
    }


    public String getNumeroSMEEConjugado1() {
        return numeroSMEEConjugado1;
    }

    public void setNumeroSMEEConjugado1(String numeroSMEEConjugado1) {
        this.numeroSMEEConjugado1 = numeroSMEEConjugado1;
    }

    public String getNumeroSMEEConjugado2() {
        return numeroSMEEConjugado2;
    }

    public void setNumeroSMEEConjugado2(String numeroSMEEConjugado2) {
        this.numeroSMEEConjugado2 = numeroSMEEConjugado2;
    }

    public String getNumeroSMEEConjugado3() {
        return numeroSMEEConjugado3;
    }

    public void setNumeroSMEEConjugado3(String numeroSMEEConjugado3) {
        this.numeroSMEEConjugado3 = numeroSMEEConjugado3;
    }

    public String getFirmware() {
        return firmware;
    }

    public void setFirmware(String firmware) {
        this.firmware = firmware;
    }

    public ModeloControlador getModelo() {
        return modelo;
    }

    public void setModelo(ModeloControlador modelo) {
        this.modelo = modelo;
    }

    public Area getArea() {
        return area;
    }

    public void setArea(Area area) {
        this.area = area;
    }

    public List<Anel> getAneis() {
        return aneis;
    }

    public void setAneis(List<Anel> aneis) {
        this.aneis = aneis;
        this.aneis.stream().forEach(anel -> anel.setControlador(this));
    }

    public List<GrupoSemaforico> getGruposSemaforicos() {
        return gruposSemaforicos;
    }

    public void setGruposSemaforicos(List<GrupoSemaforico> gruposSemaforicos) {
        this.gruposSemaforicos = gruposSemaforicos;
    }

    public List<Detector> getDetectores() {
        return detectores;
    }

    public void setDetectores(List<Detector> detectores) {
        this.detectores = detectores;
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

    public List<Movimento> getMovimentos() {
        return movimentos;
    }

    public void setMovimentos(List<Movimento> movimentos) {
        this.movimentos = movimentos;
    }
}
