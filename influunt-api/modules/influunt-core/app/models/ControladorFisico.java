package models;

import com.avaje.ebean.Expr;
import com.avaje.ebean.ExpressionList;
import com.avaje.ebean.Model;
import com.avaje.ebean.annotation.ChangeLog;
import com.avaje.ebean.annotation.CreatedTimestamp;
import com.avaje.ebean.annotation.UpdatedTimestamp;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import jdk.nashorn.internal.ir.annotations.Ignore;
import json.deserializers.InfluuntDateTimeDeserializer;
import json.serializers.InfluuntDateTimeSerializer;
import org.apache.commons.codec.binary.Hex;
import org.apache.commons.collections.ListUtils;
import org.joda.time.DateTime;
import utils.EncryptionUtil;
import utils.MosquittoPBKDF2;

import javax.persistence.*;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.security.KeyPair;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.UUID;

/**
 * Created by lesiopinheiro on 8/16/16.
 */
@Entity
@Table(name = "controladores_fisicos")
@ChangeLog
public class ControladorFisico extends Model implements Serializable {

    private static final long serialVersionUID = -1589359465988312426L;

    public static Finder<UUID, ControladorFisico> find = new Finder<UUID, ControladorFisico>(ControladorFisico.class);

    @Id
    private UUID id;

    @Column
    private String idJson;

    @ManyToOne
    @Valid
    @NotNull(message = "não pode ficar em branco")
    private Area area;

    @OneToMany(mappedBy = "controladorFisico", cascade = CascadeType.ALL)
    @Valid
    private List<VersaoControlador> versoes;

    @Column
    private StatusDevice statusDevice;

    @JsonIgnore
    @Column(columnDefinition = "TEXT")
    private String centralPrivateKey;

    @JsonIgnore
    @Column(columnDefinition = "TEXT")
    private String centralPublicKey;

    @JsonIgnore
    @Column(columnDefinition = "TEXT")
    private String controladorPublicKey;

    @Ignore
    @Column(columnDefinition = "TEXT")
    private String controladorPrivateKey;

    @JsonIgnore
    private String password;

    @Ignore
    @Column(name = "password_hash")
    private String passwordHash;


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

    @OneToOne
    @JoinColumn(name = "controlador_sincronizado_id")
    private Controlador controladorSincronizado;

    @Column
    private String marca;


    @Column
    private String modelo;


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

    public List<VersaoControlador> getVersoes() {
        return versoes;
    }

