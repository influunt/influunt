package models;

import checks.PlanosCheck;
import com.avaje.ebean.Model;
import com.avaje.ebean.annotation.CreatedTimestamp;
import com.avaje.ebean.annotation.UpdatedTimestamp;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import json.deserializers.InfluuntDateTimeDeserializer;
import json.serializers.EstagioPlanoSerializer;
import json.serializers.InfluuntDateTimeSerializer;
import org.apache.commons.lang3.Range;
import org.joda.time.DateTime;
import utils.RangeUtils;

import javax.persistence.*;
import javax.validation.constraints.AssertTrue;
import javax.validation.constraints.NotNull;
import java.util.UUID;

/**
 * Created by lesiopinheiro on 7/13/16.
 */
@Entity
@Table(name = "estagios_planos")
@JsonSerialize(using = EstagioPlanoSerializer.class)
public class EstagioPlano extends Model implements Cloneable {

    @Id
    private UUID id;

    @Column
    private String idJson;

    public String getIdJson() {
        return idJson;
    }

    public void setIdJson(String idJson) {
        this.idJson = idJson;
    }

    @ManyToOne
    @NotNull
    private Estagio estagio;

    @ManyToOne
    @NotNull
    private Plano plano;

    @Column
    private Integer posicao;

    @Column
    private Integer tempoVerde;

    @Column
    private Integer tempoVerdeMinimo;

    @Column
    private Integer tempoVerdeMaximo;

    @Column
    private Integer tempoVerdeIntermediario;

    @Column
    private Double tempoExtensaoVerde;

    @Column
    private boolean dispensavel;

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

    public EstagioPlano() {
        super();
        this.setIdJson(UUID.randomUUID().toString());
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public Estagio getEstagio() {
        return estagio;
    }

    public void setEstagio(Estagio estagio) {
        this.estagio = estagio;
    }

    public Plano getPlano() {
        return plano;
    }

    public void setPlano(Plano plano) {
        this.plano = plano;
    }

    public Integer getPosicao() {
        return posicao;
    }

    public void setPosicao(Integer posicao) {
        this.posicao = posicao;
    }

    public Integer getTempoVerde() {
        return tempoVerde;
    }

    public Integer getTempoVerdeMinimo() {
        return tempoVerdeMinimo;
    }

    public Integer getTempoVerdeMaximo() {
        return tempoVerdeMaximo;
    }

    public Integer getTempoVerdeIntermediario() {
        return tempoVerdeIntermediario;
    }

    public Double getTempoExtensaoVerde() {
        return tempoExtensaoVerde;
    }

    public boolean isDispensavel() {
        return dispensavel;
    }

    public void setDispensavel(boolean dispensavel) {
        this.dispensavel = dispensavel;
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

    @AssertTrue(groups = PlanosCheck.class, message = "deve estar entre 1 e 255")
    public boolean isTempoVerde() {
        if (getPlano().isTempoFixoIsolado() || getPlano().isTempoFixoCoordenado()) {
            return RangeUtils.getInstance().TEMPO_VERDE.contains(getTempoVerde());
        }
        return true;
    }

    public void setTempoVerde(Integer tempoVerde) {
        this.tempoVerde = tempoVerde;
    }

    @AssertTrue(groups = PlanosCheck.class, message = "deve estar entre 10 e 255")
    public boolean isTempoVerdeMinimo() {
        if (getPlano().isAtuado()) {
            return RangeUtils.getInstance().TEMPO_VERDE_MINIMO.contains(getTempoVerdeMinimo());
        }
        return true;
    }

    public void setTempoVerdeMinimo(Integer tempoVerdeMinimo) {
        this.tempoVerdeMinimo = tempoVerdeMinimo;
    }

    @AssertTrue(groups = PlanosCheck.class, message = "deve estar entre 10 e 255")
    public boolean isTempoVerdeMaximo() {
        if (getPlano().isAtuado()) {
            return RangeUtils.getInstance().TEMPO_VERDE_MAXIMO.contains(getTempoVerdeMaximo());
        }
        return true;
    }

    public void setTempoVerdeMaximo(Integer tempoVerdeMaximo) {
        this.tempoVerdeMaximo = tempoVerdeMaximo;
    }

    @AssertTrue(groups = PlanosCheck.class, message = "deve estar entre 10 e 255")
    public boolean isTempoVerdeIntermediario() {
        if (getPlano().isAtuado()) {
            return RangeUtils.getInstance().TEMPO_VERDE_INTERMEDIARIO.contains(getTempoVerdeIntermediario());
        }
        return true;
    }

    public void setTempoVerdeIntermediario(Integer tempoVerdeIntermitente) {
        this.tempoVerdeIntermediario = tempoVerdeIntermitente;
    }

    @AssertTrue(groups = PlanosCheck.class, message = "deve estar entre 1 e 10")
    public boolean isTempoExtensaoVerde() {
        if (getPlano().isAtuado()) {
            return RangeUtils.getInstance().TEMPO_EXTENSAO_VERDE.contains(getTempoExtensaoVerde());
        }
        return true;
    }

    public void setTempoExtensaoVerde(Double tempoExtensaoVerde) {
        this.tempoExtensaoVerde = tempoExtensaoVerde;
    }

    @AssertTrue(groups = PlanosCheck.class, message = "O tempo de verde intermediaria deve estar entre o valor de verde minimo e verde maximo.")
    public boolean isTempoVerdeIntermediarioFieldEntreMinimoMaximo() {
        if (getPlano().isAtuado() && RangeUtils.getInstance().TEMPO_VERDE_MINIMO.contains(getTempoVerdeMinimo()) && RangeUtils.TEMPO_VERDE_MAXIMO.contains(getTempoVerdeMaximo()) && RangeUtils.TEMPO_VERDE_INTERMEDIARIO.contains(getTempoVerdeIntermediario())) {
            return Range.between(getTempoVerdeMinimo(), getTempoVerdeMaximo()).contains(getTempoVerdeIntermediario());
        }
        return true;
    }

    @AssertTrue(groups = PlanosCheck.class, message = "O tempo de estagio ultrapassa o tempo maximo de permanencia.")
    public boolean isUltrapassaTempoMaximoPermanencia() {
        if (getEstagio().getTempoMaximoPermanenciaAtivado()) {
            if (getPlano().isAtuado() && RangeUtils.getInstance().TEMPO_VERDE_MAXIMO.contains(getTempoVerdeMaximo())) {
                return getTempoVerdeMaximo() <= getEstagio().getTempoMaximoPermanencia();
            } else if ((getPlano().isTempoFixoCoordenado() || getPlano().isTempoFixoIsolado()) && RangeUtils.TEMPO_VERDE.contains(getTempoVerde())) {
                return getTempoVerde() <= getEstagio().getTempoMaximoPermanencia();
            }
        }
        return true;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        EstagioPlano that = (EstagioPlano) o;

        return id != null ? id.equals(that.id) : that.id == null;

    }

    @Override
    public int hashCode() {
        return id != null ? id.hashCode() : 0;
    }

    @Override
    public Object clone() throws CloneNotSupportedException {
        return super.clone();
    }
}
