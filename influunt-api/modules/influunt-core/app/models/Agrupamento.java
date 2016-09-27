package models;

import checks.NumeroDeControladores;
import checks.NumeroDeElementosNaLista;
import checks.PlanosCheck;
import com.avaje.ebean.Model;
import com.avaje.ebean.annotation.ChangeLog;
import com.avaje.ebean.annotation.CreatedTimestamp;
import com.avaje.ebean.annotation.UpdatedTimestamp;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import json.deserializers.AgrupamentoDeserializer;
import json.deserializers.InfluuntDateTimeDeserializer;
import json.serializers.AgrupamentoSerializer;
import json.serializers.InfluuntDateTimeSerializer;
import org.hibernate.validator.constraints.NotBlank;
import org.joda.time.DateTime;
import org.joda.time.LocalTime;
import utils.InfluuntUtils;

import javax.persistence.*;
import javax.validation.constraints.AssertTrue;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.UUID;

/**
 * Entidade que representa um {@link Agrupamento} no sistema
 *
 * @author Pedro Pires
 */
@Entity
@Table(name = "agrupamentos")
@ChangeLog
@JsonSerialize(using = AgrupamentoSerializer.class)
@JsonDeserialize(using = AgrupamentoDeserializer.class)
public class Agrupamento extends Model implements Cloneable, Serializable {

    private static final long serialVersionUID = -7310183724485834593L;

    public static Finder<UUID, Agrupamento> find = new Finder<UUID, Agrupamento>(Agrupamento.class);

    public Agrupamento() {
        this.idJson = UUID.randomUUID().toString();
    }

    @Id
    private UUID id;

    @Column
    private String idJson;

    @Column
    @NotNull(message = "não pode ficar em branco")
    private String nome;

    @Column
    private String numero;

    @Column
    private String descricao;

    @Column
    @Enumerated(EnumType.STRING)
    @NotNull(message = "não pode ficar em branco")
    private TipoAgrupamento tipo;

    @ManyToMany(cascade = CascadeType.REMOVE)
    @JoinTable(name = "agrupamentos_aneis", joinColumns = {@JoinColumn(name = "agrupamento_id")}, inverseJoinColumns = {@JoinColumn(name = "anel_id")})
    @NumeroDeElementosNaLista(message = "este agrupamento deve ter pelo menos 1 anel.")
    private List<Anel> aneis;

    @Column
    @NotNull(message = "não pode ficar em branco")
    private Integer posicaoPlano;

    @Column
    @Enumerated(EnumType.STRING)
    @NotNull(message = "não pode ficar em branco")
    private DiaDaSemana diaDaSemana;

    @Column
    @NotNull(message = "não pode ficar em branco")
    private LocalTime horario;

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

    public TipoAgrupamento getTipo() {
        return tipo;
    }

    public void setTipo(TipoAgrupamento tipo) {
        this.tipo = tipo;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getNumero() {
        return numero;
    }

    public void setNumero(String numero) {
        this.numero = numero;
    }

    @Override
    public Object clone() throws CloneNotSupportedException {
        return super.clone();
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public List<Anel> getAneis() {
        return aneis;
    }

    public void setAneis(List<Anel> aneis) {
        this.aneis = aneis;
    }

    public void addAnel(Anel anel) {
        if (getAneis() == null) {
            setAneis(new ArrayList<Anel>());
        }

        getAneis().add(anel);
    }

    public Integer getPosicaoPlano() {
        return posicaoPlano;
    }

    public void setPosicaoPlano(Integer posicao_plano) {
        this.posicaoPlano = posicao_plano;
    }

    public LocalTime getHorario() {
        return horario;
    }

    public void setHorario(LocalTime horario) {
        this.horario = horario;
    }

    public DiaDaSemana getDiaDaSemana() {
        return diaDaSemana;
    }

    public void setDiaDaSemana(DiaDaSemana diaDaSemana) {
        this.diaDaSemana = diaDaSemana;
    }

    @AssertTrue(message = "O plano associado ao agrupamento deve estar configurado em todos os anéis")
    public boolean isPlanoConfiguradoEmTodosOsAneis() {
        boolean isConfigurado = true;
        for (Anel anel : getAneis()) {
            for (Plano plano : anel.getPlanos()) {
                if (!plano.getPosicao().equals(getPosicaoPlano())) {
                    isConfigurado = false;
                    break;
                }
            }
            if (!isConfigurado) break;
        }
        return isConfigurado;
    }

    // TODO: Testa se o plano X (1, 2, etc.) em todos os anéis são múltiplos entre si.
    @AssertTrue(message = "O Tempo de ciclo deve ser simétrico ou assimétrico ao tempo de ciclo dos planos.")
    public boolean isTempoCicloIgualOuMultiploDeTodoPlano() {
        boolean isMultiplo = true;
        int tempoCiclo = this.getTempoCiclo();
        for (Anel anel : getAneis()) {
            for (Plano plano : anel.getPlanos()) {
                if (plano.getPosicao().equals(getPosicaoPlano()) && (plano.isTempoFixoCoordenado() || plano.isTempoFixoIsolado())) {
                    if (!InfluuntUtils.getInstance().multiplo(tempoCiclo, plano.getTempoCiclo())) {
                        isMultiplo = false;
                        break;
                    }
                }
            }
            if (!isMultiplo) break;
        }
        return isMultiplo;
    }

    // Retorna o tempo de ciclo do plano do primeiro anel
    private Integer getTempoCiclo() {
        Plano p = getAneis()
                .stream()
                .map(Anel::getPlanos)
                .flatMap(Collection::stream)
                .filter(plano -> plano.getPosicao().equals(this.getPosicaoPlano()) && (plano.isTempoFixoCoordenado() || plano.isTempoFixoIsolado()))
                .findFirst()
                .orElse(null);

        if (p != null) {
            return p.getTempoCiclo();
        }
        return 1;
    }
}
