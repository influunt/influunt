package models;

import be.objectify.deadbolt.java.models.Permission;
import be.objectify.deadbolt.java.models.Role;
import be.objectify.deadbolt.java.models.Subject;
import com.avaje.ebean.Model;
import com.avaje.ebean.annotation.ChangeLog;
import com.avaje.ebean.annotation.CreatedTimestamp;
import com.avaje.ebean.annotation.UpdatedTimestamp;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import excpetions.InfluuntNoMatchException;
import helpers.HashHelper;
import json.deserializers.InfluuntDateTimeDeserializer;
import json.deserializers.UsuarioDeserialiazer;
import json.serializers.InfluuntDateTimeSerializer;
import json.serializers.UsuarioSerializer;
import org.hibernate.validator.constraints.NotBlank;
import org.joda.time.DateTime;
import org.joda.time.Hours;

import javax.persistence.*;
import javax.validation.constraints.AssertTrue;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Entity
@Table(name = "usuarios")
@ChangeLog
@JsonSerialize(using = UsuarioSerializer.class)
@JsonDeserialize(using = UsuarioDeserialiazer.class)
public class Usuario extends Model implements Subject, Serializable {

    private static final long serialVersionUID = 5650038782769149539L;

    public static Model.Finder<UUID, Usuario> find = new Model.Finder<UUID, Usuario>(Usuario.class);

    @Column
    String senha;

    @Id
    private UUID id;

    @Column
    private String idJson;

    @NotBlank(message = "não pode ficar em branco")
    @Column(unique = true)
    private String login;

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

    @OneToMany(mappedBy = "usuario", cascade = CascadeType.ALL)
    private List<DisparoAlarme> disparoAlarmes;

    @Column
    private String resetPasswordToken;

    @Column
    private DateTime passwordTokenExpiration;

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

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

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
        return new ArrayList<Permissao>();
    }

    public Perfil getPerfil() {
        return perfil;
    }

    public void setPerfil(Perfil perfil) {
        this.perfil = perfil;
    }

    public String getResetPasswordToken() {
        return resetPasswordToken;
    }

    public void setResetPasswordToken(String resetPasswordToken) {
        this.resetPasswordToken = resetPasswordToken;
    }

    public DateTime getPasswordTokenExpiration() {
        return passwordTokenExpiration;
    }

    public void setPasswordTokenExpiration(DateTime passwordTokenExpiration) {
        this.passwordTokenExpiration = passwordTokenExpiration;
    }

    @Override
    public String getIdentifier() {
        return getLogin();
    }

    public boolean isAllowed(String key) {
        return getPermissions().stream().filter(p -> p.getValue().equals(key)).count() > 0;
    }

    public boolean isAllowedByArea(String controladorId) {
        if (getArea() != null) {
            Controlador controlador = Controlador.findUniqueByArea(controladorId, getArea().getId().toString());
            return controlador != null;
        }
        return false;
    }

    public boolean isResetPasswordTokenValid(String token) {
        if (this.getResetPasswordToken() != null && this.getResetPasswordToken().compareTo(token) == 0) {
            boolean tokenExpired = Hours.hoursBetween(this.getPasswordTokenExpiration(), new DateTime()).getHours() >= 24;
            return !tokenExpired;
        }
        return false;
    }

    public void redefinirSenha(String novaSenha, String confirmacaoNovaSenha) throws InfluuntNoMatchException {

        if (novaSenha.compareTo(confirmacaoNovaSenha) != 0)
            throw new InfluuntNoMatchException("A nova senha e sua confirmação não conferem.");

        this.setSenha(novaSenha);
        this.setResetPasswordToken(null);
        this.setPasswordTokenExpiration(null);
    }

    public boolean podeAcessarTodasAreas() {
        return getPermissions().stream().filter(permission -> permission.getValue().equals("visualizarTodasAreas")).count() > 0;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Usuario usuario = (Usuario) o;

        return id != null ? id.equals(usuario.id) : usuario.id == null;

    }

    @Override
    public int hashCode() {
        return id != null ? id.hashCode() : 0;
    }


    public List<DisparoAlarme> getDisparoAlarmes() {
        return disparoAlarmes;
    }

    public void setDisparoAlarmes(List<DisparoAlarme> disparoAlarmes) {
        this.disparoAlarmes = disparoAlarmes;
    }

    @AssertTrue(message = "não pode ficar em branco")
    public boolean isPerfilObrigatorioSeNaoForRoot() {
        return isRoot() || getPerfil() != null;
    }
}
