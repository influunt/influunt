package models;

import com.avaje.ebean.Model;
import org.joda.time.DateTime;
import play.data.validation.Constraints;
import play.data.validation.ValidationError;
import play.i18n.Messages;

import javax.persistence.*;
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
public class Controlador extends Model {

    private static final long serialVersionUID = 521560643019927963L;
    public static Finder<UUID, Controlador> find = new Finder<UUID, Controlador>(Controlador.class);

    @Id
    private UUID id;

    @Column
    private DateTime dataCriacao;
    @Column
    private DateTime dataAtualizacao;

    @Column
    @Constraints.Required
    @Constraints.MaxLength(value = 256)
    private String descricao;

    @Column
    @Constraints.Required
    private String numeroSMEE;

    @Column
    @Constraints.Required
    private String idControlador;

    @Column
    private String numeroSMEEConjugado1;

    @Column
    private String numeroSMEEConjugado2;

    @Column
    private String numeroSMEEConjugado3;

    @Column
    @Constraints.Required
    private String firmware;

    @OneToOne(cascade =  CascadeType.ALL)
    @JoinColumn(name = "coordenada_id")
    @Constraints.Required
    private CoordenadaGeografica coordenada;

    @Constraints.Required
    private ModeloControlador modelo;

    @OneToOne
    @JoinColumn(name = "area_id")
    @Constraints.Required
    private Area area;

    @OneToMany(mappedBy = "controlador", cascade = CascadeType.ALL)
    private List<Anel> aneis;

    @OneToMany(mappedBy = "controlador", cascade = CascadeType.ALL)
    private List<GrupoSemaforico> gruposSemaforicos;

    public List<ValidationError> validate() {
        List<ValidationError> errors = new ArrayList<ValidationError>();
        if (this.modelo != null) {
            ConfiguracaoControlador conf = this.modelo.getConfiguracao();
            if (this.aneis == null || this.aneis.size() != conf.getLimiteAnel()) {
                errors.add(new ValidationError("aneis", Messages.get("modelos.controlador.errors.numero_de_aneis_invalido", conf.getLimiteAnel())));
            }
            if (this.gruposSemaforicos == null || this.gruposSemaforicos.size() != conf.getLimiteGrupoSemaforico()) {
                errors.add(new ValidationError("gruposSemaforicos",
                        Messages.get("modelos.controlador.errors.numero_de_grupos_semaforicos_invalido", conf.getLimiteGrupoSemaforico())));
            }
        }
        return errors.isEmpty()?null:errors;
    }

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

    public CoordenadaGeografica getCoordenada() {
        return coordenada;
    }

    public void setCoordenada(CoordenadaGeografica coordenada) {
        this.coordenada = coordenada;
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
}
