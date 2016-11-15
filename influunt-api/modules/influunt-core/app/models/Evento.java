package models;

import checks.TabelaHorariosCheck;
import com.avaje.ebean.Model;
import com.avaje.ebean.annotation.ChangeLog;
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
@ChangeLog
public class Evento extends Model implements Cloneable, Serializable, Comparable<Evento> {

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

    @ManyToOne
    private Agrupamento agrupamento;

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

    @Transient
    private boolean isDestroy = false;


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


    @AssertTrue(groups = TabelaHorariosCheck.class, message = "Existem eventos configurados no mesmo dia e horário.")
    public boolean isEventosMesmoDiaEHora() {
        if (!this.getTabelaHorario().getEventos().isEmpty() && this.getHorario() != null && this.getDiaDaSemana() != null && this.getTabelaHorario().getVersaoTabelaHoraria().getStatusVersao() == StatusVersao.EDITANDO) {
            return this.getTabelaHorario().getEventos().stream().filter( evento ->
                evento != this &&
                !evento.isDestroy() &&
                this.getDiaDaSemana().equals(evento.getDiaDaSemana()) &&
                this.getHorario().equals(evento.getHorario())
            ).count() == 0;
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

    public void setDiaDaSemana(DiaDaSemana diaDaSemana) {
        this.diaDaSemana = diaDaSemana;
    }

    @AssertTrue(groups = TabelaHorariosCheck.class,
        message = "não pode ficar em branco")
    public boolean isData() {
        if (this.isEventoEspecialRecorrente() || this.isEventoEspecialNaoRecorrente()) {
            return this.getData() != null;
        }
        return true;
    }

    @AssertTrue(groups = TabelaHorariosCheck.class,
        message = "O plano selecionado não está configurado em todos os anéis.")
    public boolean isPlanosConfigurados() {
        if (getPosicaoPlano() != null) {
            return getTabelaHorario().getControlador()
                .getAneis()
                .stream()
                .filter(Anel::isAtivo)
                .allMatch(anel -> anel.getPlanos().stream().anyMatch(plano -> getPosicaoPlano().equals(plano.getPosicao())));
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


    @Override
    public int compareTo(Evento o) {
        if (this.getTipo().compareTo(o.getTipo()) == 0) {
            if (this.getTipo().equals(TipoEvento.NORMAL)) {
                if (this.getDiaDaSemana().compareTo(o.getDiaDaSemana()) == 0) {
                    return this.getHorario().compareTo(o.getHorario());
                } else {
                    return this.getDiaDaSemana().compareTo(o.getDiaDaSemana());
                }
            } else {
                return this.getData().compareTo(o.getData());
            }
        } else {
            return this.getTipo().compareTo(o.getTipo());
        }
    }

    @Override
    public String toString() {
        return "EventoMotor{" +
            "posicaoPlano=" + posicaoPlano +
            ", data=" + data +
            ", diaDaSemana=" + diaDaSemana +
            ", tipo=" + tipo +
            '}';
    }

    public boolean tenhoPrioridade(Evento evento, boolean euSouPetrio, boolean outroEPetrio) {

        if (euSouPetrio && !outroEPetrio) {
            return true;
        } else if (!euSouPetrio && outroEPetrio) {
            return false;
        } else {
            return true;
        }

    }

    public boolean isAtivoEm(DateTime agora) {


        if (!this.getTipo().equals(TipoEvento.NORMAL)) {
            DateTime data = getDataHora();

            boolean ano = this.getTipo().equals(TipoEvento.ESPECIAL_NAO_RECORRENTE) ? agora.getYear() == data.getYear() : true;
            if (!ano || agora.getMonthOfYear() != data.getMonthOfYear() || agora.getDayOfMonth() != data.getDayOfMonth()) {
                return false;
            } else {
                if (agora.getMillisOfDay() >= data.getMillisOfDay()) {
                    return true;
                } else {
                    return false;
                }
            }
        }
        return false;
    }

    public DateTime getDataHora() {

        DateTime dataHora;
        if (this.data != null) {
            dataHora = new DateTime(this.data);

        } else {
            DateTime dateNow = DateTime.now();
            dataHora = new DateTime(dateNow.year().get(), dateNow.monthOfYear().get(), dateNow.dayOfMonth().get(), 0, 0, 0);
        }

        return dataHora.withMillisOfDay(0).plusMillis(this.horario.getMillisOfDay());
    }

    public Agrupamento getAgrupamento() {
        return agrupamento;
    }

    public void setAgrupamento(Agrupamento agrupamento) {
        this.agrupamento = agrupamento;
    }

    public boolean isDestroy() {
        return isDestroy;
    }

    public void setDestroy(boolean destroy) {
        isDestroy = destroy;
    }

    public Plano getPlano(Integer posicaoAnel) {
        return getTabelaHorario().findAnelByPosicao(posicaoAnel).findPlanoByPosicao(getPosicaoPlano());
    }

    public Long getMomentoEntrada(Integer posicaoAnel, DateTime momentoOriginal) {
        Plano plano = getPlano(posicaoAnel);
        if (ModoOperacaoPlano.TEMPO_FIXO_COORDENADO.equals(plano.getModoOperacao())) {
            final long tempoCiclo = plano.getTempoCiclo() * 1000L;
            final long tempoDefasagem = plano.getDefasagem() * 1000L;
            final long tempoDecorrido = momentoOriginal.getMillis();
            final long momentoEntrada = (tempoDecorrido % tempoCiclo) - tempoDefasagem;
            if (momentoEntrada < 0L) {
                return tempoCiclo - Math.abs(momentoEntrada);
            }
            return momentoEntrada;
        }
        return 0L;
    }
}
