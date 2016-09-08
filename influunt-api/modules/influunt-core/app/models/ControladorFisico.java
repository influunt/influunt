package models;

import com.avaje.ebean.Expr;
import com.avaje.ebean.Model;
import com.avaje.ebean.annotation.ChangeLog;
import com.avaje.ebean.annotation.CreatedTimestamp;
import com.avaje.ebean.annotation.UpdatedTimestamp;
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

    @OneToMany(mappedBy = "controladorFisico", cascade = CascadeType.ALL)
    @Valid
    private List<VersaoControlador> versoes;

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

    public Controlador getControladorAtivo() {
        VersaoControlador versaoControlador = VersaoControlador.find.fetch("controlador").where()
                .and(Expr.eq("controlador_fisico_id", this.id.toString()), Expr.eq("status_versao", StatusVersao.ATIVO)).findUnique();
        return (versaoControlador != null) ? versaoControlador.getControlador() : null;
    }

    public Controlador getControladorAtivoOuEditando() {
        VersaoControlador versaoControlador = this.getVersoes().stream().filter(versaoControladorAux ->
                StatusVersao.ATIVO.equals(versaoControladorAux.getStatusVersao()) ||
                        StatusVersao.EDITANDO.equals(versaoControladorAux.getStatusVersao())).findFirst().orElse(null);

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
}
