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
import utils.InfluuntUtils;
import utils.RangeUtils;

import javax.persistence.*;
import javax.validation.Valid;
import javax.validation.constraints.AssertTrue;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.*;

/**
 * Created by lesiopinheiro on 7/13/16.
 */
@Entity
@Table(name = "planos")

public class Plano extends Model implements Cloneable, Serializable {

    private static final long serialVersionUID = -2879768190025745634L;

    @Id
    private UUID id;

    @Column
    private String idJson;

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
    private List<EstagioPlano> estagiosPlanos;

    @OneToMany(mappedBy = "plano", cascade = CascadeType.ALL)
    @Valid
    private List<GrupoSemaforicoPlano> gruposSemaforicosPlanos;

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

    public Plano() {
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

    public List<EstagioPlano> getEstagiosPlanos() {
        return estagiosPlanos;
    }

    public void setEstagiosPlanos(List<EstagioPlano> estagios) {
        this.estagiosPlanos = estagios;
    }

    public List<GrupoSemaforicoPlano> getGruposSemaforicosPlanos() {
        return gruposSemaforicosPlanos;
    }

    public void setGruposSemaforicosPlanos(List<GrupoSemaforicoPlano> gruposSemaforicosPlanos) {
        this.gruposSemaforicosPlanos = gruposSemaforicosPlanos;
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
        return !(this.getGruposSemaforicosPlanos().isEmpty() || this.getAnel().getGruposSemaforicos().size() != this.getGruposSemaforicosPlanos().size());
    }

    @AssertTrue(groups = PlanosCheck.class,
            message = "Deve possuir pelo menos 2 estágios configurados.")
    public boolean isQuantidadeEstagioIgualQuantidadeAnel() {
        return !(this.getEstagiosPlanos().isEmpty() || this.getEstagiosPlanos().size() < 2);
    }

    @AssertTrue(groups = PlanosCheck.class,
            message = "A sequência de estagio não é válida.")
    public boolean isPosicaoUnicaEstagio() {
        if (!this.getEstagiosPlanos().isEmpty()) {
            return !(this.getEstagiosPlanos().size() != this.getEstagiosPlanos().stream().map(estagioPlano -> estagioPlano.getPosicao()).distinct().count());
        }
        return true;
    }

    @AssertTrue(groups = PlanosCheck.class, message = "deve estar entre 30 e 255")
    public boolean isTempoCiclo() {
        if (isTempoFixoIsolado() || isTempoFixoCoordenado()) {
            return !(getTempoCiclo() == null || !RangeUtils.getInstance().TEMPO_CICLO.contains(getTempoCiclo()));
        }
        return true;
    }

    @AssertTrue(groups = PlanosCheck.class, message = "deve estar entre 0 e o tempo de ciclo")
    public boolean isDefasagem() {
        if (isTempoFixoCoordenado() && getTempoCiclo() != null) {
            return !(getDefasagem() == null || !RangeUtils.getInstance().TEMPO_DEFASAGEM.contains(getDefasagem()) || !Range.between(0, getTempoCiclo()).contains(getDefasagem()));
        }
        return true;
    }

    @AssertTrue(groups = PlanosCheck.class, message = "A soma dos tempos dos estágios ultrapassa o tempo de ciclo.")
    public boolean isUltrapassaTempoCiclo() {
        if (!this.getEstagiosPlanos().isEmpty() && isPosicaoUnicaEstagio() && (isTempoFixoIsolado() || isTempoFixoCoordenado()) && Range.between(30, 255).contains(getTempoCiclo())) {
            getEstagiosPlanos().sort((e1, e2) -> e1.getPosicao().compareTo(e2.getPosicao()));
            return getTempoCiclo() == getEstagiosPlanos().stream().mapToInt(estagioPlano -> getTempoEstagio(estagioPlano)).sum();
        }
        return true;
    }

    @AssertTrue(groups = PlanosCheck.class,
            message = "A sequência de estagio não é válida.")
    public boolean isSequenciaInvalida() {
        if (!this.getEstagiosPlanos().isEmpty() && isPosicaoUnicaEstagio()) {
            getEstagiosPlanos().sort((e1, e2) -> e1.getPosicao().compareTo(e2.getPosicao()));
            for (int i = 0; i < getEstagiosPlanos().size(); i++) {
                EstagioPlano origem = getEstagiosPlanos().get(i);
                EstagioPlano destino = null;
                if ((i + 1) < getEstagiosPlanos().size()) {
                    destino = getEstagiosPlanos().get(i + 1);
                } else {
                    destino = getEstagiosPlanos().get(0);
                }
                if (origem.getEstagio().temTransicaoProibidaParaEstagio(destino.getEstagio())) {
                    return false;
                }
            }
        }
        return true;
    }

    @AssertTrue(groups = PlanosCheck.class,
            message = "Configure um detector veicular para o modo atuado.")
    public boolean isModoOperacaoValido() {
        if (this.isAtuado()) {
            return getAnel().temDetectorVeicular();
        }
        return true;
    }

    @AssertTrue(groups = PlanosCheck.class, message = "O Plano só poderá pertencer a um agrupamento se estiver em modo Coordenado.")
    public boolean isAgrupamento() {
        return !(!this.isTempoFixoCoordenado() && this.getAgrupamento() != null);
    }


    @AssertTrue(groups = PlanosCheck.class, message = "O Tempo de ciclo deve ser simétrico ou assimétrico ao tempo de ciclo dos controladores.")
    public boolean isTempoCicloIgualOuMultiploDeTodoAgrupamento() {
        if (this.getAgrupamento() != null && this.getTempoCiclo() != null) {
            return InfluuntUtils.getInstance().multiplo(this.getTempoCicloAgrupamento(), this.getTempoCiclo());
        }
        return true;
    }

    public void addGruposSemaforicos(GrupoSemaforicoPlano grupoPlano) {
        if (getGruposSemaforicosPlanos() == null) {
            setGruposSemaforicosPlanos(new ArrayList<GrupoSemaforicoPlano>());
        }
        getGruposSemaforicosPlanos().add(grupoPlano);
    }

    public void addEstagios(EstagioPlano estagioPlano) {
        if (getEstagiosPlanos() == null) {
            setEstagiosPlanos(new ArrayList<EstagioPlano>());
        }
        getEstagiosPlanos().add(estagioPlano);
    }

    public Estagio getEstagioAnterior(Estagio estagio) {
        int posicao = getEstagiosPlanos().stream().filter(estagioPlano -> estagioPlano.getEstagio().equals(estagio)).findFirst().get().getPosicao();
        if (posicao == 1) {
            return getEstagiosPlanos().get(getEstagiosPlanos().size() - 1).getEstagio();
        } else {
            return getEstagiosPlanos().get(posicao - 2).getEstagio();
        }
    }

    public Integer getTempoEstagio(EstagioPlano estagioPlano) {
        Estagio estagio = estagioPlano.getEstagio();
        Estagio estagioAnterior = getEstagioAnterior(estagioPlano.getEstagio());
        ArrayList<Integer> totalTempoEntreverdes = new ArrayList<Integer>();
        Integer tempoEntreVerdes = 0;
        if (!estagio.equals(estagioAnterior)) {
            for (EstagioGrupoSemaforico estagioGrupoSemaforico : estagioAnterior.getEstagiosGruposSemaforicos()) {
                TabelaEntreVerdes tabelaEntreVerdes = estagioGrupoSemaforico.getGrupoSemaforico().getTabelasEntreVerdes().stream().filter(tev -> tev.getPosicao().equals(getPosicaoTabelaEntreVerde())).findFirst().orElse(null);

                Transicao transicao = estagioGrupoSemaforico.getGrupoSemaforico().findTransicaoByOrigemDestino(estagioAnterior, estagio);

                if (Objects.nonNull(tabelaEntreVerdes) && Objects.nonNull(transicao)) {
                    TabelaEntreVerdesTransicao tabelaEntreVerdesTransicao = tabelaEntreVerdes.getTabelaEntreVerdesTransicoes().stream().filter(tvt -> tvt.getTransicao().equals(transicao)).findFirst().orElse(null);
                    if (Objects.nonNull(tabelaEntreVerdesTransicao)) {
                        totalTempoEntreverdes.add(tabelaEntreVerdesTransicao.getTotalTempoEntreverdes(estagioGrupoSemaforico.getGrupoSemaforico().getTipo()));
                    }
                }
            }
            tempoEntreVerdes = Collections.max(totalTempoEntreverdes);
        }
        if (isAtuado()) {
            return tempoEntreVerdes + estagioPlano.getTempoVerdeMaximo();
        }

        return tempoEntreVerdes + estagioPlano.getTempoVerde();

    }

    public Integer getTempoCicloAgrupamento() {
        Plano plano = this.getAgrupamento().getPlanos()
                .stream()
                .filter(planoAux ->
                        planoAux.getPosicao().equals(this.getPosicao()) && !planoAux.getIdJson().equals(this.getIdJson()))
                .findFirst()
                .orElse(null);
        if (plano != null) {
            return plano.getTempoCiclo();
        }
        return 1;
    }

    public List<EstagioPlano> ordenarEstagiosPorPosicao() {
        List<EstagioPlano> listaEstagioPlanos = this.getEstagiosPlanos();
        listaEstagioPlanos.sort((anterior, proximo) -> anterior.getPosicao().compareTo(proximo.getPosicao()));
        return listaEstagioPlanos;
    }

    @Override
    public Object clone() throws CloneNotSupportedException {
        return super.clone();
    }


}
