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
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 * Created by lesiopinheiro on 8/25/16.
 */
@Entity
@Table(name = "versoes_planos")
@ChangeLog
public class VersaoPlano extends Model implements Serializable {

    private static final long serialVersionUID = -956154568458918910L;

    public static Finder<UUID, VersaoPlano> find = new Finder<UUID, VersaoPlano>(VersaoPlano.class);

    @Id
    private UUID id;

    @Column
    private String idJson;

    @JsonIgnore
    @OneToOne(optional = true)
    private VersaoPlano versaoAnterior;

    @JsonIgnore
    @ManyToOne
    private Anel anel;

    @JsonIgnore
    @OneToMany(mappedBy = "versaoPlano", cascade = CascadeType.ALL)
    @Valid
    private List<Plano> planos;

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

    public VersaoPlano() {
        super();
        this.idJson = UUID.randomUUID().toString();
        this.statusVersao = StatusVersao.EDITANDO;
    }

    public VersaoPlano(Usuario usuario) {
        this();
        this.usuario = usuario;
        if (usuario != null && usuario.getNome() != null) {
            this.descricao = "Versão criada pelo usuário: ".concat(usuario.getNome());
        }
    }

    public VersaoPlano(Anel anel, Usuario usuario) {
        this(usuario);
        this.anel = anel;
    }

    /**
     * Retorna a {@link VersaoPlano}
     *
     * @param anel
     * @return
     */

    public static List<VersaoPlano> versoes(Anel anel) {
        return findOrderByAnel(anel);
    }

    public static List<VersaoPlano> findOrderByAnel(Anel anel) {
        return VersaoPlano.find.where()
            .eq("anel_id", anel.getId())
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

    public VersaoPlano getVersaoAnterior() {
        return versaoAnterior;
    }

    public void setVersaoAnterior(VersaoPlano versaoAnterior) {
        this.versaoAnterior = versaoAnterior;
    }

    public Anel getAnel() {
        return anel;
    }

    public void setAnel(Anel anel) {
        this.anel = anel;
    }

    public List<Plano> getPlanos() {
        return planos;
    }

    public void setPlanos(List<Plano> planos) {
        this.planos = planos;
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

    public DateTime getDataAtualizacao() {
        return dataAtualizacao;
    }

    public void setDataAtualizacao(DateTime dataAtualizacao) {
        this.dataAtualizacao = dataAtualizacao;
    }

    public void addPlano(Plano plano) {
        if (getPlanos() == null) {
            setPlanos(new ArrayList<Plano>());
        }
        getPlanos().add(plano);
    }

    public boolean isAtivo() {
        return StatusVersao.SINCRONIZADO.equals(this.getStatusVersao());
    }

    public boolean isEditando() {
        return StatusVersao.EDITANDO.equals(this.getStatusVersao());
    }

    public boolean isEmConfiguracao() {
        return StatusVersao.EM_CONFIGURACAO.equals(this.getStatusVersao());
    }

    public boolean isConfigurado() {
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
        return "VersaoPlano{"
            + "id=" + id
            + ", idJson='" + idJson + '\''
            + ", versaoAnterior=" + versaoAnterior
            + ", descricao='" + descricao + '\''
            + ", statusVersao=" + statusVersao
            + ", dataCriacao=" + dataCriacao
            + '}';
    }
}
