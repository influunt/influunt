package models;

import checks.PlanosCheck;
import com.avaje.ebean.Model;
import com.avaje.ebean.annotation.ChangeLog;
import com.avaje.ebean.annotation.CreatedTimestamp;
import com.avaje.ebean.annotation.UpdatedTimestamp;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import json.deserializers.InfluuntDateTimeDeserializer;
import json.serializers.InfluuntDateTimeSerializer;
import org.apache.commons.lang3.Range;
import org.joda.time.DateTime;
import utils.RangeUtils;

import javax.persistence.*;
import javax.validation.constraints.AssertTrue;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.List;
import java.util.UUID;

/**
 * Created by lesiopinheiro on 7/13/16.
 */
@Entity
@Table(name = "estagios_planos")
@ChangeLog
public class EstagioPlano extends Model implements Cloneable, Serializable {

    private static final long serialVersionUID = -7948479324460591011L;

    @Id
    private UUID id;

    @Column
    private String idJson;

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

    @ManyToOne
    @Column
    private EstagioPlano estagioQueRecebeEstagioDispensavel;

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
        this.idJson = UUID.randomUUID().toString();
    }

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

    public EstagioPlano getEstagioQueRecebeEstagioDispensavel() {
        return estagioQueRecebeEstagioDispensavel;
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

    @AssertTrue(groups = PlanosCheck.class, message = "Tempo de verde deve estar entre {min} e {max}")
    public boolean isTempoVerde() {
        if (getPlano().isTempoFixoIsolado() || getPlano().isTempoFixoCoordenado()) {
            return RangeUtils.getInstance().TEMPO_VERDE.contains(getTempoVerde());
        }
        return true;
    }

    public void setTempoVerde(Integer tempoVerde) {
        this.tempoVerde = tempoVerde;
    }

    @AssertTrue(groups = PlanosCheck.class, message = "Tempo de verde mínimo deve estar entre {min} e {max}")
    public boolean isTempoVerdeMinimo() {
        if (getPlano().isAtuado()) {
            return RangeUtils.getInstance().TEMPO_VERDE_MINIMO.contains(getTempoVerdeMinimo());
        }
        return true;
    }

    public void setTempoVerdeMinimo(Integer tempoVerdeMinimo) {
        this.tempoVerdeMinimo = tempoVerdeMinimo;
    }

    @AssertTrue(groups = PlanosCheck.class, message = "Tempo de verde máximo deve estar entre {min} e {max}")
    public boolean isTempoVerdeMaximo() {
        if (getPlano().isAtuado()) {
            return RangeUtils.getInstance().TEMPO_VERDE_MAXIMO.contains(getTempoVerdeMaximo());
        }
        return true;
    }

    public void setTempoVerdeMaximo(Integer tempoVerdeMaximo) {
        this.tempoVerdeMaximo = tempoVerdeMaximo;
    }

    @AssertTrue(groups = PlanosCheck.class, message = "Tempo de verde intermediário deve estar entre {min} e {max}")
    public boolean isTempoVerdeIntermediario() {
        if (getPlano().isAtuado()) {
            return RangeUtils.getInstance().TEMPO_VERDE_INTERMEDIARIO.contains(getTempoVerdeIntermediario());
        }
        return true;
    }

    public void setTempoVerdeIntermediario(Integer tempoVerdeIntermitente) {
        this.tempoVerdeIntermediario = tempoVerdeIntermitente;
    }

    @AssertTrue(groups = PlanosCheck.class, message = "Tempo de extensão de verde deve estar entre {min} e {max}")
    public boolean isTempoExtensaoVerde() {
        if (getPlano().isAtuado()) {
            return RangeUtils.getInstance().TEMPO_EXTENSAO_VERDE.contains(getTempoExtensaoVerde());
        }
        return true;
    }

    public void setTempoExtensaoVerde(Double tempoExtensaoVerde) {
        this.tempoExtensaoVerde = tempoExtensaoVerde;
    }

    @AssertTrue(groups = PlanosCheck.class, message = "O tempo de verde intermediário deve estar entre os valores de verde mínimo e verde máximo.")
    public boolean isTempoVerdeIntermediarioFieldEntreMinimoMaximo() {
        RangeUtils rangeUtils = RangeUtils.getInstance();
        if (getPlano().isAtuado() &&
                rangeUtils.TEMPO_VERDE_MINIMO.contains(getTempoVerdeMinimo()) &&
                rangeUtils.TEMPO_VERDE_MAXIMO.contains(getTempoVerdeMaximo()) &&
                rangeUtils.TEMPO_VERDE_INTERMEDIARIO.contains(getTempoVerdeIntermediario())) {
            return Range.between(getTempoVerdeMinimo(), getTempoVerdeMaximo()).contains(getTempoVerdeIntermediario());
        }
        return true;
    }

    @AssertTrue(groups = PlanosCheck.class, message = "O tempo de estagio ultrapassa o tempo máximo de permanência.")
    public boolean isUltrapassaTempoMaximoPermanencia() {
        if (getEstagio().getTempoMaximoPermanenciaAtivado()) {
            RangeUtils rangeUtils = RangeUtils.getInstance();
            if (getPlano().isAtuado() && rangeUtils.TEMPO_VERDE_MAXIMO.contains(getTempoVerdeMaximo())) {
                return getTempoVerdeMaximo() <= getEstagio().getTempoMaximoPermanencia();
            } else if ((getPlano().isTempoFixoCoordenado() || getPlano().isTempoFixoIsolado()) && rangeUtils.TEMPO_VERDE.contains(getTempoVerde())) {
                return getTempoVerde() <= getEstagio().getTempoMaximoPermanencia();
            }
        }
        return true;
    }

    @AssertTrue(groups = PlanosCheck.class, message = "não pode ficar em branco.")
    public boolean isEstagioQueRecebeEstagioDispensavel() {
        if (getPlano().isTempoFixoCoordenado() && isDispensavel()) {
            return getEstagioQueRecebeEstagioDispensavel() != null;
        }
        return true;
    }

    public void setEstagioQueRecebeEstagioDispensavel(EstagioPlano estagioQueRecebeEstagioDispensavel) {
        this.estagioQueRecebeEstagioDispensavel = estagioQueRecebeEstagioDispensavel;
    }

    @AssertTrue(groups = PlanosCheck.class, message = "deve ser o estágio anterior ou posterior ao estágio dispensável.")
    public boolean isEstagioQueRecebeEstagioDispensavelFieldEstagioQueRecebeValido() {
        if (getEstagioQueRecebeEstagioDispensavel() != null) {
            List<EstagioPlano> listaEstagioPlanos = getPlano().ordenarEstagiosPorPosicao();
            return getEstagioQueRecebeEstagioDispensavel().getIdJson().equals(getEstagioPlanoAnterior(listaEstagioPlanos).getIdJson()) || getEstagioQueRecebeEstagioDispensavel().getIdJson().equals(getEstagioPlanoProximo(listaEstagioPlanos).getIdJson());
        }
        return true;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        EstagioPlano that = (EstagioPlano) o;

        return id != null ? id.equals(that.id) : idJson != null ? idJson.equals(that.idJson) : that.id == null;

    }

    @Override
    public int hashCode() {
        return id != null ? id.hashCode() : 0;
    }

    @Override
    public Object clone() throws CloneNotSupportedException {
        return super.clone();
    }

    @Override
    public String toString() {
        return "EstagioPlano{" +
                "posicao=" + posicao +
                ", estagio=E" + estagio.getPosicao() +
                '}';
    }

    public EstagioPlano getEstagioPlanoAnterior(List<EstagioPlano> listaEstagioPlanos) {
        Integer index = listaEstagioPlanos.indexOf(this);
        if (index == 0) {
            return listaEstagioPlanos.get(listaEstagioPlanos.size() - 1);
        }
        return listaEstagioPlanos.get(index - 1);
    }

    public EstagioPlano getEstagioPlanoProximo(List<EstagioPlano> listaEstagioPlanos) {
        Integer index = listaEstagioPlanos.indexOf(this);
        if (index == listaEstagioPlanos.size() - 1) {
            return listaEstagioPlanos.get(0);
        }
        return listaEstagioPlanos.get(index + 1);
    }

    public Integer getTempoVerdeDoGrupoSemaforico(List<EstagioPlano> listaEstagioPlanos, GrupoSemaforico grupoSemaforico) {
        Integer tempoVerde = 0;
        EstagioPlano estagioPlanoAnterior = getEstagioPlanoAnterior(listaEstagioPlanos);
        while (!this.equals(estagioPlanoAnterior) && estagioPlanoAnterior.getEstagio().getGruposSemaforicos().contains(grupoSemaforico)) {
            tempoVerde += estagioPlanoAnterior.getTempoVerdeEstagio();
            estagioPlanoAnterior = estagioPlanoAnterior.getEstagioPlanoAnterior(listaEstagioPlanos);
        }
        EstagioPlano estagioPlanoProximo = this.getEstagioPlanoProximo(listaEstagioPlanos);
        while (!this.equals(estagioPlanoProximo) && estagioPlanoProximo.getEstagio().getGruposSemaforicos().contains(grupoSemaforico)) {
            tempoVerde += estagioPlanoProximo.getTempoVerdeEstagio();
            estagioPlanoProximo = estagioPlanoProximo.getEstagioPlanoProximo(listaEstagioPlanos);
        }
        tempoVerde += this.getTempoVerdeEstagio();
        return tempoVerde;
    }

    //TODO: Verificar como é feito a configuração de verde no caso de estagio de pedestre no modo atuado
    public Integer getTempoVerdeEstagio() {
        if (this.getPlano().isAtuado() && this.getTempoVerdeMinimo() != null) {
            return this.getTempoVerdeMinimo();
        }
        return this.getTempoVerde();
    }

    public Integer getDuracaoEstagio() {
        return getPlano().getTempoEstagio(this);
    }

    public long getTempoVerdeSegurancaFaltante(EstagioPlano estagioPlanoAnterior, long contadorIntervalo) {
        final long tempoVerdeSeguranca = this.getTempoMaximoVerdeSeguranca(estagioPlanoAnterior) * 1000L;

        return Math.max(0, tempoVerdeSeguranca - contadorIntervalo);
    }

    public Integer getTempoMaximoVerdeSeguranca(EstagioPlano estagioAnteriorPlano) {
        return this.getEstagio()
                .getGruposSemaforicos()
                .stream()
                .map(grupoSemaforico -> grupoSemaforico.getTempoVerdeSegurancaFaltante(this, estagioAnteriorPlano))
                .max(Integer::max)
                .get();
    }
}
