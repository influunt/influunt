package models;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import org.hibernate.annotations.GenericGenerator;

import framework.BaseEntity;

/**
 * Entidade que repesenta o {@link Modelo} do {@link Controlador} no sistema
 * @author lesiopinheiro
 *
 */
@Entity
public class Modelo extends BaseEntity<String> {
    
    private static final long serialVersionUID = -3153929481907380680L;
    
    @Id
    @GeneratedValue(generator = "hibernate-uuid")
    @GenericGenerator(name = "uuid", strategy = "uuid2")
    @Column(name = "id", unique = true)
    private String id;
    
    @ManyToOne
    @JoinColumn(name = "fabricante_id")
    private Fabricante fabricante;
    
    @ManyToOne
    @JoinColumn(name = "configuracao_controlador_id")
    private ConfiguracaoControlador configuracao;
    
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

    public Fabricante getFabricante() {
        return fabricante;
    }

    public void setFabricante(Fabricante fabricante) {
        this.fabricante = fabricante;
    }

    public ConfiguracaoControlador getConfiguracao() {
        return configuracao;
    }

    public void setConfiguracao(ConfiguracaoControlador configuracao) {
        this.configuracao = configuracao;
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

    protected void setDataAtualizacao(Date dataAtualizacao) {
        this.dataAtualizacao = dataAtualizacao;
    }

    
}
