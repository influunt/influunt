package models;

import java.util.Date;

import com.avaje.ebean.Model;


// TODO - Verificar como serao salva as imagens
public class Imagem extends Model {

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

    public void setDataAtualizacao(Date dataAtualizacao) {
        this.dataAtualizacao = dataAtualizacao;
    }

}
