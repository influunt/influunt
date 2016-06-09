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

    public <S extends T> S save(S entity) {
        entity.setDataAtualizacao(new Date());
        jpaApi.withTransaction(() -> {
            if (entity.getId() == null) {
                entity.setDataCriacao(new Date());
                jpaApi.em().persist(entity);
            } else {
                jpaApi.em().merge(entity);
            }
        });
        return entity;
    }

    public <S extends T> S update(S entity, ID id) {
        jpaApi.withTransaction(() -> {
            entity.setDataAtualizacao(new Date());
            entity.setId(id);
            jpaApi.em().merge(entity);
        });
        return entity;
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
//                jpaApi.em().remove(entity);
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
