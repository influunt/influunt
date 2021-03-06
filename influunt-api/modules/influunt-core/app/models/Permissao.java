package models;

import be.objectify.deadbolt.java.models.Permission;
import com.avaje.ebean.Model;
import com.avaje.ebean.annotation.ChangeLog;
import com.avaje.ebean.annotation.CreatedTimestamp;
import com.avaje.ebean.annotation.UpdatedTimestamp;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import json.deserializers.InfluuntDateTimeDeserializer;
import json.serializers.InfluuntDateTimeSerializer;
import org.hibernate.validator.constraints.NotBlank;
import org.joda.time.DateTime;

import javax.persistence.*;
import java.io.Serializable;
import java.util.List;
import java.util.UUID;

/**
 * Created by rodrigosol on 6/29/16.
 */
@Entity
@Table(name = "permissoes")
@ChangeLog
public class Permissao extends Model implements Permission, Serializable {

    private static final long serialVersionUID = -1771456494137102241L;

    public static Finder<UUID, Permissao> find = new Finder<UUID, Permissao>(Permissao.class);

    @Id
    private UUID id;

    @Column
    private String idJson;

    @Column
    @NotBlank(message = "não pode ficar em branco")
    private String descricao;

    @Column
    @NotBlank(message = "não pode ficar em branco")
    private String chave;

    @ManyToMany(mappedBy = "permissoes")
    private List<Perfil> perfis;

    @ManyToMany(mappedBy = "permissoes")
    private List<PermissaoApp> permissoesApp;

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
    public String getValue() {
        return chave;
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public String getChave() {
        return chave;
    }

    public void setChave(String chave) {
        this.chave = chave;
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

    public List<PermissaoApp> getPermissoesApp() {
        return permissoesApp;
    }

    public void setPermissoesApp(List<PermissaoApp> permissoesApp) {
        this.permissoesApp = permissoesApp;
    }
}
