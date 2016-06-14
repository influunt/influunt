package services;

import com.google.inject.Inject;
import exceptions.EntityNotFound;
import framework.BaseEntity;
import play.db.jpa.JPAApi;

import javax.persistence.EntityManager;
import java.io.Serializable;
import java.lang.reflect.ParameterizedType;
import java.util.Date;

public abstract class CrudService<T extends BaseEntity<ID>, ID extends Serializable> {

    @Inject
    private JPAApi jpaApi;

    public abstract void parseFromEntity(T existingEntity, T entity);

    public <S extends T> S save(S entity) {
        entity.setDataAtualizacao(new Date());
        entity.setDataCriacao(entity.getDataAtualizacao());
        jpaApi.em().persist(entity);
        return entity;
    }

    @SuppressWarnings("unchecked")
    public <S extends T> S update(S entity, ID id) {
            S existingEntity = (S) findOne(id);
            if (existingEntity != null) {
                existingEntity.setDataAtualizacao(new Date());
                parseFromEntity(existingEntity, entity);
                return existingEntity;
            }
            return null;
    }

    @SuppressWarnings("unchecked")
    public T findOne(ID id) {
        return (T) jpaApi.em().find(getGenericType(), id);
    }

    @SuppressWarnings("unchecked")
    public Iterable<T> findAll() {
        return jpaApi.em().createQuery("Select t from " + getGenericType().getSimpleName() + " t").getResultList();
    }

    public void delete(ID id) throws EntityNotFound {
        boolean found = false;
        T entity = findOne(id);
        if (entity != null) {
            EntityManager em = jpaApi.em();
            em.remove(em.contains(entity) ? entity : em.merge(entity));
            found = true;
        }
        if (!found) {
            throw new EntityNotFound(getGenericType().getSimpleName() + " não encontrado.");
        }
    }

    private Class getGenericType() {
        return ((Class) ((ParameterizedType) this.getClass().getGenericSuperclass()).getActualTypeArguments()[0]);
    }


}
