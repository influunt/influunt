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
import org.joda.time.DateTime;

import javax.persistence.*;
import javax.validation.constraints.AssertTrue;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.Collection;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

/**
 * Created by lesiopinheiro on 7/13/16.
 */
@Entity
@Table(name = "grupos_semaforicos_planos")
@ChangeLog
public class GrupoSemaforicoPlano extends Model implements Cloneable, Serializable {

    private static final long serialVersionUID = -8086882310576023685L;

    @Id
    private UUID id;

    @Column
    private String idJson;

    @ManyToOne
    @NotNull(message = "não pode ficar em branco")
    private GrupoSemaforico grupoSemaforico;

    @ManyToOne
    @NotNull(message = "não pode ficar em branco")
    private Plano plano;

    @Column
    private boolean ativado = true;

    @Transient
    private boolean destroy;

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

    public GrupoSemaforicoPlano() {
        super();
        this.idJson = UUID.randomUUID().toString();
    }

    public boolean isDestroy() {
        return destroy;
    }

    public void setDestroy(boolean destroy) {
        this.destroy = destroy;
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

    public GrupoSemaforico getGrupoSemaforico() {
        return grupoSemaforico;
    }

    public void setGrupoSemaforico(GrupoSemaforico grupoSemaforico) {
        this.grupoSemaforico = grupoSemaforico;
    }

    public Plano getPlano() {
        return plano;
    }

    public void setPlano(Plano plano) {
        this.plano = plano;
    }

    public boolean isAtivado() {
        return ativado;
    }

    public void setAtivado(boolean ativado) {
        this.ativado = ativado;
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

    @AssertTrue(groups = PlanosCheck.class, message = "O tempo de verde deve ser maior que o tempo de segurança configurado.")
    public boolean isRespeitaVerdesDeSeguranca() {
        if (isAtivado() && this.getPlano().isModoOperacaoVerde() && this.getGrupoSemaforico().getTempoVerdeSeguranca() != null) {
            List<EstagioPlano> listaEstagioPlanos = getPlano().getEstagiosOrdenados()
                .stream().filter(ep -> !ep.isDestroy()).collect(Collectors.toList());

            return !this.getPlano().getEstagiosPlanos().stream()
                .filter(estagioPlano -> !estagioPlano.isDestroy() && estagioPlano.getEstagio().getGruposSemaforicos()
                    .contains(this.getGrupoSemaforico()) && estagioPlano.getTempoVerdeEstagio() != null)
                .anyMatch(estagioPlano -> estagioPlano.getTempoVerdeDoGrupoSemaforico(listaEstagioPlanos, this.getGrupoSemaforico()) < this.getGrupoSemaforico().getTempoVerdeSeguranca());
        }
        return true;
    }

    @AssertTrue(groups = PlanosCheck.class, message = "O tempo de verde está menor que o tempo de segurança configurado devido à não execução do estágio dispensável")
    public boolean isRespeitaVerdesDeSegurancaSemDispensavel() {
        if (isRespeitaVerdesDeSeguranca() && isAtivado() && this.getPlano().isModoOperacaoVerde() && this.getGrupoSemaforico().getTempoVerdeSeguranca() != null) {
            List<EstagioPlano> listaEstagioPlanosSemDipensavel = getPlano().ordenarEstagiosPorPosicaoSemEstagioDispensavel()
                .stream().filter(ep -> !ep.isDestroy()).collect(Collectors.toList());

            return !this.getPlano().getEstagiosPlanosSemEstagioDispensavel().stream()
                .filter(estagioPlano -> !estagioPlano.isDestroy() && estagioPlano.getEstagio().getGruposSemaforicos()
                    .contains(this.getGrupoSemaforico()) && estagioPlano.getTempoVerdeEstagio() != null)
                .anyMatch(estagioPlano -> estagioPlano.getTempoVerdeDoGrupoSemaforico(listaEstagioPlanosSemDipensavel, this.getGrupoSemaforico()) < this.getGrupoSemaforico().getTempoVerdeSeguranca());
        }
        return true;
    }

    @AssertTrue(groups = PlanosCheck.class,
        message = "Um grupo semafórico não associado a nenhum estágio da sequência do plano deve estar apagado.")
    public boolean isGrupoApagadoSeNaoAssociado() {
        if (getPlano().isModoOperacaoVerde() && !getPlano().isManual()) {
            GrupoSemaforico grupo = getGrupoSemaforico();
            List<Estagio> estagios = grupo.getEstagiosGruposSemaforicos().stream()
                .map(EstagioGrupoSemaforico::getEstagio).collect(Collectors.toList());

            boolean isDemandaPrioritaria = estagios.stream().anyMatch(Estagio::isDemandaPrioritaria);
            if (!isDemandaPrioritaria) {
                List<EstagioPlano> eps = estagios
                    .stream()
                    .map(Estagio::getEstagiosPlanos)
                    .flatMap(Collection::stream)
                    .filter(estagioPlano -> !estagioPlano.isDestroy() && getPlano().equals(estagioPlano.getPlano()))
                    .collect(Collectors.toList());
                if (eps.isEmpty() && isAtivado()) {
                    return false;
                }
            }
        }
        return true;
    }

    @Override
    public Object clone() throws CloneNotSupportedException {
        return super.clone();
    }

}
