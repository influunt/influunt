package models;

import checks.PlanosCheck;
import com.avaje.ebean.Model;
import com.avaje.ebean.annotation.ChangeLog;
import com.avaje.ebean.annotation.CreatedTimestamp;
import com.avaje.ebean.annotation.UpdatedTimestamp;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import json.deserializers.InfluuntDateTimeDeserializer;
import json.serializers.InfluuntDateTimeSerializer;
import org.apache.commons.lang3.Range;
import org.apache.commons.math3.util.Pair;
import org.joda.time.DateTime;
import utils.RangeUtils;

import javax.persistence.*;
import javax.validation.constraints.AssertTrue;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.HashMap;
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

    @Transient
    @JsonIgnore
    private boolean destroy;

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

    public Integer getTempoVerde(int ciclo) {
        if (plano.isCicloDuplo() && ciclo % 2 != 0) {
            return getTempoVerdeCicloDuplo();
        }
        return getTempoVerde();
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
        if (!isDestroy()) {
            if (getPlano().isTempoFixoIsolado() || getPlano().isTempoFixoCoordenado()) {
                return getPlano().getAnel().getControlador().getRangeUtils().TEMPO_VERDE.contains(getTempoVerde());
            }
        }
        return true;
    }

    public void setTempoVerde(Integer tempoVerde) {
        this.tempoVerde = tempoVerde;
    }

    @AssertTrue(groups = PlanosCheck.class, message = "Tempo de verde mínimo deve estar entre {min} e {max}")
    public boolean isTempoVerdeMinimo() {
        if (getPlano().isAtuado() && !isDestroy()) {
            return getPlano().getAnel().getControlador().getRangeUtils().TEMPO_VERDE_MINIMO.contains(getTempoVerdeMinimo());
        }
        return true;
    }

    public void setTempoVerdeMinimo(Integer tempoVerdeMinimo) {
        this.tempoVerdeMinimo = tempoVerdeMinimo;
    }

    @AssertTrue(groups = PlanosCheck.class, message = "Tempo de verde máximo deve estar entre {min} e {max}")
    public boolean isTempoVerdeMaximo() {
        if (getPlano().isAtuado() && !isDestroy()) {
            return getPlano().getAnel().getControlador().getRangeUtils().TEMPO_VERDE_MAXIMO.contains(getTempoVerdeMaximo());
        }
        return true;
    }

    public void setTempoVerdeMaximo(Integer tempoVerdeMaximo) {
        this.tempoVerdeMaximo = tempoVerdeMaximo;
    }

    @AssertTrue(groups = PlanosCheck.class, message = "Tempo de verde intermediário deve estar entre {min} e {max}")
    public boolean isTempoVerdeIntermediario() {
        if (getPlano().isAtuado() && !isDestroy()) {
            return getPlano().getAnel().getControlador().getRangeUtils().TEMPO_VERDE_INTERMEDIARIO.contains(getTempoVerdeIntermediario());
        }
        return true;
    }

    public void setTempoVerdeIntermediario(Integer tempoVerdeIntermitente) {
        this.tempoVerdeIntermediario = tempoVerdeIntermitente;
    }

    @AssertTrue(groups = PlanosCheck.class, message = "Tempo de extensão de verde deve estar entre {min} e {max}")
    public boolean isTempoExtensaoVerde() {
        if (getPlano().isAtuado() && !isDestroy()) {
            return getPlano().getAnel().getControlador().getRangeUtils().TEMPO_EXTENSAO_VERDE.contains(getTempoExtensaoVerde());
        }
        return true;
    }

    public void setTempoExtensaoVerde(Double tempoExtensaoVerde) {
        this.tempoExtensaoVerde = tempoExtensaoVerde;
    }

    @AssertTrue(groups = PlanosCheck.class, message = "O tempo de verde intermediário deve estar entre os valores de verde mínimo e verde máximo.")
    public boolean isTempoVerdeIntermediarioFieldEntreMinimoMaximo() {
        if (!isDestroy()) {
            RangeUtils rangeUtils = getPlano().getAnel().getControlador().getRangeUtils();
            if (getPlano().isAtuado() &&
                rangeUtils.TEMPO_VERDE_MINIMO.contains(getTempoVerdeMinimo()) &&
                rangeUtils.TEMPO_VERDE_MAXIMO.contains(getTempoVerdeMaximo()) &&
                rangeUtils.TEMPO_VERDE_INTERMEDIARIO.contains(getTempoVerdeIntermediario())) {
                return Range.between(getTempoVerdeMinimo(), getTempoVerdeMaximo()).contains(getTempoVerdeIntermediario());
            }
        }
        return true;
    }

    @AssertTrue(groups = PlanosCheck.class, message = "O tempo de verde mínimo deve ser maior ou igual ao verde de segurança e menor que o verde máximo.")
    public boolean isTempoVerdeMinimoFieldMenorMaximo() {
        if (!isDestroy()) {
            RangeUtils rangeUtils = getPlano().getAnel().getControlador().getRangeUtils();
            if (getPlano().isAtuado() &&
                rangeUtils.TEMPO_VERDE_MINIMO.contains(getTempoVerdeMinimo()) &&
                rangeUtils.TEMPO_VERDE_MAXIMO.contains(getTempoVerdeMaximo())) {
                return getTempoVerdeMinimo() < getTempoVerdeMaximo();
            }
        }
        return true;
    }

    @AssertTrue(groups = PlanosCheck.class, message = "O tempo de estagio ultrapassa o tempo máximo de permanência.")
    public boolean isUltrapassaTempoMaximoPermanencia() {
        if (getEstagio().isTempoMaximoPermanenciaAtivado() && !isDestroy()) {
            RangeUtils rangeUtils = getPlano().getAnel().getControlador().getRangeUtils();
            int tempo;
            if (getPlano().isAtuado() && rangeUtils.TEMPO_VERDE_MAXIMO.contains(getTempoVerdeMaximo())) {
                tempo = getTempoVerdeMaximo();

                tempo = adicionaTempoEstagioDuplo(tempo, true);

                return tempo <= getEstagio().getTempoMaximoPermanencia();
            } else if ((getPlano().isTempoFixoCoordenado() || getPlano().isTempoFixoIsolado()) && rangeUtils.TEMPO_VERDE.contains(getTempoVerde())) {
                tempo = getTempoVerde();

                tempo = adicionaTempoEstagioDuplo(tempo, false);

                return tempo <= getEstagio().getTempoMaximoPermanencia();
            }
        }
        return true;
    }

    private int adicionaTempoEstagioDuplo(int tempo, boolean atuado) {
        if (getEstagioPlanoAnterior() != null && getEstagioPlanoAnterior().getEstagio().equals(getEstagio())) {
            if (atuado) {
                tempo += getEstagioPlanoAnterior().getTempoVerdeMaximo();
            } else {
                tempo += getEstagioPlanoAnterior().getTempoVerdeCicloDuplo();
            }
        }

        if (getEstagioPlanoProximo() != null && getEstagioPlanoProximo().getEstagio().equals(getEstagio())) {
            if (atuado) {
                tempo += getEstagioPlanoProximo().getTempoVerdeMaximo();
            } else {
                tempo += getEstagioPlanoProximo().getTempoVerdeCicloDuplo();
            }
        }
        return tempo;
    }

    @AssertTrue(groups = PlanosCheck.class, message = "O estágio que recebe o tempo do estágio dispensável não pode ficar em branco.")
    public boolean isEstagioQueRecebeEstagioDispensavel() {
        if (getPlano().isTempoFixoCoordenado() && isDispensavel() && !isDestroy()) {
            return getEstagioQueRecebeEstagioDispensavel() != null &&
                !getEstagioQueRecebeEstagioDispensavel().isDestroy();
        }
        return true;
    }

    public void setEstagioQueRecebeEstagioDispensavel(EstagioPlano estagioQueRecebeEstagioDispensavel) {
        this.estagioQueRecebeEstagioDispensavel = estagioQueRecebeEstagioDispensavel;
    }

    @AssertTrue(groups = PlanosCheck.class, message = "O estágio que recebe o tempo do estágio dispensável deve ser o estágio anterior ou posterior ao estágio dispensável.")
    public boolean isEstagioQueRecebeEstagioDispensavelFieldEstagioQueRecebeValido() {
        if (getEstagioQueRecebeEstagioDispensavel() != null && !isDestroy() &&
            !primeiroEstagioDaSequencia() && !ultimoEstagioDaSequencia()) {
            List<EstagioPlano> listaEstagioPlanos = getPlano().getEstagiosOrdenados();
            return getEstagioQueRecebeEstagioDispensavel()
                .getIdJson()
                .equals(getEstagioPlanoAnterior(listaEstagioPlanos).getIdJson()) ||
                getEstagioQueRecebeEstagioDispensavel().getIdJson().equals(getEstagioPlanoProximo(listaEstagioPlanos).getIdJson());
        }
        return true;
    }

    @AssertTrue(groups = PlanosCheck.class, message = "O estágio que recebe o tempo do estágio dispensável deve ser o próximo, pois esse estágio é o primeiro da sequência.")
    public boolean isEstagioQueRecebeEstagioDispensavelEProximo() {
        if (getEstagioQueRecebeEstagioDispensavel() != null && !isDestroy() && primeiroEstagioDaSequencia()) {
            List<EstagioPlano> listaEstagioPlanos = getPlano().getEstagiosOrdenados();
            return getEstagioQueRecebeEstagioDispensavel().getIdJson().equals(getEstagioPlanoProximo(listaEstagioPlanos).getIdJson());
        }
        return true;
    }

    @AssertTrue(groups = PlanosCheck.class, message = "O estágio que recebe o tempo do estágio dispensável deve ser o anterior, pois esse estágio é o último da sequência.")
    public boolean isEstagioQueRecebeEstagioDispensavelEAnterior() {
        if (getEstagioQueRecebeEstagioDispensavel() != null && !isDestroy() && ultimoEstagioDaSequencia()) {
            List<EstagioPlano> listaEstagioPlanos = getPlano().getEstagiosOrdenados();
            return getEstagioQueRecebeEstagioDispensavel().getIdJson().equals(getEstagioPlanoAnterior(listaEstagioPlanos).getIdJson());
        }
        return true;
    }

    @AssertTrue(groups = PlanosCheck.class, message = "O estágio precisa estar associado a um detector para ser dispensável.")
    public boolean isPodeSerEstagioDispensavel() {
        if (isDispensavel() && !isDestroy()) {
            return getEstagio().getDetector() != null;
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

    public EstagioPlano getEstagioPlanoAnterior() {
        List<EstagioPlano> listaEstagioPlanos = getPlano().ordenarEstagiosPorPosicaoSemEstagioDispensavel();
        return getEstagioPlanoAnterior(listaEstagioPlanos);
    }

    public EstagioPlano getEstagioPlanoAnterior(List<EstagioPlano> listaEstagioPlanos) {
        Integer index = listaEstagioPlanos.indexOf(this);

        if (index == -1) {
            return null;
        }

        if (index == 0) {
            return listaEstagioPlanos.get(listaEstagioPlanos.size() - 1);
        }
        return listaEstagioPlanos.get(index - 1);
    }

    public EstagioPlano getEstagioPlanoProximo() {
        List<EstagioPlano> listaEstagioPlanos = getPlano().ordenarEstagiosPorPosicaoSemEstagioDispensavel();
        return getEstagioPlanoProximo(listaEstagioPlanos);
    }

    public EstagioPlano getEstagioPlanoProximo(List<EstagioPlano> listaEstagioPlanos) {
        Integer index = listaEstagioPlanos.indexOf(this);

        if (index == -1) {
            return null;
        }

        if (index == listaEstagioPlanos.size() - 1) {
            return listaEstagioPlanos.get(0);
        }
        return listaEstagioPlanos.get(index + 1);
    }
    public Integer getTempoVerdeDoGrupoSemaforico(List<EstagioPlano> listaEstagioPlanos,
                                                  GrupoSemaforico grupoSemaforico) {
        return getTempoVerdeDoGrupoSemaforico(listaEstagioPlanos, grupoSemaforico, 0);
    }

    public Integer getTempoVerdeDoGrupoSemaforico(List<EstagioPlano> listaEstagioPlanos,
                                                  GrupoSemaforico grupoSemaforico,
                                                  int ciclo) {
        Integer tempoVerde = 0;
        EstagioPlano estagioPlanoAnterior = getEstagioPlanoAnterior(listaEstagioPlanos);
        while (!this.equals(estagioPlanoAnterior) && estagioPlanoAnterior.getEstagio().getGruposSemaforicos().contains(grupoSemaforico)) {
            tempoVerde += estagioPlanoAnterior.getTempoVerdeEstagio(ciclo);
            estagioPlanoAnterior = estagioPlanoAnterior.getEstagioPlanoAnterior(listaEstagioPlanos);
        }
        EstagioPlano estagioPlanoProximo = this.getEstagioPlanoProximo(listaEstagioPlanos);
        if (!estagioPlanoProximo.getEstagio().getGruposSemaforicos().contains(grupoSemaforico)) {
            Transicao transicao = grupoSemaforico.findTransicaoByOrigemDestino(this.getEstagio(), estagioPlanoProximo.getEstagio());
            if (transicao != null) {
                tempoVerde += transicao.getTempoAtrasoGrupo();
            }
        } else {
            while (!this.equals(estagioPlanoProximo) && estagioPlanoProximo.getEstagio().getGruposSemaforicos().contains(grupoSemaforico)) {
                tempoVerde += estagioPlanoProximo.getTempoVerdeEstagio(ciclo);
                estagioPlanoProximo = estagioPlanoProximo.getEstagioPlanoProximo(listaEstagioPlanos);
            }
        }

        tempoVerde += this.getTempoVerdeEstagio(ciclo);
        return tempoVerde;
    }

    public Integer getTempoVerdeEstagio() {
        return getTempoVerdeEstagio(0);
    }

    public Integer getTempoVerdeEstagio(int ciclo) {
        if (getPlano().isAtuado() && getTempoVerdeMinimo() != null) {
            if (getEstagio().getDetector().isComFalha()) {
                return getTempoVerdeIntermediario();
            }
            return getTempoVerdeMinimo();
        } else if (getPlano().isManual() && !getEstagio().isDemandaPrioritaria()) {
            return 255;
        }

        if (getPlano().isTempoFixoCoordenado() && getPlano().isCicloDuplo()) {
            return getTempoVerde(ciclo);
        }
        return getTempoVerde();
    }

    public Integer getDuracaoEstagio() {
        return getPlano().getTempoEstagio(this);
    }

    public Integer getDuracaoEstagio(int ciclo) {
        return getPlano().getTempoEstagio(this, ciclo);
    }

    public long getTempoVerdeSegurancaFaltante(EstagioPlano estagioPlanoAnterior, int contadorDeCiclos) {
        return this.getTempoMaximoVerdeSeguranca(estagioPlanoAnterior, contadorDeCiclos) * 1000L;
    }

    public long getTempoVerdeSegurancaFaltante(EstagioPlano estagioPlanoAnterior, EstagioPlano estagioPlanoProximo, int contadorDeCiclos) {
        return this.getTempoMaximoVerdeSeguranca(estagioPlanoAnterior, estagioPlanoProximo, contadorDeCiclos) * 1000L;
    }

    public Integer getTempoMaximoVerdeSeguranca(EstagioPlano estagioAnteriorPlano, int contadorDeCiclos) {
        return this.getEstagio()
            .getGruposSemaforicos()
            .stream()
            .mapToInt(grupoSemaforico -> grupoSemaforico.getTempoVerdeSegurancaFaltante(this, estagioAnteriorPlano, contadorDeCiclos))
            .max()
            .orElse(0);
    }

    public Integer getTempoMaximoVerdeSeguranca(EstagioPlano estagioPlanoAnterior, EstagioPlano estagioPlanoProximo, int contadorDeCiclos) {
        return this.getEstagio()
            .getGruposSemaforicos()
            .stream()
            .mapToInt(grupoSemaforico -> grupoSemaforico.getTempoVerdeSegurancaFaltante(this, estagioPlanoAnterior, estagioPlanoProximo, contadorDeCiclos))
            .max()
            .orElse(0);
    }

    public int getTempoVerdeEstagioComTempoDoEstagioDispensavel(HashMap<Pair<Integer, Integer>, Long> tabelaEntreVerde,
                                                                long tempoCicloDecorrido,
                                                                List<EstagioPlano> listaEstagioPlanos,
                                                                EstagioPlano estagioPlanoPassado,
                                                                int ciclo) {
        final int index = listaEstagioPlanos.indexOf(this) + 1;
        boolean primeiroCiclo = ciclo == 0;
        if (index < listaEstagioPlanos.size() && listaEstagioPlanos.get(index).getEstagio().isDemandaPrioritaria()) {
            return getTempoVerdeSeguranca();
        }
        int tempoVerdeDoEstagioDispensavel = 0;
        if (!getEstagio().isDemandaPrioritaria() && getPlano().isTempoFixoCoordenado() && !primeiroCiclo) {
            EstagioPlano estagioPlanoAnterior = getEstagioPlanoAnterior(plano.getEstagiosOrdenados());
            if (estagioPlanoAnterior.isDispensavel() &&
                !estagioPlanoAnterior.equals(estagioPlanoPassado) &&
                this.equals(estagioPlanoAnterior.getEstagioQueRecebeEstagioDispensavel()) &&
                !estagioPlanoAnterior.ultimoEstagioDaSequencia()) {

                tempoVerdeDoEstagioDispensavel = ((Long) ((getPlano().getMomentoEntradaEstagioPlano(this, ciclo) - tempoCicloDecorrido) / 1000)).intValue();
                tempoVerdeDoEstagioDispensavel += tabelaEntreVerde.get(new Pair<Integer, Integer>(estagioPlanoAnterior.getEstagio().getPosicao(),
                    this.getEstagio().getPosicao())) / 1000;

                tempoVerdeDoEstagioDispensavel -= tabelaEntreVerde.get(new Pair<Integer, Integer>(estagioPlanoPassado.getEstagio().getPosicao(),
                    this.getEstagio().getPosicao())) / 1000;

            } else if (isDispensavel()) {
                tempoVerdeDoEstagioDispensavel = ((Long) ((getPlano().getMomentoEntradaEstagioPlano(this, ciclo) - tempoCicloDecorrido) / 1000)).intValue();
            }
        }
        return getTempoVerdeEstagio(ciclo) + tempoVerdeDoEstagioDispensavel;
    }

    public boolean isDestroy() {
        return destroy;
    }

    public void setDestroy(boolean destroy) {
        this.destroy = destroy;
    }


    public Integer getTempoVerdeSeguranca() {
        return this.getEstagio()
            .getGruposSemaforicos()
            .stream()
            .mapToInt(grupoSemaforico -> grupoSemaforico.getTempoVerdeSeguranca())
            .max()
            .orElse(0);
    }

    public Integer getInicio() {
        return getPlano().getDefasagem() + getPlano().getEstagiosOrdenados().stream()
            .filter(estagioPlano -> estagioPlano.getPosicao() < getPosicao()).mapToInt(EstagioPlano::getDuracaoEstagio).sum();
    }

    public boolean estagioQueRecebeEstagioDispensavelEAnterior() {
        return getEstagioQueRecebeEstagioDispensavel().equals(getEstagioPlanoAnterior(getPlano().getEstagiosOrdenados()));
    }

    public boolean ultimoEstagioDispensavel() {
        return !getPlano().getEstagiosOrdenados()
            .stream()
            .anyMatch(estagioPlano -> estagioPlano.isDispensavel() && estagioPlano.getPosicao().compareTo(getPosicao()) > 0);
    }

    public boolean ultimoEstagioDaSequencia() {
        return getPlano().getEstagiosOrdenados().get(getPlano().getEstagiosOrdenados().size() - 1).equals(this);
    }

    public boolean primeiroEstagioDaSequencia() {
        return getPosicao().equals(1);
    }

    public Integer getTempoAdicionalCicloDuplo() {
        Plano plano = getPlano();
        if (!plano.isCicloDuplo()) {
            return 0;
        }
        final Integer tempoAdicional;
        final Integer diffTempoCiclos = plano.getTempoCicloDuplo() - plano.getTempoCiclo();
        if (primeiroEstagioDaSequencia()) {
            tempoAdicional = diffTempoCiclos - plano.getEstagiosOrdenados()
                .stream()
                .filter(ep -> !ep.getPosicao().equals(1))
                .mapToInt(EstagioPlano::getTempoAdicionalCicloDuplo)
                .sum();
        } else {
            tempoAdicional = (int) ((getTempoVerde() / (float) plano.getSomatorioTempoVerde()) * (diffTempoCiclos));
        }
        return tempoAdicional;
    }

    public Integer getTempoVerdeCicloDuplo() {
        return getTempoVerde() + getTempoAdicionalCicloDuplo();
    }
}
