package models;

import checks.PlanosCheck;
import com.avaje.ebean.Model;
import com.avaje.ebean.annotation.CreatedTimestamp;
import com.avaje.ebean.annotation.UpdatedTimestamp;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import json.deserializers.InfluuntDateTimeDeserializer;
import json.serializers.InfluuntDateTimeSerializer;
import org.apache.commons.lang3.Range;
import org.joda.time.DateTime;

import javax.persistence.*;
import javax.validation.Valid;
import javax.validation.constraints.AssertTrue;
import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.UUID;

/**
 * Created by lesiopinheiro on 7/13/16.
 */
@Entity
@Table(name = "planos")
public class Plano extends Model implements Cloneable {

    @Id
    private UUID id;

    @Column
    @NotNull(message = "não pode ficar em branco.")
    private Integer posicao;

    @Column
    private Integer tempoCiclo;

    @Column
    private Integer defasagem = 0;

    @ManyToOne
    private Anel anel;

    @ManyToOne
    private Agrupamento agrupamento;

    @OneToMany(mappedBy = "plano", cascade = CascadeType.ALL)
    @Valid
    private List<EstagioPlano> estagios;

    @OneToMany(mappedBy = "plano", cascade = CascadeType.ALL)
    private List<GrupoSemaforicoPlano> gruposSemaforicos;

    @Column
    @NotNull(message = "não pode ficar em branco.")
    private ModoOperacaoPlano modoOperacao;

    /**
     * Armazena qual {@link TabelaEntreVerdes} deve ser utiliada pelos grupos semafóricos a paritr da posição
     */
    @Column
    @NotNull(message = "não pode ficar em branco.")
    private Integer posicaoTabelaEntreVerde;

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

    public Integer getPosicao() {
        return posicao;
    }

    public void setPosicao(Integer posicao) {
        this.posicao = posicao;
    }

    public Integer getTempoCiclo() {
        return tempoCiclo;
    }

    public void setTempoCiclo(Integer tempoCiclo) {
        this.tempoCiclo = tempoCiclo;
    }

    public Integer getDefasagem() {
        return defasagem;
    }

    public void setDefasagem(Integer defasagem) {
        this.defasagem = defasagem;
    }

    public Anel getAnel() {
        return anel;
    }

    public void setAnel(Anel anel) {
        this.anel = anel;
    }

    public Agrupamento getAgrupamento() {
        return agrupamento;
    }

    public void setAgrupamento(Agrupamento agrupamento) {
        this.agrupamento = agrupamento;
    }

    public List<EstagioPlano> getEstagios() {
        return estagios;
    }

    public void setEstagios(List<EstagioPlano> estagios) {
        this.estagios = estagios;
    }

    public List<GrupoSemaforicoPlano> getGruposSemaforicos() {
        return gruposSemaforicos;
    }

    public void setGruposSemaforicos(List<GrupoSemaforicoPlano> gruposSemaforicos) {
        this.gruposSemaforicos = gruposSemaforicos;
    }

    public ModoOperacaoPlano getModoOperacao() {
        return modoOperacao;
    }

    public void setModoOperacao(ModoOperacaoPlano modoOperacao) {
        this.modoOperacao = modoOperacao;
    }

    public Integer getPosicaoTabelaEntreVerde() {
        return posicaoTabelaEntreVerde;
    }

