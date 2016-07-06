package models;

import javax.persistence.*;
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

    @Column
    private Integer tempoAmarelo;

    @Column
    private Integer tempoVermelhoIntermitente;

    @Column
    private Integer tempoVermelhoLimpeza;

    @Column
    private Integer tempoAtrasoGrupo;



}
