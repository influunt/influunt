package models;

import com.avaje.ebean.Model;
import com.avaje.ebean.annotation.ChangeLog;
import com.avaje.ebean.annotation.CreatedTimestamp;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import json.deserializers.InfluuntDateTimeDeserializer;
import json.serializers.InfluuntDateTimeSerializer;
import org.joda.time.DateTime;

import javax.persistence.*;
import javax.validation.Valid;
import java.io.Serializable;
import java.util.UUID;

/**
 * Created by lesiopinheiro on 8/25/16.
 */
@Entity
@Table(name = "versoes_tabelas_horarias")
@ChangeLog
public class VersaoTabelaHoraria extends Model implements Serializable {

    private static final long serialVersionUID = -8768656473022942047L;

    public static Finder<UUID, VersaoTabelaHoraria> find = new Finder<UUID, VersaoTabelaHoraria>(VersaoTabelaHoraria.class);

    @Id
    private UUID id;

    @Column
    private String idJson;

    @JsonIgnore
    @ManyToOne
    private Controlador controlador;

    @JsonIgnore
    @OneToOne
    private TabelaHorario tabelaHorariaOrigem;

    @JsonIgnore
    @OneToOne(cascade = CascadeType.ALL, mappedBy = "versaoTabelaHoraria")
    @Valid
    private TabelaHorario tabelaHoraria;

    @ManyToOne
    private Usuario usuario;

    @Column
    private String descricao;

    @Column
    private StatusVersao statusVersao;

    @Column
    @JsonDeserialize(using = InfluuntDateTimeDeserializer.class)
    @JsonSerialize(using = InfluuntDateTimeSerializer.class)
    @CreatedTimestamp
    private DateTime dataCriacao;

    public VersaoTabelaHoraria() {
        super();
        this.idJson = UUID.randomUUID().toString();
        this.statusVersao = StatusVersao.EDITANDO;
    }

    public VersaoTabelaHoraria(Controlador controlador, TabelaHorario tabelaHorariaOrigem, TabelaHorario tabelaHoraria, Usuario usuario) {
        super();
        this.idJson = UUID.randomUUID().toString();
        this.controlador = controlador;
        this.tabelaHorariaOrigem = tabelaHorariaOrigem;
        this.tabelaHoraria = tabelaHoraria;
        this.usuario = usuario;
        if (usuario != null && usuario.getNome() != null) {
            this.descricao = "Tabela Horaria criado pelo usuário:".concat(usuario.getNome());
        }
        this.statusVersao = StatusVersao.EDITANDO;
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

    public TabelaHorario getTabelaHorariaOrigem() {
        return tabelaHorariaOrigem;
    }

    public void setTabelaHorariaOrigem(TabelaHorario tabelaHorariaOrigem) {
        this.tabelaHorariaOrigem = tabelaHorariaOrigem;
    }

    public Controlador getControlador() {
        return controlador;
    }

    public void setControlador(Controlador controlador) {
        this.controlador = controlador;
    }

    public TabelaHorario getTabelaHoraria() {
        return tabelaHoraria;
    }

    public void setTabelaHoraria(TabelaHorario tabelaHoraria) {
        this.tabelaHoraria = tabelaHoraria;
    }

    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public StatusVersao getStatusVersao() {
        return statusVersao;
    }

    public void setStatusVersao(StatusVersao statusVersao) {
        this.statusVersao = statusVersao;
    }

    public DateTime getDataCriacao() {
        return dataCriacao;
    }

    public void setDataCriacao(DateTime dataCriacao) {
        this.dataCriacao = dataCriacao;
    }

    public boolean isAtivo() {
        return StatusVersao.ATIVO.equals(this.getStatusVersao());
    }

    public boolean isEditando() {
        return StatusVersao.EDITANDO.equals(this.getStatusVersao());
    }

    public void ativar() {
        setStatusVersao(StatusVersao.ATIVO);
    }

    @Override
    public String toString() {
        return "VersaoTabelaHoraria{"
                + "id=" + id
                + ", idJson='" + idJson + '\''
                + ", descricao='" + descricao + '\''
                + ", statusVersao=" + statusVersao
                + ", dataCriacao=" + dataCriacao
                + '}';
    }
}
