package services;

import models.TipoDetector;

public class TipoDetectorCrudService extends CrudService<TipoDetector, String> {

    @Override
    public void parseFromEntity(TipoDetector existingEntity, TipoDetector entity) {
        existingEntity.setDescricao(entity.getDescricao());
    }

}
