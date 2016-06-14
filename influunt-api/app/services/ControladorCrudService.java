package services;
import models.Controlador;;

public class ControladorCrudService extends CrudService<Controlador, String> {

    @Override
    public void parseFromEntity(Controlador existingEntity, Controlador entity) {
        existingEntity.setArea(entity.getArea());
//        if(entity.getCoordenada()!=null) {
//            existingEntity.setCoordenada(entity.getCoordenada());
//        }
    }
}