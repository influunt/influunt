package models;

import checks.TabelaHorariosCheck;
import com.avaje.ebean.Model;
import com.avaje.ebean.annotation.CreatedTimestamp;
import com.avaje.ebean.annotation.UpdatedTimestamp;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import json.deserializers.InfluuntDateTimeDeserializer;
import json.serializers.InfluuntDateTimeSerializer;
import org.joda.time.DateTime;

import javax.persistence.*;
import javax.validation.Valid;
import javax.validation.constraints.AssertTrue;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 * Entidade que representa o {@link TabelaHorario} no sistema
 *
 * @author lesiopinheiro
 * @author lesiopinheiro
 */
@Entity
@Table(name = "tabela_horarios")
public class TabelaHorario extends Model implements Cloneable, Serializable {

    private static final long serialVersionUID = -165840092475392332L;

    public static Finder<UUID, TabelaHorario> find = new Finder<UUID, TabelaHorario>(TabelaHorario.class);

    @Id
    private UUID id;

    @Column
    private String idJson;

    @OneToOne
    private Anel anel;

    @OneToMany(cascade = CascadeType.ALL)
    @Valid
    private List<Evento> eventos;

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

    public TabelaHorario() {
        super();
        this.idJson = UUID.randomUUID().toString();
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public String getIdJson() {
        return idJson;
    }

    public void setIdJson(String idJson) {
        this.idJson = idJson;
    }

    public Anel getAnel() {
        return anel;
    }

    public void setAnel(Anel anel) {
        this.anel = anel;
    }

    public List<Evento> getEventos() {
        return eventos;
    }

    public void setEventos(List<Evento> eventos) {
        this.eventos = eventos;
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

    @AssertTrue(groups = TabelaHorariosCheck.class,
            message = "A tabela horário deve ter pelo menos 1 evento configurado.")
    public boolean isAoMenosUmEvento() {
        return !this.getEventos().isEmpty();
    }

    public void addEventos(Evento evento) {
        if (getEventos() == null) {
            setEventos(new ArrayList<Evento>());
        }
        getEventos().add(evento);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        TabelaHorario tabelaHorario = (TabelaHorario) o;

        return id != null ? id.equals(tabelaHorario.id) : tabelaHorario.id == null;

    }

    @Override
    public int hashCode() {
        return id != null ? id.hashCode() : 0;
    }

    @Override
    public Object clone() throws CloneNotSupportedException {
        return super.clone();
    }

}

