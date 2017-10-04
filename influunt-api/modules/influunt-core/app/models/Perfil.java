package models;

import be.objectify.deadbolt.java.models.Role;
import com.avaje.ebean.Model;
import com.avaje.ebean.annotation.ChangeLog;
import com.avaje.ebean.annotation.CreatedTimestamp;
import com.avaje.ebean.annotation.UpdatedTimestamp;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import json.deserializers.InfluuntDateTimeDeserializer;
import json.serializers.InfluuntDateTimeSerializer;
import json.serializers.PerfilSerializer;
import org.hibernate.validator.constraints.NotBlank;
import org.joda.time.DateTime;

import javax.persistence.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 * Created by rodrigosol on 6/29/16.
 */
@Entity
@Table(name = "perfis")
@ChangeLog
@JsonSerialize(using = PerfilSerializer.class)
public class Perfil extends Model implements Role, Serializable {

    private static final long serialVersionUID = -4815523520090761198L;

    public static Finder<UUID, Perfil> find = new Finder<UUID, Perfil>(Perfil.class);

    @ManyToMany(cascade = CascadeType.REMOVE)
    @JoinTable(name = "permissoes_perfis", joinColumns = {@JoinColumn(name = "perfil_id")}, inverseJoinColumns = {@JoinColumn(name = "permissao_id")})
    private List<Permissao> permissoes;

    @OneToMany(mappedBy = "perfil")
    private List<Usuario> usuarios;

    @Id
    private UUID id;

    @Column
    private String idJson;

    @Column
    @NotBlank(message = "não pode ficar em branco")
    private String nome;

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

    public String getIdJson() {
        return idJson;
    }

    public void setIdJson(String idJson) {
        this.idJson = idJson;
    }

    @Override
    public String getName() {
        return nome;
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public List<Permissao> getPermissoes() {
        return permissoes;
    }

    public void setPermissoes(List<Permissao> permissoes) {
        this.permissoes = permissoes;
    }

    public void addPermissao(Permissao permissao) {
        if (getPermissoes() == null) {
            setPermissoes(new ArrayList<>());
        }
        getPermissoes().add(permissao);
    }

    public List<Usuario> getUsuarios() {
        return usuarios;
    }

    public void setUsuarios(List<Usuario> usuarios) {
        this.usuarios = usuarios;
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

    public boolean isAdministrador() {
        return "Administrador".equalsIgnoreCase(nome);
    }

    public boolean isCoordenador() {
        return "Coordenador".equalsIgnoreCase(nome);
    }
}
