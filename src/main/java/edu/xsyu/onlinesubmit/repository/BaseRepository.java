package edu.xsyu.onlinesubmit.repository;

import edu.xsyu.onlinesubmit.entity.BaseEntity;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

public abstract class BaseRepository<T extends BaseEntity> {
    private final Class<T> classTag;
    private final String entityName;

    @PersistenceContext
    protected EntityManager em;

    protected BaseRepository(Class<T> classTag) {
        this.classTag = classTag;
        this.entityName = classTag.getSimpleName();
    }

    protected BaseRepository(Class<T> classTag, String entityName) {
        this.classTag = classTag;
        this.entityName = entityName;
    }

    public Optional<T> findOneById(Long id) {
        return Optional.ofNullable(em.find(classTag, id));
    }

    public List<T> findAll() {
        TypedQuery<T> query = em.createQuery("SELECT entity FROM " + entityName + " entity", classTag);
        return query.getResultList();
    }

    /**
     * 不关心实体是否已经存在, 如果存在则根据 id 更新<br/>
     * 当你不确定是调用 create 还是 updateById 时, 请调用 save
     */
    @Transactional
    public T save(T entity) {
        if (entity != null) {
            if (entity.getId() != null) {
                em.merge(entity);
            } else {
                em.persist(entity);
            }
        }

        return entity;
    }

    @Transactional
    public T updateById(T entity) {
        return em.merge(entity);
    }

    @Transactional
    public T create(T entity) {
        em.persist(entity);
        return entity;
    }

    @Transactional
    public void deleteById(T entity) {
        if (entity != null && entity.getId() != null) {
            if (em.contains(entity)) {
                em.remove(entity);
            } else {
                em.remove(em.getReference(classTag, entity.getId()));
            }
        }
    }

    @Transactional
    public void deleteById(Long id) {
        if (id != null) {
            em.remove(em.getReference(classTag, id));
        }
    }

}
