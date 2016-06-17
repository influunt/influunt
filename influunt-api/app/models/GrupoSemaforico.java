package models;

import com.avaje.ebean.Model;
import com.avaje.ebean.annotation.CreatedTimestamp;
import com.avaje.ebean.annotation.UpdatedTimestamp;
import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import org.joda.time.DateTime;
import utils.InfluuntDateTimeDeserializer;
import utils.InfluuntDateTimeSerializer;

import javax.persistence.*;
import java.util.List;
import java.util.UUID;

/**
 * Entidade que representa o {@link GrupoSemaforico} no sistema
 *
 * @author lesiopinheiro
 *
 */
@Entity
@Table(name = "grupos_semaforicos")
public class GrupoSemaforico extends Model {

    private static final long serialVersionUID = 7439393568357903233L;

    @Id
    private UUID id;

    @ManyToOne
    private TipoGrupoSemaforico tipo;

    @ManyToOne
    @JoinColumn(name = "anel_id")
    private Anel anel;

    @ManyToOne
    @JsonBackReference
    private Controlador controlador;

    @ManyToOne
    private GrupoSemaforico grupoConflito;

    @OneToMany(mappedBy = "grupoConflito")
    private List<GrupoSemaforico> verdesConflitantes;

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

    public GrupoSemaforico(TipoGrupoSemaforico tipoGrupoSemaforico) {
        this.tipo = tipoGrupoSemaforico;
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public TipoGrupoSemaforico getTipo() {
        return tipo;
    }

    public void setTipo(TipoGrupoSemaforico tipo) {
        this.tipo = tipo;
    }

    public Anel getAnel() {
        return anel;
    }

    public void setAnel(Anel anel) {
        this.anel = anel;
    }

    public Controlador getControlador() {
        return controlador;
    }

    public void setControlador(Controlador controlador) {
        this.controlador = controlador;
    }

    public GrupoSemaforico getGrupoConflito() {
        return grupoConflito;
    }

    public void setGrupoConflito(GrupoSemaforico grupoConflito) {
        this.grupoConflito = grupoConflito;
    }

    public List<GrupoSemaforico> getVerdesConflitantes() {
        return verdesConflitantes;
    }

    public void setVerdesConflitantes(List<GrupoSemaforico> verdesConflitantes) {
        this.verdesConflitantes = verdesConflitantes;
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
