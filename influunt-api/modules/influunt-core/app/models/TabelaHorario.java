package models;

import checks.TabelaHorariosCheck;
import com.avaje.ebean.Model;
import com.avaje.ebean.annotation.ChangeLog;
import com.avaje.ebean.annotation.CreatedTimestamp;
import com.avaje.ebean.annotation.UpdatedTimestamp;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import json.deserializers.InfluuntDateTimeDeserializer;
import json.serializers.InfluuntDateTimeSerializer;
import org.joda.time.DateTime;

import javax.persistence.*;
import javax.validation.Valid;
import javax.validation.constraints.AssertTrue;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

/**
 * Entidade que representa o {@link TabelaHorario} no sistema
 *
 * @author lesiopinheiro
 * @author lesiopinheiro
 */
@Entity
@Table(name = "tabela_horarios")
@ChangeLog
public class TabelaHorario extends Model implements Cloneable, Serializable {

    private static final long serialVersionUID = -165840092475392332L;

    public static Finder<UUID, TabelaHorario> find = new Finder<UUID, TabelaHorario>(TabelaHorario.class);

    @Id
    private UUID id;

    @Column
    private String idJson;

    @JsonIgnore
    @Transient
    private Controlador controlador;

    @OneToOne
    @NotNull(message = "nao pode ser vazio")
    private VersaoTabelaHoraria versaoTabelaHoraria;

    @OneToMany(mappedBy = "tabelaHorario", cascade = CascadeType.ALL)
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

    public VersaoTabelaHoraria getVersaoTabelaHoraria() {
        return versaoTabelaHoraria;
    }

    public void setVersaoTabelaHoraria(VersaoTabelaHoraria versaoTabelaHoraria) {
        this.versaoTabelaHoraria = versaoTabelaHoraria;
    }

    public Controlador getControlador() {
        return getVersaoTabelaHoraria().getControlador();
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
            message = "A tabela horária deve ter pelo menos 1 evento configurado.")
    public boolean isAoMenosUmEvento() {
        return !this.getEventos().stream().filter(ev -> !ev.isDestroy()).collect(Collectors.toList()).isEmpty();
    }

    public void addEvento(Evento evento) {
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

    @Override
    public String toString() {
        return "TabelaHorario{" +
                "id=" + id +
                ", idJson='" + idJson + '\'' +
                ", controlador=" + controlador +
                ", eventos=" + eventos +
                ", dataCriacao=" + dataCriacao +
                ", dataAtualizacao=" + dataAtualizacao +
                '}';
    }

    public void voltarVersaoAnterior() {
        VersaoTabelaHoraria versaoAtual = getVersaoTabelaHoraria();
        if (versaoAtual != null && StatusVersao.EDITANDO.equals(versaoAtual.getStatusVersao())) {
            VersaoTabelaHoraria versaoAnterior = versaoAtual.getVersaoAnterior();
            if (versaoAnterior != null) {
                versaoAnterior.setStatusVersao(StatusVersao.CONFIGURADO);
                versaoAnterior.update();
                versaoAtual.delete();
            }
        }
    }
}

