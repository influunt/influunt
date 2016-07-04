package models;

import java.util.ArrayList;
import java.util.List;

import be.objectify.deadbolt.java.models.Permission;
import be.objectify.deadbolt.java.models.Role;
import be.objectify.deadbolt.java.models.Subject;
import com.avaje.ebean.Model;
import com.avaje.ebean.annotation.CreatedTimestamp;
import com.avaje.ebean.annotation.UpdatedTimestamp;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import helpers.HashHelper;
import json.deserializers.InfluuntDateTimeDeserializer;
import json.serializers.InfluuntDateTimeSerializer;
import json.serializers.UsuarioSerializer;
import org.hibernate.validator.constraints.NotBlank;
import org.joda.time.DateTime;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

@Entity
@Table(name = "usuarios")
@JsonSerialize(using = UsuarioSerializer.class)
public class Usuario extends Model implements Subject {

    public static Model.Finder<String, Usuario> find = new Model.Finder<String, Usuario>(Usuario.class);

    @Id
    @NotBlank(message = "não pode ficar em branco")
    private String login;

    @Column
    String senha;

    @Column
    @NotBlank(message = "não pode ficar em branco")
    private String email;

    @Column
    @NotBlank(message = "não pode ficar em branco")
    private String nome;

    @Column
    private Boolean root = false;

    @ManyToOne(fetch = FetchType.EAGER)
    private Area area;

    @ManyToOne
    private Perfil perfil;

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


    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getSenha() {
        return senha;
    }

    public void setSenha(String senha) {
        this.senha = HashHelper.createPassword(senha);
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

    public Boolean isRoot() {
        return root;
    }

    public void setRoot(Boolean root) {
        this.root = root;
    }

    public Area getArea() {
        return this.area;
    }

    public void setArea(Area area) {
        this.area = area;
    }

    @Override
    public List<? extends Role> getRoles() {
        return null;
    }

    @Override
    public List<? extends Permission> getPermissions() {
        if (this.perfil != null) {
            List<Permissao> permissoes = this.perfil.getPermissoes();
            if (permissoes != null) {
                return permissoes;
            }
        }
        return new ArrayList<Permission>();
    }

    public Perfil getPerfil() {
        return perfil;
    }

    public void setPerfil(Perfil perfil) {
        this.perfil = perfil;
    }

    @Override
    public String getIdentifier() {
        return getLogin();
    }

    public boolean isAllowed(String key) {
        return getPermissions().stream().filter(p -> p.getValue().equals(key)).count() > 0;
    }
}
