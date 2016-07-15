package models;

import checks.PlanosCheck;
import com.avaje.ebean.Model;
import com.avaje.ebean.annotation.CreatedTimestamp;
import com.avaje.ebean.annotation.UpdatedTimestamp;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import json.deserializers.InfluuntDateTimeDeserializer;
import json.serializers.InfluuntDateTimeSerializer;
import org.apache.commons.lang.ObjectUtils;
import org.apache.commons.lang3.Range;
import org.joda.time.DateTime;
import utils.RangeUtils;

import javax.persistence.*;
import javax.validation.constraints.AssertTrue;
import javax.validation.constraints.NotNull;
import java.util.*;

/**
 * Created by lesiopinheiro on 7/13/16.
 */
@Entity
@Table(name = "estagios_planos")
public class EstagioPlano extends Model {

    @Id
    private UUID id;

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

    public void setTempoVerde(Integer tempoVerde) {
        this.tempoVerde = tempoVerde;
    }

    public Integer getTempoVerdeMinimo() {
        return tempoVerdeMinimo;
    }

    public void setTempoVerdeMinimo(Integer tempoVerdeMinimo) {
        this.tempoVerdeMinimo = tempoVerdeMinimo;
    }

    public Integer getTempoVerdeMaximo() {
        return tempoVerdeMaximo;
    }

    public void setTempoVerdeMaximo(Integer tempoVerdeMaximo) {
        this.tempoVerdeMaximo = tempoVerdeMaximo;
    }

    public Integer getTempoVerdeIntermediario() {
        return tempoVerdeIntermediario;
    }

    public void setTempoVerdeIntermediario(Integer tempoVerdeIntermitente) {
        this.tempoVerdeIntermediario = tempoVerdeIntermitente;
    }

    public Double getTempoExtensaoVerde() {
        return tempoExtensaoVerde;
    }

    public void setTempoExtensaoVerde(Double tempoExtensaoVerde) {
        this.tempoExtensaoVerde = tempoExtensaoVerde;
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

    @AssertTrue(groups = PlanosCheck.class, message = "deve estar entre 10 e 255")
    public boolean isTempoVerdeMinimo() {
        if (getPlano().isAtuado()) {
            return RangeUtils.getInstance().TEMPO_VERDE_MINIMO.contains(getTempoVerdeMinimo());
        }
        return true;
    }

    @AssertTrue(groups = PlanosCheck.class, message = "deve estar entre 10 e 255")
    public boolean isTempoVerdeMaximo() {
        if (getPlano().isAtuado()) {
            return RangeUtils.getInstance().TEMPO_VERDE_MAXIMO.contains(getTempoVerdeMaximo());
        }
        return true;
    }

    @AssertTrue(groups = PlanosCheck.class, message = "deve estar entre 10 e 255")
    public boolean isTempoVerdeIntermediario() {
        if (getPlano().isAtuado()) {
            return RangeUtils.getInstance().TEMPO_VERDE_INTERMEDIARIO.contains(getTempoVerdeIntermediario());
        }
        return true;
    }

    @AssertTrue(groups = PlanosCheck.class, message = "deve estar entre 1 e 10")
    public boolean isTempoExtensaoVerde() {
        if (getPlano().isAtuado()) {
            return RangeUtils.getInstance().TEMPO_EXTENSAO_VERDE.contains(getTempoExtensaoVerde());
        }
        return true;
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

    public Integer getTempoEstagio() {
        Estagio estagioAnterior = getPlano().getEstagioAnterior(getEstagio());
        ArrayList<Integer> totalTempoEntreverdes = new ArrayList<Integer>();
        for (EstagioGrupoSemaforico estagioGrupoSemaforico : estagioAnterior.getEstagiosGruposSemaforicos()) {
            TabelaEntreVerdes tabelaEntreVerdes = estagioGrupoSemaforico.getGrupoSemaforico().getTabelasEntreVerdes().stream().filter(tev -> tev.getPosicao().equals(getPlano().getPosicaoTabelaEntreVerde())).findFirst().orElse(null);

            Transicao transicao = estagioGrupoSemaforico.getGrupoSemaforico().findTransicaoByOrigemDestino(estagioAnterior, getEstagio());

            if (Objects.nonNull(tabelaEntreVerdes) && Objects.nonNull(transicao)) {
                TabelaEntreVerdesTransicao tabelaEntreVerdesTransicao = tabelaEntreVerdes.getTransicoes().stream().filter(tvt -> tvt.getTransicao().equals(transicao)).findFirst().orElse(null);
                if (Objects.nonNull(tabelaEntreVerdesTransicao)) {
                    totalTempoEntreverdes.add(tabelaEntreVerdesTransicao.getTotalTempoEntreverdes(estagioGrupoSemaforico.getGrupoSemaforico().getTipo()));
                }
            }
        }

        if(getPlano().isAtuado()) {
            return Collections.max(totalTempoEntreverdes) + getTempoVerdeMaximo();
        }

        return Collections.max(totalTempoEntreverdes) + getTempoVerde();
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
}
