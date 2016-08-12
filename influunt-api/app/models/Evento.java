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
import org.joda.time.LocalTime;

import javax.persistence.*;
import javax.validation.constraints.AssertTrue;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.UUID;

/**
 * Entidade que representa um {@link Evento} no sistema
 *
 * @author Pedro Pires
 */
@Entity
@Table(name = "eventos")
public class Evento extends Model implements Cloneable, Serializable {

    private static final long serialVersionUID = -8164198987601502461L;

    public static Finder<UUID, Evento> find = new Finder<UUID, Evento>(Evento.class);

    @Id
    private UUID id;

    @Column
    private String idJson;

    @Column
    @NotNull(message = "não pode ficar em branco")
    private Integer posicao;

    @Column
    @Enumerated(EnumType.STRING)
    @NotNull(message = "não pode ficar em branco")
    private DiaDaSemana diaDaSemana;

    @Column
    @NotNull(message = "não pode ficar em branco")
    private LocalTime horario;

    @ManyToOne
    @NotNull(message = "não pode ficar em branco")
    private TabelaHorario tabelaHorario;

    @ManyToOne
    @NotNull(message = "não pode ficar em branco")
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

    public Evento() {
        super();
        this.setIdJson(UUID.randomUUID().toString());
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

    public Integer getPosicao() {
        return posicao;
    }

    public void setPosicao(Integer posicao) {
        this.posicao = posicao;
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

    public Plano getPlano() {
        return plano;
    }

    public void setPlano(Plano plano) {
        this.plano = plano;
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
            message = "Existem eventos configurados no mesmo dia e horário.")
    public boolean isEventosMesmoDiaEHora() {
        if (!this.getTabelaHorario().getEventos().isEmpty() && this.getHorario() != null && this.getDiaDaSemana() != null) {
            return !(this.getTabelaHorario().getEventos().stream().filter(
                    evento -> this.getDiaDaSemana().equals(evento.getDiaDaSemana()) && this.getHorario().equals(evento.getHorario())).count() > 1);
        }
        return true;
    }

    @AssertTrue(groups = TabelaHorariosCheck.class,
            message = "O Plano deve pertencer ao mesmo anel da tabela horário.")
    public boolean isPlanoDoMesmoAnel() {
        if (this.getPlano() != null && this.getTabelaHorario() != null) {
            return this.getPlano().getAnel().equals(this.getTabelaHorario().getAnel());
        }
        return true;
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
