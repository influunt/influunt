package models;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import java.util.UUID;

@Entity
public class TransicaoProibida {

    @Id
    private UUID id;

    @OneToOne
    private Estagio origem;

    @OneToOne
    private Estagio destino;

    @OneToOne
    private Estagio alternativo;

    public Estagio getOrigem() {
        return origem;
    }

    public void setOrigem(Estagio origem) {
        this.origem = origem;
    }

    public Estagio getDestino() {
        return destino;
    }

    public void setDestino(Estagio destino) {
        this.destino = destino;
    }

    public Estagio getAlternativo() {
        return alternativo;
    }

    public void setAlternativo(Estagio alternativo) {
        this.alternativo = alternativo;
    }
}