    public void setVersoes(List<VersaoControlador> versoes) {
        this.versoes = versoes;
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

    public StatusDevice getStatusDevice() {
        return statusDevice;
    }

    public void setStatusDevice(StatusDevice statusDevice) {
        this.statusDevice = statusDevice;
    }

    public void criarChaves() {
        try {
            KeyPair key = EncryptionUtil.generateRSAKey();
            this.centralPrivateKey = Hex.encodeHexString(key.getPrivate().getEncoded());
            this.centralPublicKey = Hex.encodeHexString(key.getPublic().getEncoded());

            KeyPair keyControlador = EncryptionUtil.generateRSAKey();
            this.controladorPrivateKey = Hex.encodeHexString(keyControlador.getPrivate().getEncoded());
            this.controladorPublicKey = Hex.encodeHexString(keyControlador.getPublic().getEncoded());

            this.password = UUID.randomUUID().toString();
            this.passwordHash = new MosquittoPBKDF2().createPassword(this.password);


        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
    }

    public Controlador getControladorConfiguradoOuSincronizado() {
        VersaoControlador versaoControlador = VersaoControlador.find.where().
            and(
                Expr.eq("controlador_fisico_id", this.id.toString()),
                Expr.eq("status_versao", StatusVersao.CONFIGURADO)
            ).findUnique();

        if (versaoControlador == null) {
            versaoControlador = VersaoControlador.find.where().
                and(
                    Expr.eq("controlador_fisico_id", this.id.toString()),
                    Expr.eq("status_versao", StatusVersao.SINCRONIZADO)
                ).findUnique();
        }

        return (versaoControlador != null) ? versaoControlador.getControlador() : null;
    }

    public Controlador getControladorSincronizadoOuConfigurado() {
        VersaoControlador versaoControlador = VersaoControlador.find.where().
            and(
                Expr.eq("controlador_fisico_id", this.id.toString()),
                Expr.eq("status_versao", StatusVersao.SINCRONIZADO)
            ).findUnique();

        if (versaoControlador == null) {
            versaoControlador = VersaoControlador.find.where().
                and(
                    Expr.eq("controlador_fisico_id", this.id.toString()),
                    Expr.eq("status_versao", StatusVersao.CONFIGURADO)
                ).findUnique();
        }

        return (versaoControlador != null) ? versaoControlador.getControlador() : null;
    }

    public Controlador getControladorConfiguradoOuAtivoOuEditando() {
        VersaoControlador versaoControlador = this
            .getVersoes()
            .stream()
            .filter(versaoControladorAux ->
                StatusVersao.CONFIGURADO.equals(versaoControladorAux.getStatusVersao()) ||
                    StatusVersao.SINCRONIZADO.equals(versaoControladorAux.getStatusVersao()) ||
                    StatusVersao.EDITANDO.equals(versaoControladorAux.getStatusVersao())
            ).findFirst().orElse(null);
        return (versaoControlador != null) ? versaoControlador.getControlador() : null;
    }

    public Controlador getVersaoAtualControlador() {
        VersaoControlador versaoControlador = this.getVersoes()
            .stream()
            .filter(versao -> !StatusVersao.ARQUIVADO.equals(versao .getStatusVersao()))
            .findFirst().orElse(null);

        return versaoControlador != null ? versaoControlador.getControlador() : null;
    }

    public boolean isEditando() {
        return false;
    }

    public void addVersaoControlador(VersaoControlador versaoControlador) {
        if (getVersoes() == null) {
            setVersoes(new ArrayList<VersaoControlador>());
        }
        getVersoes().add(versaoControlador);
    }

    public Area getArea() {
        return area;
    }

    public void setArea(Area area) {
        this.area = area;
    }

    public String getCentralPrivateKey() {
        return centralPrivateKey;
    }

    public String getCentralPublicKey() {
        return centralPublicKey;
    }

    public String getControladorPublicKey() {
        return controladorPublicKey;
    }

    public String getControladorPrivateKey() {
        return controladorPrivateKey;
    }

    public Controlador getControladorSincronizado() {
        return controladorSincronizado;
    }

    public static List<ControladorFisico> getControladoresPorUsuario(Usuario usuario) {
        List<ControladorFisico> controladoresFisicos = null;
        if (usuario.isRoot() || usuario.podeAcessarTodasAreas()) {
            controladoresFisicos = ControladorFisico.find.fetch("versoes").findList();
        } else if (usuario.getArea() != null) {
            controladoresFisicos = ControladorFisico.find.fetch("versoes").where().eq("area_id", usuario.getArea().getId()).findList();
        }

        return controladoresFisicos;
    }

    public static List<ControladorFisico> getControladoresSincronizadosPorUsuario(Usuario usuario) {
        Area area = null;
        if (!usuario.isRoot() && !usuario.podeAcessarTodasAreas()) {
            area = usuario.getArea();

            if (area == null) {
                return Collections.emptyList();
            }
        }

        return getControladoresSincronizadosPorArea(area);
    }

    public static List<ControladorFisico> getControladoresSincronizadosPorArea(Area area) {
        ExpressionList<ControladorFisico> query = ControladorFisico
            .find
            .fetch("controladorSincronizado")
            .fetch("controladorSincronizado.area")
            .where()
            .isNotNull("controladorSincronizado");

        if (area != null) {
            query.eq("controladorSincronizado.area", area);
        }

        return query.findList();
    }

    public void setControladorSincronizado(Controlador controladorSincronizado) {
        this.controladorSincronizado = controladorSincronizado;
    }

    public String getMarca() {
        return marca;
    }

    public void setMarca(String marca) {
        this.marca = marca;
    }

    public String getModelo() {
        return modelo;
    }

    public void setModelo(String modelo) {
        this.modelo = modelo;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getPasswordHash() {
        return passwordHash;
    }

    public void setPasswordHash(String passwordHash) {
        this.passwordHash = passwordHash;
    }
}
