package models;

import com.avaje.ebean.Model;
import com.avaje.ebean.annotation.CreatedTimestamp;
import com.avaje.ebean.annotation.UpdatedTimestamp;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import models.deserializers.InfluuntDateTimeDeserializer;
import models.deserializers.ModeloControladorDeserializer;
import models.serializers.InfluuntDateTimeSerializer;
import models.serializers.ModeloControladorSerializer;
import org.joda.time.DateTime;

import javax.persistence.*;
import java.util.UUID;

/**
 * Entidade que repesenta o {@link ModeloControlador} do {@link Controlador} no
 * sistema
 *
 * @author lesiopinheiro
 */
@Entity
@Table(name = "modelo_controladores")
@JsonSerialize(using = ModeloControladorSerializer.class)
@JsonDeserialize(using = ModeloControladorDeserializer.class)
public class ModeloControlador extends Model {

    private static final long serialVersionUID = -3153929481907380680L;

    public static Finder<UUID, ModeloControlador> find = new Finder<UUID, ModeloControlador>(ModeloControlador.class);

    @Id
    private UUID id;

    @ManyToOne
    private Fabricante fabricante;

    @ManyToOne
    private ConfiguracaoControlador configuracao;

    @Column
    private String descricao;

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

    public Fabricante getFabricante() {
        return fabricante;
    }

    public void setFabricante(Fabricante fabricante) {
        this.fabricante = fabricante;
    }

    public ConfiguracaoControlador getConfiguracao() {
        return configuracao;
    }

    public void setConfiguracao(ConfiguracaoControlador configuracao) {
        this.configuracao = configuracao;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
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
