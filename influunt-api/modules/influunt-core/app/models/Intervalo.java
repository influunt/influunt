package models;

import checks.PlanosCheck;
import com.avaje.ebean.Model;
import com.avaje.ebean.annotation.ChangeLog;
import com.avaje.ebean.annotation.CreatedTimestamp;
import com.avaje.ebean.annotation.UpdatedTimestamp;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import json.deserializers.InfluuntDateTimeDeserializer;
import json.serializers.InfluuntDateTimeSerializer;
import org.joda.time.DateTime;

import javax.persistence.*;
import javax.validation.constraints.AssertTrue;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.List;
import java.util.UUID;

import static com.sun.tools.doclint.Entity.nu;

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

    @OneToOne
    @NotNull
    private GrupoSemaforicoPlano grupoSemaforicoPlano;

    @NotNull
    private Integer ordem;

    @NotNull
    private EstadoGrupoSemaforico estadoGrupoSemaforico;

    @NotNull
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

    @Override
    public Object clone() throws CloneNotSupportedException {
        return super.clone();
    }
}
