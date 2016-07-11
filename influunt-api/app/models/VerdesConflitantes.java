package models;

import com.avaje.ebean.Model;
import com.avaje.ebean.annotation.CreatedTimestamp;
import com.avaje.ebean.annotation.UpdatedTimestamp;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import json.deserializers2.InfluuntDateTimeDeserializer;
import json.serializers2.InfluuntDateTimeSerializer;
import json.serializers2.VerdesConflitantesSerializer;
import org.joda.time.DateTime;

import javax.persistence.*;
import java.util.UUID;

/**
 * Created by lesiopinheiro on 7/11/16.
 */
@Entity
@Table(name = "verdes_conflitantes")
@JsonSerialize(using = VerdesConflitantesSerializer.class)
public class VerdesConflitantes extends Model {

    @Id
    private UUID id;

    @ManyToOne
    private GrupoSemaforico origem;

    @ManyToOne
    private GrupoSemaforico destino;

    @Column
    @JsonDeserialize(using = InfluuntDateTimeDeserializer.class)
    @JsonSerialize(using = InfluuntDateTimeSerializer.class)
    @CreatedTimestamp
    private DateTime dataCriacao;

    @Column
    @JsonDeserialize(using = InfluuntDateTimeDeserializer.class)
    @JsonSerialize(using = InfluuntDateTimeSerializer.class)
    @UpdatedTimestamp
    private DateTime dataAtualizacao;

    public VerdesConflitantes() {
        super();
    }

    public VerdesConflitantes(GrupoSemaforico origem, GrupoSemaforico destino) {
        super();
        this.origem = origem;
        this.destino = destino;
    }


    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public GrupoSemaforico getOrigem() {
        return origem;
    }

    public void setOrigem(GrupoSemaforico origem) {
        this.origem = origem;
    }

    public GrupoSemaforico getDestino() {
        return destino;
    }

    public void setDestino(GrupoSemaforico destino) {
        this.destino = destino;
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

    public boolean conflitaComEleMesmo(GrupoSemaforico grupoSemaforico) {
        return origem.equals(grupoSemaforico) && destino.equals(grupoSemaforico);
    }

    public boolean conflitaGrupoSemaforicoOutroAnel(GrupoSemaforico grupoSemaforico) {
        return !getVerdeConflitante(grupoSemaforico).getAnel().equals(grupoSemaforico.getAnel());
    }

    public GrupoSemaforico getVerdeConflitante(GrupoSemaforico grupoSemaforico) {
        if (origem.equals(grupoSemaforico)) {
            return destino;
        }
        return origem;
    }

}
