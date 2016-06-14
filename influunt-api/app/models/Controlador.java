package models;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

import framework.BaseEntity;
import play.data.validation.Constraints;
import play.data.validation.ValidationError;

/**
 * Entidade que representa o {@link Controlador} no sistema
 * 
 * @author lesiopinheiro
 *
 */
@Entity
@Table(name = "controladores")
public class Controlador extends BaseEntity<String> {

    private static final long serialVersionUID = 521560643019927963L;

    @Id
    @GeneratedValue(generator = "uuid2")
    @GenericGenerator(name = "uuid2", strategy = "uuid2")
    @Column(name = "id", unique = true)
    private String id;

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

    @OneToOne
    @JoinColumn(name = "coordenada_id")
    @Constraints.Required
    private CoordenadaGeografica coordenada;

    @Constraints.Required
    private ModeloControlador modelo;

    @OneToOne
    @JoinColumn(name = "area_id")
    @Constraints.Required
    private Area area;

    @OneToMany(mappedBy = "controlador", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Anel> aneis;

    @OneToMany(mappedBy = "controlador", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<GrupoSemaforico> gruposSemaforicos;

    @Column
    private Date dataCriacao;

    @Column
    private Date dataAtualizacao;

    public List<ValidationError> validate() {
	List<ValidationError> errors = new ArrayList<ValidationError>();
	if(this.modelo != null){
	    ConfiguracaoControlador conf = this.modelo.getConfiguracao();
	    if(this.aneis == null){
		errors.add(new ValidationError("aneis", "Nenhum anel foi preenchido"));
	    }else{
		if(this.aneis.size()  < 1){
		    errors.add(new ValidationError("aneis", "No mínimo 1 anel precisa ser ativado"));    
		}else if(this.aneis.size() > conf.getLimiteAnel()){
		    errors.add(new ValidationError("aneis", "Este modelo de controlador permite no máximo " + conf.getLimiteAnel() + " anéis"));
		}
	    }
	    if(this.gruposSemaforicos == null){
		errors.add(new ValidationError("gruposSemaforicos", "Nenhum grupo semáforico foi preenchido"));
	    }else{
		if(this.gruposSemaforicos.size()  < 1){
		    errors.add(new ValidationError("gruposSemaforicos", "No mínimo 1 grupo semáforico precisa ser preenchido"));    
		}else if(this.gruposSemaforicos.size() > conf.getLimiteGrupoSemaforico()){
		    errors.add(new ValidationError("aneis", "Este modelo de controlador permite no máximo " + conf.getLimiteGrupoSemaforico() + " grupos semafóricos"));
		}
	    }


	}
	return errors.isEmpty() ? null : errors;
    }

    public String getId() {
	return id;
    }

    public void setId(String id) {
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

    public Date getDataCriacao() {
	return dataCriacao;
    }

    public void setDataCriacao(Date dataCriacao) {
	this.dataCriacao = dataCriacao;
    }

    public Date getDataAtualizacao() {
	return dataAtualizacao;
    }

    public void setDataAtualizacao(Date dataAtualizacao) {
	this.dataAtualizacao = dataAtualizacao;
    }

    public static long getSerialversionuid() {
	return serialVersionUID;
    }


}
