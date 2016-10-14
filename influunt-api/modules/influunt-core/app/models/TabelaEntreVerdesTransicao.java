package models;

import checks.ControladorTabelaEntreVerdesCheck;
import com.avaje.ebean.Model;
import com.avaje.ebean.annotation.ChangeLog;
import com.avaje.ebean.annotation.CreatedTimestamp;
import com.avaje.ebean.annotation.UpdatedTimestamp;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import json.deserializers.InfluuntDateTimeDeserializer;
import json.serializers.InfluuntDateTimeSerializer;
import org.joda.time.DateTime;
import utils.RangeUtils;

import javax.persistence.*;
import javax.validation.constraints.AssertTrue;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.UUID;

/**
 * Created by lesiopinheiro on 7/6/16.
 */
@Entity
@ChangeLog
public class TabelaEntreVerdesTransicao extends Model implements Cloneable, Serializable {

    private static final long serialVersionUID = -4972858598924929065L;

    public static Finder<UUID, TabelaEntreVerdesTransicao> find = new Finder<UUID, TabelaEntreVerdesTransicao>(TabelaEntreVerdesTransicao.class);

    @Id
    private UUID id;

    @Column
    private String idJson;

    @ManyToOne(cascade = CascadeType.PERSIST)
    private TabelaEntreVerdes tabelaEntreVerdes;

    @ManyToOne
    private Transicao transicao;

    @Column
    private Integer tempoAmarelo;

    @Column
    private Integer tempoVermelhoIntermitente;

    @NotNull(message = "não pode ficar em branco")
    @Column
    private Integer tempoVermelhoLimpeza = 0;

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

    public TabelaEntreVerdesTransicao() {
        super();
        this.idJson = UUID.randomUUID().toString();
    }

    public TabelaEntreVerdesTransicao(TabelaEntreVerdes tabelaEntreVerdes, Transicao transicao) {
        super();
        this.idJson = UUID.randomUUID().toString();
        this.tabelaEntreVerdes = tabelaEntreVerdes;
        this.transicao = transicao;
    }

    public String getIdJson() {
        return idJson;
    }

    public void setIdJson(String idJson) {
        this.idJson = idJson;
    }

    @Override
    public void save() {
        getId();
        super.save();
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public TabelaEntreVerdes getTabelaEntreVerdes() {
        return tabelaEntreVerdes;
    }

    public void setTabelaEntreVerdes(TabelaEntreVerdes tabelaEntreVerdes) {
        this.tabelaEntreVerdes = tabelaEntreVerdes;
    }

    public Transicao getTransicao() {
        return transicao;
    }

    public void setTransicao(Transicao transicao) {
        this.transicao = transicao;
    }

    public Integer getTempoAmarelo() {
        return tempoAmarelo;
    }

    public Integer getTempoVermelhoIntermitente() {
        return tempoVermelhoIntermitente;
    }

    public Integer getTempoVermelhoLimpeza() {
        return tempoVermelhoLimpeza;
    }

    public void setTempoVermelhoLimpeza(Integer tempoVermelhoLimpeza) {
        this.tempoVermelhoLimpeza = tempoVermelhoLimpeza;
    }

    public Integer getTempoAtrasoGrupo() {
        if (getTransicao() != null && getTransicao().getAtrasoDeGrupo() != null) {
            return getTransicao().getAtrasoDeGrupo().getAtrasoDeGrupo();
        }
        return 0;
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

    @AssertTrue(groups = ControladorTabelaEntreVerdesCheck.class, message = "não pode ficar em branco")
    public boolean isTempoAmarelo() {
        if (getTransicao().isPerdaDePassagem() && getTransicao().getGrupoSemaforico().isVeicular()) {
            return getTempoAmarelo() != null;
        }
        return true;
    }

    public void setTempoAmarelo(Integer tempoAmarelo) {
        this.tempoAmarelo = tempoAmarelo;
    }

    @AssertTrue(groups = ControladorTabelaEntreVerdesCheck.class, message = "deve estar entre {min} e {max}")
    public boolean isTempoVermelhoLimpezaFieldVeicular() {
        if (getTransicao().isPerdaDePassagem() && getTransicao().getGrupoSemaforico().isVeicular() && getTempoVermelhoLimpeza() != null) {
            return RangeUtils.getInstance().TEMPO_VERMELHO_LIMPEZA_VEICULAR.contains(getTempoVermelhoLimpeza());
        }
        return true;
    }

    @AssertTrue(groups = ControladorTabelaEntreVerdesCheck.class, message = "deve estar entre {min} e {max}")
    public boolean isTempoVermelhoLimpezaFieldPedestre() {
        if (getTransicao().isPerdaDePassagem() && getTransicao().getGrupoSemaforico().isPedestre() && getTempoVermelhoLimpeza() != null) {
            return RangeUtils.getInstance().TEMPO_VERMELHO_LIMPEZA_PEDESTRE.contains(getTempoVermelhoLimpeza());
        }
        return true;
    }

    @AssertTrue(groups = ControladorTabelaEntreVerdesCheck.class, message = "não pode ficar em branco")
    public boolean isTempoVermelhoIntermitente() {
        if (getTransicao().isPerdaDePassagem() && getTransicao().getGrupoSemaforico().isPedestre()) {
            return getTempoVermelhoIntermitente() != null;
        }
        return true;
    }

    public void setTempoVermelhoIntermitente(Integer tempoVermelhoIntermitente) {
        this.tempoVermelhoIntermitente = tempoVermelhoIntermitente;
    }

    @AssertTrue(groups = ControladorTabelaEntreVerdesCheck.class, message = "deve estar entre {min} e {max}")
    public boolean isTempoVermelhoIntermitenteOk() {
        if (getTransicao().isPerdaDePassagem() && getTransicao().getGrupoSemaforico().isPedestre()) {
            return getTempoVermelhoIntermitente() != null && RangeUtils.getInstance().TEMPO_VERMELHO_INTERMITENTE.contains(getTempoVermelhoIntermitente());
        }
        return true;
    }

    @AssertTrue(message = "deve estar entre {min} e {max}")
    public boolean isTempoAmareloOk() {
        if (getTransicao().isPerdaDePassagem() && getTransicao().getGrupoSemaforico().isVeicular() && getTempoAmarelo() != null) {
            return RangeUtils.getInstance().TEMPO_AMARELO.contains(getTempoAmarelo());
        }
        return true;
    }

    @Override
    public Object clone() throws CloneNotSupportedException {
        return super.clone();
    }

    public Integer getTotalTempoEntreverdes(TipoGrupoSemaforico tipoGrupoSemaforico) {
        if (TipoGrupoSemaforico.PEDESTRE.equals(tipoGrupoSemaforico)) {
            return getTempoVermelhoIntermitente() + getTempoVermelhoLimpeza();
        } else {
            return getTempoAmarelo() + getTempoVermelhoLimpeza();
        }
    }
}
