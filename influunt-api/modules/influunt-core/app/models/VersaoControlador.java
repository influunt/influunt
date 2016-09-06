package models;

import com.avaje.ebean.Model;
import com.avaje.ebean.annotation.CreatedTimestamp;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import json.deserializers.InfluuntDateTimeDeserializer;
import json.serializers.InfluuntDateTimeSerializer;
import org.joda.time.DateTime;

import javax.persistence.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 * Created by lesiopinheiro on 8/4/16.
 */
@Entity
@Table(name = "versoes_controladores")
public class VersaoControlador extends Model implements Serializable {

    private static final long serialVersionUID = -7484351940720950030L;

    public static Finder<UUID, VersaoControlador> find = new Finder<UUID, VersaoControlador>(VersaoControlador.class);

    @Id
    private UUID id;

    @Column
    private String idJson;

    @JsonIgnore
    @OneToOne
    private Controlador controladorOrigem;

    @JsonIgnore
    @OneToOne
    private Controlador controlador;

    @JsonIgnore
    @ManyToOne
    private ControladorFisico controladorFisico;

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

    public VersaoControlador() {

    }

    /**
     * Criar uma {@link VersaoControlador} para registro histórico
     *
     * @param controladorOrigem - {@link Controlador} de origem
     * @param controlador       - {@link Controlador} de destino
     * @param usuario           - {@link Usuario} que realizou a operação
     */
    public VersaoControlador(Controlador controladorOrigem, Controlador controlador, Usuario usuario) {
        super();
        this.controladorOrigem = controladorOrigem;
        this.controlador = controlador;
        this.usuario = usuario;
        this.statusVersao = StatusVersao.EDITANDO;

    }

    public VersaoControlador(Controlador controlador, ControladorFisico controladorFisico, Usuario usuario) {
        super();
        this.controlador = controlador;
        this.controladorFisico = controladorFisico;
        this.usuario = usuario;
        this.descricao = "Controlador criado pelo usuário: ".concat(usuario.getNome());
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

    public Controlador getControladorOrigem() {
        return controladorOrigem;
    }

    public void setOrigem(Controlador controladorOrigem) {
        this.controladorOrigem = controladorOrigem;
    }

    public Controlador getControlador() {
        return controlador;
    }

    public void setControlador(Controlador controladorEdicao) {
        this.controlador = controladorEdicao;
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

    public DateTime getDataCriacao() {
        return dataCriacao;
    }

    public void setDataCriacao(DateTime dataCriacao) {
        this.dataCriacao = dataCriacao;
    }

    public StatusVersao getStatusVersao() {
        return statusVersao;
    }

    public void setStatusVersao(StatusVersao statusVersao) {
        this.statusVersao = statusVersao;
    }

    public ControladorFisico getControladorFisico() {
        return controladorFisico;
    }

    public void setControladorFisico(ControladorFisico controladorFisico) {
        this.controladorFisico = controladorFisico;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        VersaoControlador that = (VersaoControlador) o;

        return id != null ? id.equals(that.id) : that.id == null;

    }

    @Override
    public int hashCode() {
        return id != null ? id.hashCode() : 0;
    }

    public static boolean usuarioPodeEditarControlador(Controlador controlador, Usuario usuario) {
        VersaoControlador versao = findByControlador(controlador);
        if (versao != null) {
            return usuario.equals(versao.getUsuario());
        }
        return false;
    }

    /**
     * Retorna a {@link  VersaoControlador}
     *
     * @param controlador
     * @return
     */
    public static List<VersaoControlador> versoes(Controlador controlador) {
        ArrayList<VersaoControlador> versoes = new ArrayList<VersaoControlador>();
        getElement(versoes, controlador);
        return versoes;
    }

    static void getElement(ArrayList<VersaoControlador> versoes, Controlador controlador) {
        VersaoControlador versao = findByControlador(controlador);
        if (versao != null) {
            versoes.add(versao);
            if (versao.getControladorOrigem() != null) {
                getElement(versoes, versao.getControladorOrigem());
            }
        }
    }

    public static VersaoControlador findByControlador(Controlador controlador) {
        return VersaoControlador.find.where().eq("controlador_id", controlador.getId()).findUnique();
    }

    public void ativar() {
        setStatusVersao(StatusVersao.ATIVO);
    }
}

