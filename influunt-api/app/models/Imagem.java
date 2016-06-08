package models;

import java.util.Date;

import framework.BaseEntity;

// TODO - Verificar como serao salva as imagens
public class Imagem extends BaseEntity<String> {

    private static final long serialVersionUID = 238472872642410060L;

    private String id;
    private Date dataCriacao;
    private Date dataAtualizacao;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Date getDataCriacao() {
        return dataCriacao;
    }

    public void setDataCriacao(Date dataCriacao) {
        this.dataCriacao = dataCriacao;
    }

    public Date getDataAtualizacao() {
        return dataAtualizacao;
    }

    protected void setDataAtualizacao(Date dataAtualizacao) {
        this.dataAtualizacao = dataAtualizacao;
    }

}
