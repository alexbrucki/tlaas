package de.chocoquic.fasade;

import com.querydsl.core.types.EntityPath;
import java.io.Serializable;
import java.util.Collection;
import java.util.List;

import javax.ejb.ConcurrencyManagement;
import javax.ejb.ConcurrencyManagementType;
import javax.ejb.Singleton;
import javax.inject.Inject;
import javax.inject.Named;
import javax.persistence.EntityManager;
import javax.persistence.Query;

import com.querydsl.core.types.dsl.EntityPathBase;
import com.querydsl.core.types.dsl.PathBuilder;
import com.querydsl.core.types.dsl.PathBuilderFactory;
import com.querydsl.jpa.impl.JPAQuery;

import de.chocoquic.entity.EagerAble;
import java.util.Objects;
import javax.enterprise.inject.Produces;
import javax.persistence.LockModeType;
import javax.persistence.PersistenceContext;

@Singleton()
@ConcurrencyManagement(ConcurrencyManagementType.BEAN)
@Named("genericFasade")
@SuppressWarnings("serial")
public class GenericFasade<T> implements Serializable {

    private static final int BULK_INSERT_BATCH_SIZE = 50;

    @Inject
    @PersistenceContext(unitName = "tlaas-pu")
    private EntityManager em;

    private Class<T> defineClass;

    private PathBuilder<?> builder;    
   
    public EntityPath qPath;

    public GenericFasade() {
    }

    public GenericFasade(Class<T> entityClass) {
        this.defineClass = entityClass;
        this.builder = new PathBuilderFactory().create(entityClass);
        this.qPath = new EntityPathBase<>(this.defineClass, this.defineClass.getSimpleName());
    }

    @Produces
    public EntityManager getEntityManager() {
        return em;
    }
    
    

    /**
     * Returns the entity identified by Id or null if non found.
     * <p/>
     * @param <T> type of the entity
     * @param entityClass the defineClass
     * @param id the id
     * @return the entity identified by Id or null if non found.
     */
    public <T> T findById(Class<T> entityClass, Object id) {
        validate(entityClass);
        if (id == null) {
            return null;
        }
        return em.find(entityClass, id);
    }

    /**
     * Returns the entity identified by Id or null if non found. This is the
     * eager implementation. If the Entity implements {@link EagerAble}, the
     * method fetchEager is called in the transaction.
     * <p/>
     * @param <T> type of the entity
     * @param entityClass the defineClass
     * @param id the id
     * @return the entity identified by Id or null if non found.
     */
    public <T> T findByIdEager(Class<T> entityClass, Object id) {
        return optionalFetchEager(findById(entityClass, id));
    }

    /**
     * Returns the entity identified by Id and locks it by lockMode or null if
     * non found.
     * <p/>
     * @param <T> type of the entity
     * @param entityClass the defineClass
     * @param id the id
     * @param lockModeType the lockMode to use
     * @return the entity identified by Id or null if non found.
     */
    public <T> T findById(Class<T> entityClass, Object id, LockModeType lockModeType) {
        validate(entityClass);
        if (id == null) {
            return null;
        }
        return em.find(entityClass, id, lockModeType);
    }

    /**
     * Returns the entity identified by Id and locks it by lockMode or null if
     * non found. This is the eager implementation. If the Entity implements
     * {@link EagerAble}, the method fetchEager is called in the transaction.
     * <p/>
     * @param <T> type of the entity
     * @param entityClass the defineClass
     * @param id the id
     * @param lockModeType the lockMode to use
     * @return the entity identified by Id or null if non found.
     */
    public <T> T findByIdEager(Class<T> entityClass, Object id, LockModeType lockModeType) {
        return optionalFetchEager(findById(entityClass, id, lockModeType));
    }

    /**
     * Returns all entities of the defineClass or an empty list.
     * <p/>
     * @param <T> the type of the entities
     * @param entityClass the defineClass
     * @return all entities of the defineClass or an empty list.
     */
    public <T> List<T> findAll(Class<T> entityClass) {
        validate(entityClass);

        JPAQuery query = new JPAQuery(em);      
        
        return  query.from(qPath).fetch();
    }

    /**
     * Returns all entities of the defineClass or an empty list. This is the
     * eager implementation. If the Entity implements {@link EagerAble}, the
     * method fetchEager is called in the transaction.
     * <p/>
     * @param <T> the type of the entities
     * @param entityClass the defineClass
     * @return all entities of the defineClass or an empty list.
     */
    public <T> List<T> findAllEager(Class<T> entityClass) {
        return optionalFetchEager(findAll(entityClass));
    }

