package models;

import java.util.Date;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

public class GrupoSemaforico {
    
    private String id;
    private TipoGrupoSemaforico tipo;
    @ManyToOne
    @JoinColumn(name = "anel_id")
    private Anel anel;
    private Controlador controlador;
    private List<GrupoSemaforico> verdesConflitantes;
    @Column
    private Date dataCriacao;
    
    @Column
    private Date dataAtualizacao;

}
