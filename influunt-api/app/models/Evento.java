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
import java.util.Date;
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
    private DiaDaSemana diaDaSemana;

    @Column
    @NotNull(message = "não pode ficar em branco")
    private LocalTime horario;

    @Column
    private Date data;

    @Column
    private String nome;

    @Column
    @NotNull(message = "não pode ficar em branco")
    private Integer posicaoPlano;

    @Column
    @Enumerated(EnumType.STRING)
    @NotNull(message = "não pode ficar em branco")
    private TipoEvento tipo;

    @ManyToOne
    @NotNull(message = "não pode ficar em branco")
    private TabelaHorario tabelaHorario;

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

    public Date getData() {
        return data;
    }

    public Date setData(Date data) {
        this.data = data;
        return data;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public TipoEvento getTipo() {
        return tipo;
    }

    public void setTipo(TipoEvento tipo) {
        this.tipo = tipo;
    }

    public TabelaHorario getTabelaHorario() {
        return tabelaHorario;
    }

    public void setTabelaHorario(TabelaHorario tabelaHorario) {
        this.tabelaHorario = tabelaHorario;
    }

    public Integer getPosicaoPlano() {
        return posicaoPlano;
    }

    public void setPosicaoPlano(Integer posicaoPlano) {
        this.posicaoPlano = posicaoPlano;
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
            message = "não pode ficar em branco")
    public boolean isDiaDaSemana() {
        if (this.isEventoNormal()) {
            return this.getDiaDaSemana() != null;
        }
        return true;
    }

    @AssertTrue(groups = TabelaHorariosCheck.class,
            message = "não pode ficar em branco")
    public boolean isData() {
        if (this.isEventoEspecialRecorrente() || this.isEventoEspecialNaoRecorrente()) {
            return this.getData() != null;
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

    public boolean isEventoNormal() {
        return TipoEvento.NORMAL.equals(this.getTipo());
    }

    public boolean isEventoEspecialRecorrente() {
        return TipoEvento.ESPECIAL_RECORRENTE.equals(this.getTipo());
    }

    public boolean isEventoEspecialNaoRecorrente() {
        return TipoEvento.ESPECIAL_NAO_RECORRENTE.equals(this.getTipo());
    }
}