    public void setPosicaoTabelaEntreVerde(Integer posicaoTableEntreVerde) {
        this.posicaoTabelaEntreVerde = posicaoTableEntreVerde;
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

    @JsonIgnore
    @Transient
    public boolean isTempoFixoIsolado() {
        return Objects.nonNull(getModoOperacao()) && ModoOperacaoPlano.TEMPO_FIXO_ISOLADO.equals(getModoOperacao());
    }

    @JsonIgnore
    @Transient
    public boolean isTempoFixoCoordenado() {
        return Objects.nonNull(getModoOperacao()) && ModoOperacaoPlano.TEMPO_FIXO_COORDENADO.equals(getModoOperacao());
    }

    @JsonIgnore
    @Transient
    public boolean isAtuado() {
        return Objects.nonNull(getModoOperacao()) && ModoOperacaoPlano.ATUADO.equals(getModoOperacao());
    }

    @JsonIgnore
    @Transient
    public boolean isIntermitente() {
        return Objects.nonNull(getModoOperacao()) && ModoOperacaoPlano.INTERMITENTE.equals(getModoOperacao());
    }

    @JsonIgnore
    @Transient
    public boolean isApagada() {
        return Objects.nonNull(getModoOperacao()) && ModoOperacaoPlano.APAGADO.equals(getModoOperacao());
    }


    @AssertTrue(groups = PlanosCheck.class,
            message = "Todos os grupos semafóricos devem possuir configurações de ativado/desativado.")
    public boolean isQuantidadeGrupoSemaforicoIgualQuantidadeAnel() {
        return !(this.getGruposSemaforicos().isEmpty() || this.getAnel().getGruposSemaforicos().size() != this.getGruposSemaforicos().size());
    }

    @AssertTrue(groups = PlanosCheck.class,
            message = "Todos os estágios devem possuir as suas configurações.")
    public boolean isQuantidadeEstagioIgualQuantidadeAnel() {
        return !(this.getEstagios().isEmpty() || this.getAnel().getEstagios().size() != this.getEstagios().size());
    }

    @AssertTrue(groups = PlanosCheck.class,
            message = "A sequência de estagio não é válida.")
    public boolean isPosicaoUnicaEstagio() {
        if (!this.getEstagios().isEmpty()) {
            return !(this.getAnel().getEstagios().size() != this.getEstagios().stream().map(estagioPlano -> estagioPlano.getPosicao()).distinct().count());
        }
        return true;
    }

    @AssertTrue(groups = PlanosCheck.class, message = "deve estar entre 30 e 255")
    public boolean isTempoCiclo() {
        if (isTempoFixoIsolado() || isTempoFixoCoordenado()) {
            return !(getTempoCiclo() == null || !Range.between(30, 255).contains(getTempoCiclo()));
        }
        return true;
    }

    @AssertTrue(groups = PlanosCheck.class, message = "A soma dos tempos dos estágios ultrapassa o tempo de ciclo.")
    public boolean isUltrapassaTempoCiclo() {
        if (!this.getEstagios().isEmpty() && isPosicaoUnicaEstagio() && (isTempoFixoIsolado() || isTempoFixoCoordenado()) && Range.between(30, 255).contains(getTempoCiclo())) {
            return getTempoCiclo() == getEstagios().stream().mapToInt(EstagioPlano::getTempoEstagio).sum();
        }
        return true;
    }

    @AssertTrue(groups = PlanosCheck.class,
            message = "A sequência de estagio não é válida.")
    public boolean isSequenciaInvalida() {
        if (!this.getEstagios().isEmpty() && isPosicaoUnicaEstagio()) {
            getEstagios().sort((e1, e2) -> e1.getPosicao().compareTo(e2.getPosicao()));
            for (int i = 0; i < getEstagios().size(); i++) {
                EstagioPlano origem = getEstagios().get(i);
                EstagioPlano destino = null;
                if ((i + 1) < getEstagios().size()) {
                    destino = getEstagios().get(i + 1);
                } else {
                    destino = getEstagios().get(0);
                }
                if (origem.getEstagio().temTransicaoProibidaComEstagio(destino.getEstagio())) {
                    return false;
                }
            }
        }
        return true;
    }

    public void addGruposSemaforicos(GrupoSemaforicoPlano grupoPlano) {
        if (getGruposSemaforicos() == null) {
            setGruposSemaforicos(new ArrayList<GrupoSemaforicoPlano>());
        }
        getGruposSemaforicos().add(grupoPlano);
    }

    public void addEstagios(EstagioPlano estagioPlano) {
        if (getEstagios() == null) {
            setEstagios(new ArrayList<EstagioPlano>());
        }
        getEstagios().add(estagioPlano);
    }

    public Estagio getEstagioAnterior(Estagio estagio) {
        getEstagios().sort((e1, e2) -> e1.getPosicao().compareTo(e2.getPosicao()));
        int posicao = getEstagios().stream().filter(estagioPlano -> estagioPlano.getEstagio().equals(estagio)).findFirst().get().getPosicao();
        if (posicao == 1) {
            return getEstagios().get(getEstagios().size() - 1).getEstagio();
        } else {
            return getEstagios().get(posicao - 2).getEstagio();
        }
    }
}
