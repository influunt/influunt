package models;

import com.avaje.ebean.Model;
import com.avaje.ebean.annotation.CreatedTimestamp;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import json.deserializers.InfluuntDateTimeDeserializer;
import json.serializers.InfluuntDateTimeSerializer;
import org.joda.time.DateTime;

import javax.persistence.*;
import java.io.Serializable;
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

    @ManyToOne
    private Controlador controladorOrigem;

    @ManyToOne
    private Controlador controladorEdicao;

    @ManyToOne
    private Usuario usuario;

    @Column
    private String descricao;

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
     * @param controlador       - {@link Controlador} de origem
     * @param controladorEdicao - {@link Controlador} de destino
     * @param usuario           - {@link Usuario} que realizou a operação
     */
    public VersaoControlador(Controlador controlador, Controlador controladorEdicao, Usuario usuario) {
        super();
        this.controladorOrigem = controlador;
        this.controladorEdicao = controladorEdicao;
        this.usuario = usuario;
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public Controlador getControladorOrigem() {
        return controladorOrigem;
    }

    public void setOrigem(Controlador controladorOrigem) {
        this.controladorOrigem = controladorOrigem;
    }

    public Controlador getControladorEdicao() {
        return controladorEdicao;
    }

    public void setControladorEdicao(Controlador controladorEdicao) {
        this.controladorEdicao = controladorEdicao;
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
}

