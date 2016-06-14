package services;

import models.Area;

public class AreaCrudService extends CrudService<Area, String> {

    @Override
    public void parseFromEntity(Area existingEntity, Area entity) {
        existingEntity.setCidade(entity.getCidade());
        existingEntity.setDescricao(entity.getDescricao());        
    }

}