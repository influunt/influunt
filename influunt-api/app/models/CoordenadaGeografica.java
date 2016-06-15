package models;

import com.avaje.ebean.Model;
import org.joda.time.DateTime;

import javax.persistence.*;
import java.util.UUID;

/**
 * Entidade que representa todas as coordenadas geograficas no sistema.
 * 
 * @author lesiopinheiro
 *
 */
@Entity
@Table(name = "coordenadas_geograficas")
public class CoordenadaGeografica extends Model {

    private static final long serialVersionUID = -2938220305527197172L;

    @Id
    private UUID id;

    @Column
    private Double latitude;

    @Column
    private Double longitude;

    @Column
    private DateTime dataCriacao;

    @Column
    private DateTime dataAtualizacao;

    @ManyToOne
    @JoinColumn(name="area_id")
    private Area area;

    public CoordenadaGeografica() {
        super();
    }
    public CoordenadaGeografica(Double latitude, Double longitude) {
        super();
        this.latitude = latitude;
        this.longitude = longitude;
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public Double getLatitude() {
        return latitude;
    }

    public void setLatitude(Double latitude) {
        this.latitude = latitude;
    }

    public Double getLongitude() {
        return longitude;
    }

    public void setLongitude(Double longitude) {
        this.longitude = longitude;
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

    public Area getArea() {
        return area;
    }

    public void setArea(Area area) {
        this.area = area;
    }
}
