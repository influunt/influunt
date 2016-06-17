package models;

import com.avaje.ebean.Model;
import com.avaje.ebean.annotation.CreatedTimestamp;
import com.avaje.ebean.annotation.UpdatedTimestamp;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import org.joda.time.DateTime;
import utils.InfluuntDateTimeDeserializer;
import utils.InfluuntDateTimeSerializer;

import javax.persistence.*;
import javax.validation.Valid;
import java.util.UUID;

/**
 * Entidade que representa o {@link Movimento} no sistema
 * 
 * @author lesiopinheiro
 *
 */
@Entity
@Table(name = "movimentos")
public class Movimento extends Model {

    private static final long serialVersionUID = 5984122994022835262L;

    @Id
    private UUID id;
    @Column
    private String descricao;

    // TODO - verificar como as imagens serao salvas
    @Transient
    private Imagem imagem;

    @ManyToOne
    @JoinColumn(name = "anel_id")
    private Anel anel;

    @OneToOne(mappedBy = "movimento", cascade = CascadeType.ALL)
    @JsonManagedReference
    @Valid
    private Estagio estagio;

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

    public Imagem getImagem() {
        return imagem;
    }

    public void setImagem(Imagem imagem) {
        this.imagem = imagem;
    }

    public Anel getAnel() {
        return anel;
    }

    public void setAnel(Anel anel) {
        this.anel = anel;
    }

    public DateTime getDataCriacao() {
        return dataCriacao;
    }

    public void setDataCriacao(DateTime dataCriacao) {
        this.dataCriacao = dataCriacao;
    }

    public Estagio getEstagio() {
        return estagio;
    }

    public void setEstagio(Estagio estagio) {
        this.estagio = estagio;
    }

    public DateTime getDataAtualizacao() {
        return dataAtualizacao;
    }

    public void setDataAtualizacao(DateTime dataAtualizacao) {
        this.dataAtualizacao = dataAtualizacao;
    }


    public void criarEstagio(){
        if(this.id == null){
            Estagio estagio = new Estagio();
            estagio.setMovimento(this);
            this.setEstagio(estagio);
        }else{
            //TODO: O que fazer na atualizacao?
        }
    }
}
