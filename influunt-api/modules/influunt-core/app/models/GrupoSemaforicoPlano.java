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
import javax.validation.Valid;
import javax.validation.constraints.AssertTrue;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

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

    @OneToMany(cascade = CascadeType.ALL)
    @NotNull(message = "não pode ficar em branco")
    @Valid
    private List<Intervalo> intervalos;


    @ManyToOne
    @NotNull(message = "não pode ficar em branco")
    private Plano plano;

    @Column
    private boolean ativado = true;

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

    public List<Intervalo> getIntervalos() {
        return intervalos;
    }

    public void setIntervalos(List<Intervalo> intervalos) {
        this.intervalos = intervalos;
    }

    @AssertTrue(groups = PlanosCheck.class, message = "O tempo de verde está menor que o tempo de segurança configurado.")
    public boolean isRespeitaVerdesDeSeguranca() {
        if (isAtivado() && this.getPlano().isModoOperacaoVerde() && this.getGrupoSemaforico().getTempoVerdeSeguranca() != null) {
            List<EstagioPlano> listaEstagioPlanos = getPlano().ordenarEstagiosPorPosicao();
            return !this.getPlano().getEstagiosPlanos().stream()
                    .filter(estagioPlano -> estagioPlano.getEstagio().getGruposSemaforicos()
                            .contains(this.getGrupoSemaforico()) && estagioPlano.getTempoVerdeEstagio() != null)
                    .anyMatch(estagioPlano -> estagioPlano.getTempoVerdeDoGrupoSemaforico(listaEstagioPlanos, this.getGrupoSemaforico()) < this.getGrupoSemaforico().getTempoVerdeSeguranca());
        }
        return true;
    }

    @Override
    public Object clone() throws CloneNotSupportedException {
        return super.clone();
    }

    public void addIntervalos(Intervalo intervalo) {
        if (getIntervalos() == null) {
            setIntervalos(new ArrayList<Intervalo>());
        }
        getIntervalos().add(intervalo);
    }

    public List<Intervalo> ordenarIntervalos() {
        List<Intervalo> listaIntervalos = this.getIntervalos();
        listaIntervalos.sort((anterior, proximo) -> anterior.getOrdem().compareTo(proximo.getOrdem()));
        return listaIntervalos;
    }

    public void criarIntervalos(){
        int ordem = 1;
        GrupoSemaforico grupoSemaforico = getGrupoSemaforico();
        List<EstagioPlano> estagiosPlanos = getPlano().ordenarEstagiosPorPosicaoSemEstagioDispensavel();
        for (EstagioPlano estagioPlano : estagiosPlanos) {
            Estagio estagioAtual = estagioPlano.getEstagio();
            Estagio estagioAnterior = estagioPlano.getEstagioPlanoAnterior(estagiosPlanos).getEstagio();

            //Caso o grupoSemaforico não tinha o direito de passagem e continua sem ter o direito de passagem
            if(!estagioAnterior.getGruposSemaforicos().contains(grupoSemaforico) && !estagioAtual.getGruposSemaforicos().contains(grupoSemaforico)){
                ordem = criaIntervalo(ordem, EstadoGrupoSemaforico.VERMELHO, getPlano().getTempoEstagio(estagioPlano));
            }

            //Caso o grupoSemaforico não tinha o direito de passagem e está ganhando
            if(!estagioAnterior.getGruposSemaforicos().contains(grupoSemaforico) && estagioAtual.getGruposSemaforicos().contains(grupoSemaforico)){
                ordem = criaIntervalo(ordem, EstadoGrupoSemaforico.VERMELHO, getPlano().getTempoEntreVerdeEntreEstagios(estagioAtual, estagioAnterior));
                ordem = criaIntervalo(ordem, EstadoGrupoSemaforico.VERDE, estagioPlano.getTempoVerdeEstagio());
            }

            //Caso o grupoSemaforico tinha o direito de passagem e está perdendo
            if (estagioAnterior.getGruposSemaforicos().contains(grupoSemaforico) && !estagioAtual.getGruposSemaforicos().contains(grupoSemaforico)) {
                Transicao transicao = grupoSemaforico.findTransicaoByOrigemDestino(estagioAnterior, estagioAtual);
                TabelaEntreVerdesTransicao tabelaEntreVerdesTransicao = grupoSemaforico.findTabelaEntreVerdesTransicaoByTransicao(getPlano().getPosicaoTabelaEntreVerde(), transicao);
                if (transicao.isAtrasoDeGrupoPresent()) {
                    ordem = criaIntervalo(ordem, EstadoGrupoSemaforico.VERDE, transicao.getAtrasoDeGrupo().getAtrasoDeGrupo());
                }
                if (grupoSemaforico.isVeicular()){
                    ordem = criaIntervalo(ordem, EstadoGrupoSemaforico.AMARELO, tabelaEntreVerdesTransicao.getTempoAmarelo());
                } else {
                    ordem = criaIntervalo(ordem, EstadoGrupoSemaforico.VERMELHO_INTERMITENTE, tabelaEntreVerdesTransicao.getTempoVermelhoIntermitente());
                }
                ordem = criaIntervalo(ordem, EstadoGrupoSemaforico.VERMELHO_LIMPEZA, tabelaEntreVerdesTransicao.getTempoVermelhoLimpeza());
                ordem = criaIntervalo(ordem, EstadoGrupoSemaforico.VERMELHO, estagioPlano.getTempoVerdeEstagio());
            }

            //Caso o grupoSemaforico tinha o direito de passagem e continua tento o direito de passagem
            if(estagioAnterior.getGruposSemaforicos().contains(grupoSemaforico) && estagioAtual.getGruposSemaforicos().contains(grupoSemaforico)){
                ordem = criaIntervalo(ordem, EstadoGrupoSemaforico.VERDE, getPlano().getTempoEstagio(estagioPlano));
            }

        }
    }

    private Integer criaIntervalo(int ordem, EstadoGrupoSemaforico estado, Integer tamanho) {
        Intervalo intervalo = new Intervalo();
        intervalo.setOrdem(ordem);
        intervalo.setEstadoGrupoSemaforico(estado);
        intervalo.setTamanho(tamanho);
        intervalo.setGrupoSemaforicoPlano(this);
        this.addIntervalos(intervalo);
        return ordem + 1;
    }
}
