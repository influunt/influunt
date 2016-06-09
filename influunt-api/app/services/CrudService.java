package services;

import java.io.Serializable;

import com.google.inject.Inject;

import framework.BaseEntity;
import play.db.jpa.JPAApi;

public abstract class CrudService<T extends BaseEntity<ID>, ID extends Serializable> {
    
    @Inject
    private JPAApi jpaApi;
    
    public <S extends T> S save(S entity) {
        jpaApi.withTransaction(() -> {
            jpaApi.em().persist(entity);
        });
        return entity;
    }
//
//    public <S extends T> Iterable<S> save(Iterable<S> entities) {
//        return jpaApi.em().save(entities);
//    }
//
    public T findOne(ID id) {
        return (T) jpaApi.em().find(BaseEntity.class, id);
    }
//
//    public boolean exists(ID id) {
//        return jpaApi.em().exists(id);
//    }
//
//    public Iterable<T> findAll(Sort sort) {
//        return jpaApi.em().findAll(sort);
//    }
//
//    public Page<T> findAll(Pageable pageable) {
//        return jpaApi.em().findAll(pageable);
//    }
//
//    public long count() {
//        return jpaApi.em().count();
//    }
//
//    public void delete(ID id) {
//        jpaApi.em().delete(id);
//    }
//
//    public void delete(T entity) {
//        jpaApi.em().delete(entity);
//    }
//
//    public void delete(Iterable<? extends T> entities) {
//        jpaApi.em().delete(entities);
//    }

}
