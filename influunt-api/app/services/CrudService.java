package services;

import java.io.Serializable;
import java.lang.reflect.ParameterizedType;
import java.util.Date;

import javax.persistence.EntityManager;

import com.google.inject.Inject;

import exceptions.EntityNotFound;
import framework.BaseEntity;
import play.db.jpa.JPAApi;

public abstract class CrudService<T extends BaseEntity<ID>, ID extends Serializable> {

    @Inject
    private JPAApi jpaApi;

    public abstract void parseFromEntity(T existingEntity, T entity);
    
    public <S extends T> S save(S entity) {
        entity.setDataAtualizacao(new Date());
        jpaApi.withTransaction(() -> {
            entity.setDataCriacao(new Date());
            jpaApi.em().persist(entity);
        });
        return entity;
    }

    @SuppressWarnings("unchecked")
    public <S extends T> S update(S entity, ID id) {
        return jpaApi.withTransaction(() -> {
            EntityManager em = jpaApi.em();
            S existingEntity = (S) em.find(getGenericType(), id);
            if (existingEntity != null) {
                existingEntity.setDataAtualizacao(new Date());
                parseFromEntity(existingEntity, entity);
                return existingEntity;
            }
            return null;
        });
    }

    @SuppressWarnings("unchecked")
    public T findOne(ID id) {
        return (T) jpaApi.withTransaction(() -> {
            return (T) jpaApi.em().find(getGenericType(), id);
        });
    }

    @SuppressWarnings("unchecked")
    public Iterable<T> findAll() {
        return jpaApi.withTransaction(() -> {
            return jpaApi.em().createQuery("Select t from " + getGenericType().getSimpleName() + " t").getResultList();
        });
    }

    public void delete(ID id) throws EntityNotFound {
        boolean deleted = jpaApi.withTransaction(() -> {
            T entity = findOne(id);
            if (entity != null) {
                EntityManager em = jpaApi.em();
                em.remove(em.contains(entity) ? entity : em.merge(entity));
                return true;
            }
            return false;
        });
        if (!deleted) {
            throw new EntityNotFound(getGenericType().getSimpleName() + " não encontrado.");
        }
    }

    private Class getGenericType() {
        return ((Class) ((ParameterizedType) this.getClass().getGenericSuperclass()).getActualTypeArguments()[0]);
    }
    

}