    /**
     * Returns all entities of the defineClass in the supplied interval or an
     * empty list.
     * <p/>
     * @param <T> the type of the entities
     * @param entityClass the defineClass
     * @param start the start of the full result to begin the list with.
     * @param amount the amount of elemets to return at max.
     * @return all entities of the defineClass or an empty list.
     */
    public <T> List<T> findAll(Class<T> entityClass, int start, int amount) {
        validate(entityClass);

        JPAQuery query = new JPAQuery(em); 
        
        return query.from(qPath).fetch().subList(start, amount);
    }

    /**
     * Returns all entities of the defineClass in the supplied interval or an
     * empty list. This is the eager implementation. If the Entity implements
     * {@link EagerAble}, the method fetchEager is called in the transaction.
     * <p/>
     * @param <T> the type of the entities
     * @param entityClass the defineClass
     * @param start the start of the full result to begin the list with.
     * @param amount the amount of elemets to return at max.
     * @return all entities of the defineClass or an empty list.
     */
    public <T> List<T> findAllEager(Class<T> entityClass, int start, int amount) {
        return optionalFetchEager(findAll(entityClass, start, amount));
    }

    public <T> long count(Class<T> entityClass) {
        validate(entityClass);

        return Long.valueOf(findAll(entityClass).size());
    }

    /**
     * remove a entity by checckign the db befor
     *
     * @param entityClass
     * @param id
     * @return true for removing and false on enity not found
     */
    public boolean remove(Class<T> entityClass, Object id) {
        T findById = findById(entityClass, id);
        if (findById != null) {
            remove(findById);
            return true;
        }
        return false;
    }

    /**
     * persist a enity
     *
     * @param entity
     */
    public void persist(T entity) {
        em.persist(entity);
    }

    /**
     * merge a entity back to db
     *
     * @param entity
     * @return the merged entity
     */
    public Object merge(T entity) {
        return em.merge(entity);
    }

    /**
     * persist many entities on Bulk with a small delay for the db to handel
     * this work load
     *
     * @param entities
     */
    public void persistBulk(Collection<T> entities) {
        int i = 0;
        for (T entity : entities) {
            persist(entity);
            if (++i % BULK_INSERT_BATCH_SIZE == 0) {
                flush();
                clear();
            }
        }
        flush();
        clear();
    }

    /**
     * this is a wrapper methode for creating a Native Query for a given class
     * !WARNING! Handle with care
     *
     * @param <T>
     * @param sqlQuery
     * @param clazz the given class
     * @return a resultList of Enititys for the given class
     */
    public <T> List<T> nativeSqlQuery(String sqlQuery, Class<T> clazz) {
        Query query = em.createNativeQuery(sqlQuery, clazz);
        @SuppressWarnings("unchecked")
        List<T> resultList = query.getResultList();
        return resultList;
    }

    /**
     * this is a wrapper methode for creating a Native Query !WARNING! Handle
     * with care
     *
     * @param sqlQuery
     * @return a resultList
     */
    public List<?> nativeSqlQuery(String sqlQuery) {
        Query query = em.createNativeQuery(sqlQuery);
        return query.getResultList();
    }

    /**
     * this methode is loading a given entity eagar
     *
     * @param <T>
     * @param entity
     * @return an entity loaded eagar
     */
    private <T> T optionalFetchEager(T entity) {
        if (entity == null) {
            return null;
        }
        if (entity instanceof EagerAble) {
            EagerAble eagerAble = (EagerAble) entity;
            eagerAble.fetchEager();
        }
        return entity;
    }

    /**
     * Loading a list of entities all eager
     *
     * @param <T> List of entries
     * @param entities
     * @return the List of entries but eager loaded
     */
    private <T> List<T> optionalFetchEager(List<T> entities) {
        if (entities == null) {
            return null;
        }
        if (entities.isEmpty()) {
            return entities;
        }
        if (!(entities.get(0) instanceof EagerAble)) {
            return entities;
        }
        entities.forEach((entity) -> {
            ((EagerAble) entity).fetchEager();
        });
        return entities;
    }

    /**
     * fast validation of a given Class
     *
     * @param clazz
     */
    private void validate(Class<?> clazz) {
        Objects.requireNonNull(clazz, "Supplied entityClass is null");
        Objects.requireNonNull(em, "Supplied EntityManager is null");
    }

    /**
     * remove a entity
     *
     * @param entity
     */
    private void remove(T entity) {
        em.remove(entity);
    }

    /**
     * flush the em
     */
    private void flush() {
        em.flush();
    }

    /**
     * clear the em
     */
    private void clear() {
        em.clear();
    }


    /**
     * Returns a {@link PathBuilder} for the configured domain type.
     *
     * @param <T>
     * @return the Querdsl {@link PathBuilder}.
     */
    @SuppressWarnings("unchecked")
    protected <T> PathBuilder<T> getBuilder() {
        return (PathBuilder<T>) builder;
    }

 

}
