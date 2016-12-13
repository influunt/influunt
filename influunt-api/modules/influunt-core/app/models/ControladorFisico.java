package models;

import com.avaje.ebean.Expr;
import com.avaje.ebean.Model;
import com.avaje.ebean.annotation.ChangeLog;
import com.avaje.ebean.annotation.CreatedTimestamp;
import com.avaje.ebean.annotation.UpdatedTimestamp;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import jdk.nashorn.internal.ir.annotations.Ignore;
import json.deserializers.InfluuntDateTimeDeserializer;
import json.serializers.InfluuntDateTimeSerializer;
import org.apache.commons.codec.binary.Hex;
import org.joda.time.DateTime;
import utils.EncryptionUtil;

import javax.persistence.*;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.security.KeyPair;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
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

    @Ignore
    @Column(columnDefinition = "TEXT")
    private String centralPrivateKey;

    @Ignore
    @Column(columnDefinition = "TEXT")
    private String centralPublicKey;

    @Ignore
    @Column(columnDefinition = "TEXT")
    private String controladorPublicKey;

    @Ignore
    @Column(columnDefinition = "TEXT")
    private String controladorPrivateKey;

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
                StatusVersao.EM_CONFIGURACAO.equals(versaoControladorAux.getStatusVersao()) ||
                    StatusVersao.CONFIGURADO.equals(versaoControladorAux.getStatusVersao()) ||
                    StatusVersao.EDITANDO.equals(versaoControladorAux.getStatusVersao())
            ).findFirst().orElse(null);
        return (versaoControlador != null) ? versaoControlador.getControlador() : null;
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
}
