package models;

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
import java.io.Serializable;
import java.util.List;
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

    @Enumerated(EnumType.STRING)
    @Column
    private StatusVersao statusVersao;

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

    public VersaoTabelaHoraria() {
        super();
        this.idJson = UUID.randomUUID().toString();
        this.statusVersao = StatusVersao.EDITANDO;
    }

    public VersaoTabelaHoraria(Usuario usuario) {
        this();
        this.usuario = usuario;
        if (usuario != null && usuario.getNome() != null) {
            this.descricao = "Versão criada pelo usuário: ".concat(usuario.getNome());
        }
    }

    public VersaoTabelaHoraria(Controlador controlador, TabelaHorario tabelaHorariaOrigem, TabelaHorario tabelaHoraria, Usuario usuario) {
        this(usuario);
        this.controlador = controlador;
        this.tabelaHorariaOrigem = tabelaHorariaOrigem;
        this.tabelaHoraria = tabelaHoraria;
    }

    /**
     * Retorna a {@link  VersaoTabelaHoraria}
     *
     * @param controlador
     * @return
     */
    public static List<VersaoTabelaHoraria> versoes(Controlador controlador) {
        return findByControladorOrdered(controlador);
    }

    public static List<VersaoTabelaHoraria> findByControladorOrdered(Controlador controlador) {
        return VersaoTabelaHoraria.find.where()
            .eq("controlador_id", controlador.getId())
            .orderBy("dataAtualizacao desc")
            .findList();
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

    public DateTime getDataAtualizacao() {
        return dataAtualizacao;
    }

    public void setDataAtualizacao(DateTime dataAtualizacao) {
        this.dataAtualizacao = dataAtualizacao;
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
        return StatusVersao.SINCRONIZADO.equals(this.getStatusVersao());
    }

    public boolean isEditando() {
        return StatusVersao.EDITANDO.equals(this.getStatusVersao());
    }

    public boolean isConfigurada() {
        return StatusVersao.CONFIGURADO.equals(this.getStatusVersao());
    }

    public void ativar() {
        setStatusVersao(StatusVersao.SINCRONIZADO);
    }

    public void finalizar() {
        setStatusVersao(StatusVersao.CONFIGURADO);
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

    @JsonIgnore
    public VersaoTabelaHoraria getVersaoAnterior() {
        if (getTabelaHorariaOrigem() != null) {
            return getTabelaHorariaOrigem().getVersaoTabelaHoraria();
        }
        return null;
    }
}
