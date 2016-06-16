package models;

import com.avaje.ebean.Model;
import org.joda.time.DateTime;

import javax.persistence.*;
import java.util.UUID;

/**
 * Entidade que representa o {@link Detector} no sistema
 *
 * @author lesiopinheiro
 *
 */
@Entity
@Table(name = "detectores")
public class Detector extends Model {

    private static final long serialVersionUID = 3752412658492551927L;

    public static Finder<UUID, Detector> find = new Finder<UUID, Detector>(Detector.class);

    @Id
    private UUID id;

    @ManyToOne
    @JoinColumn(name = "tipo_detector_id")
    private TipoDetector tipo;

    @ManyToOne
    @JoinColumn(name = "anel_id")
    private Anel anel;

    @Column
    private DateTime dataCriacao;

    @Column
    private DateTime dataAtualizacao;


    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public TipoDetector getTipo() {
        return tipo;
    }

    public void setTipo(TipoDetector tipo) {
        this.tipo = tipo;
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

    public DateTime getDataAtualizacao() {
        return dataAtualizacao;
    }

    public void setDataAtualizacao(DateTime dataAtualizacao) {
        this.dataAtualizacao = dataAtualizacao;
    }
}
