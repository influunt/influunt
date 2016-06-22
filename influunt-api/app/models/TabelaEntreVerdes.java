package models;

import javax.persistence.*;
import java.util.List;
import java.util.UUID;

/**
 * Created by rodrigosol on 6/22/16.
 */
@Entity
public class TabelaEntreVerdes {

    @Id
    private UUID id;

    @ManyToOne
    private GrupoSemaforico grupoSemaforico;

    @OneToMany
    private List<Transicao> transicoes;
}
