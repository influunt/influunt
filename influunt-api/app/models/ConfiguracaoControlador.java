package models;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

import org.hibernate.annotations.GenericGenerator;

import framework.BaseEntity;

/**
 * Entidade que represnta as configurações dos controladores no sistema
 * @author lesiopinheiro
 *
 */
@Entity
public class ConfiguracaoControlador extends BaseEntity<String> {
    
    private static final long serialVersionUID = -4628897272277523020L;

    @Id
    @GeneratedValue(generator = "hibernate-uuid")
    @GenericGenerator(name = "uuid", strategy = "uuid2")
    @Column(name = "id", unique = true)
    private String id;
    
    @Column
    private Integer limiteEstagio = 16;
    
    @Column
    private Integer limiteGrupoSemaforico = 16;
    
    @Column
    private Integer limiteAnel = 4;
    
    @Column
    private Integer limiteDetectorPedestre = 4;
    
    @Column
    private Integer limiteDetectorVeicular = 8;
    
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

    public Integer getLimiteEstagio() {
        return limiteEstagio;
    }

    public void setLimiteEstagio(Integer limiteEstagio) {
        this.limiteEstagio = limiteEstagio;
    }

    public Integer getLimiteGrupoSemaforico() {
        return limiteGrupoSemaforico;
    }

    public void setLimiteGrupoSemaforico(Integer limiteGrupoSemaforico) {
        this.limiteGrupoSemaforico = limiteGrupoSemaforico;
    }

    public Integer getLimiteAnel() {
        return limiteAnel;
    }

    public void setLimiteAnel(Integer limiteAnel) {
        this.limiteAnel = limiteAnel;
    }

    public Integer getLimiteDetectorPedestre() {
        return limiteDetectorPedestre;
    }

    public void setLimiteDetectorPedestre(Integer limiteDetectorPedestre) {
        this.limiteDetectorPedestre = limiteDetectorPedestre;
    }

    public Integer getLimiteDetectorVeicular() {
        return limiteDetectorVeicular;
    }

    public void setLimiteDetectorVeicular(Integer limiteDetectorVeicular) {
        this.limiteDetectorVeicular = limiteDetectorVeicular;
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
