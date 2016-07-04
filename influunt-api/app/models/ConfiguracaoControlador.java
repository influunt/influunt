package models;

import com.avaje.ebean.Model;
import com.avaje.ebean.annotation.CreatedTimestamp;
import com.avaje.ebean.annotation.UpdatedTimestamp;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import org.hibernate.validator.constraints.NotBlank;
import org.joda.time.DateTime;
import json.deserializers.InfluuntDateTimeDeserializer;
import json.serializers.InfluuntDateTimeSerializer;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import java.util.UUID;

/**
 * Entidade que represnta as configurações dos controladores no sistema
 *
 * @author lesiopinheiro
 *
 */
@Entity
@Table(name = "configuracao_controladores")
public class ConfiguracaoControlador extends Model {

    private static final long serialVersionUID = -4628897272277523020L;

    public static Finder<UUID, ConfiguracaoControlador> find = new Finder<UUID, ConfiguracaoControlador>(ConfiguracaoControlador.class);

    @Id
    private UUID id;

    @Column
    @NotBlank(message = "não pode ficar em branco")
    private String descricao;

    @Column
    @Min(value=1, message = "Deve ser maior que zero")
    @NotNull
    private Integer limiteEstagio = 16;

    @Column
    @Min(value=1, message = "Deve ser maior que zero")
    @NotNull
    private Integer limiteGrupoSemaforico = 16;

    @Column
    @Min(value=1, message = "Deve ser maior que zero")
    @NotNull
    private Integer limiteAnel = 4;

    @Column
    @Min(value=1, message = "Deve ser maior que zero")
    @NotNull
    private Integer limiteDetectorPedestre = 4;

    @Column
    @Min(value=1, message = "Deve ser maior que zero")
    @NotNull
    private Integer limiteDetectorVeicular = 8;

    @Column
    @JsonDeserialize(using= InfluuntDateTimeDeserializer.class)
    @JsonSerialize(using= InfluuntDateTimeSerializer.class)
    @CreatedTimestamp
    private DateTime dataCriacao;

    @Column
    @JsonDeserialize(using= InfluuntDateTimeDeserializer.class)
    @JsonSerialize(using= InfluuntDateTimeSerializer.class)
    @UpdatedTimestamp
    private DateTime dataAtualizacao;

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public Integer getLimiteEstagio() {
        return limiteEstagio;
    }

    public void setLimiteEstagio(Integer limiteEstagio) {
        this.limiteEstagio = limiteEstagio;
    }

    public Integer getLimiteGrupoSemaforico() {
        return limiteGrupoSemaforico;
    }

    public void setLimiteGrupoSemaforico(Integer limiteGrupoSemaforico) {
        this.limiteGrupoSemaforico = limiteGrupoSemaforico;
    }

    public Integer getLimiteAnel() {
        return limiteAnel;
    }

    public void setLimiteAnel(Integer limiteAnel) {
        this.limiteAnel = limiteAnel;
    }

    public Integer getLimiteDetectorPedestre() {
        return limiteDetectorPedestre;
    }

    public void setLimiteDetectorPedestre(Integer limiteDetectorPedestre) {
        this.limiteDetectorPedestre = limiteDetectorPedestre;
    }

    public Integer getLimiteDetectorVeicular() {
        return limiteDetectorVeicular;
    }

    public void setLimiteDetectorVeicular(Integer limiteDetectorVeicular) {
        this.limiteDetectorVeicular = limiteDetectorVeicular;
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
}
