package services;

import models.Cidade;

public class CidadeCrudService extends CrudService<Cidade, String> {

    public void parseFromEntity(Cidade existingCidade, Cidade cidade) {
        existingCidade.setNome(cidade.getNome());
    }
    
}
