package models;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import java.util.UUID;

/**
 * Created by rodrigosol on 6/22/16.
 */
@Entity
public class Transicao {

    @Id
    private UUID id;

    @ManyToOne
    private TabelaEntreVerdes tabelaEntreVerdes;

    @OneToOne
    private Estagio origem;

    @OneToOne
    private Estagio destino;

    private Integer tempoAmarelo;
    private Integer tempoVermelhoIntermitente;
    private Integer tempoVermelhoLimpeza;
    private Integer tempoAtrasoGrupo;

}
