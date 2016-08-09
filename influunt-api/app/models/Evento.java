package models;

import checks.NumeroDeControladores;
import com.avaje.ebean.Model;
import com.avaje.ebean.annotation.CreatedTimestamp;
import com.avaje.ebean.annotation.PrivateOwned;
import com.avaje.ebean.annotation.UpdatedTimestamp;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import json.deserializers.InfluuntDateTimeDeserializer;
import json.deserializers.TabelaHorarioDeserialiazer;
import json.serializers.AgrupamentoSerializer;
import json.serializers.InfluuntDateTimeSerializer;
import json.serializers.TabelaHorarioSerializer;
import org.hibernate.validator.constraints.NotBlank;
import org.joda.time.DateTime;
import org.joda.time.LocalTime;

import javax.persistence.*;
import java.io.Serializable;
import java.util.List;
import java.util.UUID;

/**
 * Entidade que representa um {@link Evento} no sistema
 *
 * @author Pedro Pires
 */
@Entity
@Table(name = "eventos")
@JsonSerialize(using = TabelaHorarioSerializer.class)
@JsonDeserialize(using = TabelaHorarioDeserialiazer.class)
public class Evento extends Model implements Cloneable, Serializable {

    private static final long serialVersionUID = -8164198987601502461L;

    public static Finder<UUID, Evento> find = new Finder<UUID, Evento>(Evento.class);

    @Id
    private UUID id;

    @Column
    private String idJson;

    @Column
    private String numero;

    @Column
    @Enumerated(EnumType.STRING)
    private DiaDaSemana diaDaSemana;

    @Column
    private LocalTime horario;

    @ManyToOne
    private TabelaHorario tabelaHorario;

    @ManyToOne
    private Plano plano;

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

    public String getNumero() {
        return numero;
    }

    public void setNumero(String numero) {
        this.numero = numero;
    }

    public DiaDaSemana getDiaDaSemana() {
        return diaDaSemana;
    }

    public void setDiaDaSemana(DiaDaSemana diaDaSemana) {
        this.diaDaSemana = diaDaSemana;
    }

    public LocalTime getHorario() {
        return horario;
    }

    public void setHorario(LocalTime horario) {
        this.horario = horario;
    }

    public TabelaHorario getTabelaHorario() {
        return tabelaHorario;
    }

    public void setTabelaHorario(TabelaHorario tabelaHorario) {
        this.tabelaHorario = tabelaHorario;
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Evento evento = (Evento) o;

        return id != null ? id.equals(evento.id) : evento.id == null;

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
