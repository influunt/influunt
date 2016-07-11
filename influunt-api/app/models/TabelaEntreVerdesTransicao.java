package models;

import checks.ControladorTabelaEntreVerdesCheck;
import com.avaje.ebean.Model;
import com.avaje.ebean.annotation.CreatedTimestamp;
import com.avaje.ebean.annotation.UpdatedTimestamp;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import json.deserializers2.InfluuntDateTimeDeserializer;
import json.deserializers2.TabelaEntreVerdesTransicaoDeserializer;
import json.serializers2.InfluuntDateTimeSerializer;
import json.serializers2.TabelaEntreVerdesTransicaoSerializer;
import org.hibernate.validator.constraints.Range;
import org.joda.time.DateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.validation.constraints.AssertTrue;
import javax.validation.constraints.NotNull;
import java.util.UUID;

/**
 * Created by lesiopinheiro on 7/6/16.
 */
@Entity
@JsonSerialize(using = TabelaEntreVerdesTransicaoSerializer.class)
@JsonDeserialize(using = TabelaEntreVerdesTransicaoDeserializer.class)
public class TabelaEntreVerdesTransicao extends Model implements Cloneable {

    @Id
    private UUID id;

    @ManyToOne
    private TabelaEntreVerdes tabelaEntreVerdes;

    @ManyToOne
    private Transicao transicao;

    @Range(min = 3, max = 5, message = "deve estar entre {min} e {max}")
    @Column
    private Integer tempoAmarelo;

    @Range(min = 3, max = 32, message = "deve estar entre {min} e {max}")
    @Column
    private Integer tempoVermelhoIntermitente;

    @NotNull(message = "não pode ficar em branco")
    @Column
    private Integer tempoVermelhoLimpeza;

    @Range(min = 0, max = 20, message = "deve estar entre {min} e {max}")
    @NotNull(message = "não pode ficar em branco")
    @Column
    private Integer tempoAtrasoGrupo;

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

    }

    @Override
    public void save() {
        getId();
        super.save();
    }

    public TabelaEntreVerdesTransicao(TabelaEntreVerdes tabelaEntreVerdes, Transicao transicao) {
        super();
        this.tabelaEntreVerdes = tabelaEntreVerdes;
        this.transicao = transicao;
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

    public void setTempoAmarelo(Integer tempoAmarelo) {
        this.tempoAmarelo = tempoAmarelo;
    }

    public Integer getTempoVermelhoIntermitente() {
        return tempoVermelhoIntermitente;
    }

    public void setTempoVermelhoIntermitente(Integer tempoVermelhoIntermitente) {
        this.tempoVermelhoIntermitente = tempoVermelhoIntermitente;
    }

    public Integer getTempoVermelhoLimpeza() {
        return tempoVermelhoLimpeza;
    }

    public void setTempoVermelhoLimpeza(Integer tempoVermelhoLimpeza) {
        this.tempoVermelhoLimpeza = tempoVermelhoLimpeza;
    }

    public Integer getTempoAtrasoGrupo() {
        return tempoAtrasoGrupo;
    }

    public void setTempoAtrasoGrupo(Integer tempoAtrasoGrupo) {
        this.tempoAtrasoGrupo = tempoAtrasoGrupo;
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
        if (getTransicao().getGrupoSemaforico().isVeicular()) {
            return getTempoAmarelo() != null;
        }
        return true;
    }

    @AssertTrue(groups = ControladorTabelaEntreVerdesCheck.class, message = "deve estar entre 0 e 7")
    public boolean isTempoVermelhoLimpezaFieldVeicular() {
        if (getTransicao().getGrupoSemaforico().isVeicular() && getTempoVermelhoLimpeza() != null) {
            return org.apache.commons.lang3.Range.between(0, 7).contains(getTempoVermelhoLimpeza());
        }
        return true;
    }

    @AssertTrue(groups = ControladorTabelaEntreVerdesCheck.class, message = "deve estar entre 0 e 5")
    public boolean isTempoVermelhoLimpezaFieldPedestre() {
        if (getTransicao().getGrupoSemaforico().isPedestre() && getTempoVermelhoLimpeza() != null) {
            return org.apache.commons.lang3.Range.between(0, 5).contains(getTempoVermelhoLimpeza());
        }
        return true;
    }

    @AssertTrue(groups = ControladorTabelaEntreVerdesCheck.class, message = "não pode ficar em branco")
    public boolean isTempoVermelhoIntermitente() {
        if (getTransicao().getGrupoSemaforico().isPedestre()) {
            return getTempoVermelhoIntermitente() != null;
        }
        return true;
    }

    @Override
    public Object clone() throws CloneNotSupportedException {
        return super.clone();
    }
}
