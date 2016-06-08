package models;

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

import org.hibernate.annotations.GenericGenerator;

import framework.BaseEntity;

/**
 * Entidade que representa o {@link Controlador} no sistema
 * @author lesiopinheiro
 *
 */
@Entity
public class Controlador extends BaseEntity<String> {

    private static final long serialVersionUID = 521560643019927963L;

    @Id
    @GeneratedValue(generator = "hibernate-uuid")
    @GenericGenerator(name = "uuid", strategy = "uuid2")
    @Column(name = "id", unique = true)
    private String id;

    @Column
    private String descricao;

    @Column
    private String numeroSMEE;
    
    @Column
    private String idControlador;
    
    @Column
    private String numeroSMEEConjugado1;
    
    @Column
    private String numeroSMEEConjugado2;
    
    @Column
    private String numeroSMEEConjugado3;
    
    @Column
    private String firmware;
    
    @OneToOne
    @JoinColumn(name = "coordenada_id")
    private CoordenadaGeografica coordenada;
    private Modelo modelo;

    @OneToOne
    @JoinColumn(name = "area_id")
    private Area area;

    @OneToMany(mappedBy = "controlador", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Anel> aneis;

    @OneToMany(mappedBy = "controlador", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<GrupoSemaforico> gruposSemaforicos;

    @Column
    private Date dataCriacao;

    @Column
    private Date dataAtualizacao;

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

    public Modelo getModelo() {
        return modelo;
    }

    public void setModelo(Modelo modelo) {
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
