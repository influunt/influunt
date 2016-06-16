package models;

import checks.ControladorAneisCheck;
import checks.ControladorDadosBasicosChecks;
import checks.NumeroDeAneis;
import checks.NumeroDeGrupos;
import com.avaje.ebean.Model;
import com.avaje.ebean.annotation.CreatedTimestamp;
import com.avaje.ebean.annotation.UpdatedTimestamp;
import com.fasterxml.jackson.annotation.JsonManagedReference;
<<<<<<< ca1fd2bb7c15e4ff59efb364369f024784e60036
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
=======
import org.hibernate.validator.constraints.NotBlank;
>>>>>>> Inclusao do mecanismo de validacao
import org.joda.time.DateTime;
import play.data.validation.Constraints;
import play.data.validation.ValidationError;
import play.i18n.Messages;
import utils.InfluuntDateTimeDeserializer;
import utils.InfluuntDateTimeSerializer;

import javax.persistence.*;
import javax.validation.Valid;
import javax.validation.constraints.Max;
import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 * Entidade que representa o {@link Controlador} no sistema
 *
 * @author lesiopinheiro
 */
@Entity
@Table(name = "controladores")
@NumeroDeAneis(groups = ControladorAneisCheck.class)
@NumeroDeGrupos(groups = ControladorAneisCheck.class)
public class Controlador extends Model {

    private static final long serialVersionUID = 521560643019927963L;
    public static Finder<UUID, Controlador> find = new Finder<UUID, Controlador>(Controlador.class);

    @Id
    private UUID id;

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
    @Max(value = 256)
    private String descricao;

    @Column
    @NotNull
    private String numeroSMEE;

    @Column
    @NotNull
    private String idControlador;

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

    @Valid
    @NotNull(groups = ControladorDadosBasicosChecks.class)
    private ModeloControlador modelo;

    @ManyToOne
    @Valid
    @NotNull(groups = ControladorDadosBasicosChecks.class)
    private Area area;

    @OneToMany(mappedBy = "controlador", cascade = CascadeType.ALL)
    @JsonManagedReference
    @Valid
    @NotNull(groups = ControladorAneisCheck.class)
    private List<Anel> aneis;

    @OneToMany(mappedBy = "controlador", cascade = CascadeType.ALL)
    @JsonManagedReference
<<<<<<< ca1fd2bb7c15e4ff59efb364369f024784e60036
=======
    @Valid
    @NotNull(groups = ControladorAneisCheck.class)
>>>>>>> Inclusao do mecanismo de validacao
    private List<GrupoSemaforico> gruposSemaforicos;

//    public List<ValidationError> validate() {
//        List<ValidationError> errors = new ArrayList<ValidationError>();
//        if (this.modelo != null) {
//            ConfiguracaoControlador conf = this.modelo.getConfiguracao();
//            if (this.aneis == null || this.aneis.size() != conf.getLimiteAnel()) {
//                errors.add(new ValidationError("aneis", Messages.get("modelos.controlador.errors.numero_de_aneis_invalido", conf.getLimiteAnel())));
//            }
//            if (this.gruposSemaforicos == null || this.gruposSemaforicos.size() != conf.getLimiteGrupoSemaforico()) {
//                errors.add(new ValidationError("gruposSemaforicos",
//                        Messages.get("modelos.controlador.errors.numero_de_grupos_semaforicos_invalido", conf.getLimiteGrupoSemaforico())));
//            }
//        }
//        return errors.isEmpty()?null:errors;
//    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
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
        return idControlador;
    }

    public void setIdControlador(String idControlador) {
        this.idControlador = idControlador;
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
    }

    public List<GrupoSemaforico> getGruposSemaforicos() {
        return gruposSemaforicos;
    }

    public void setGruposSemaforicos(List<GrupoSemaforico> gruposSemaforicos) {
        this.gruposSemaforicos = gruposSemaforicos;
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
}
