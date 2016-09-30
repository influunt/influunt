package models;

import com.avaje.ebean.Model;
import com.avaje.ebean.annotation.ChangeLog;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.UUID;

/**
 * Created by lesiopinheiro on 7/13/16.
 */
@Entity
@Table(name = "intervalos")
@ChangeLog
public class Intervalo extends Model implements Cloneable, Serializable {

    private static final long serialVersionUID = -8086882310576023685L;

    @Id
    private UUID id;

    @Column
    private String idJson;

    @ManyToOne
    @NotNull(message = "não pode ficar em branco")
    private GrupoSemaforicoPlano grupoSemaforicoPlano;

    @NotNull(message = "não pode ficar em branco")
    @Column
    private Integer ordem;

    @Enumerated(EnumType.STRING)
    @Column
    @NotNull(message = "não pode ficar em branco")
    private EstadoGrupoSemaforico estadoGrupoSemaforico;

    @NotNull(message = "não pode ficar em branco")
    @Column
    private Integer tamanho;


    public Intervalo() {
        super();
        this.idJson = UUID.randomUUID().toString();
    }

    public String getIdJson() {
        return idJson;
    }

    public void setIdJson(String idJson) {
        this.idJson = idJson;
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }


    public Integer getOrdem() {
        return ordem;
    }

    public void setOrdem(Integer ordem) {
        this.ordem = ordem;
    }

    public Integer getTamanho() {
        return tamanho;
    }

    public void setTamanho(Integer tamanho) {
        this.tamanho = tamanho;
    }

    public EstadoGrupoSemaforico getEstadoGrupoSemaforico() {
        return estadoGrupoSemaforico;
    }

    public void setEstadoGrupoSemaforico(EstadoGrupoSemaforico estadoGrupoSemaforico) {
        this.estadoGrupoSemaforico = estadoGrupoSemaforico;
    }

    public GrupoSemaforicoPlano getGrupoSemaforicoPlano() {
        return grupoSemaforicoPlano;
    }

    public void setGrupoSemaforicoPlano(GrupoSemaforicoPlano grupoSemaforicoPlano) {
        this.grupoSemaforicoPlano = grupoSemaforicoPlano;
    }

    @Override
    public Object clone() throws CloneNotSupportedException {
        return super.clone();
    }
}
