package de.chocoquic.dao;


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

import de.chocoquic.entity.DBEntity;




@SuppressWarnings("serial")
@Singleton()
@ConcurrencyManagement(ConcurrencyManagementType.BEAN)
@Named("genericDao")
public class GenericDao implements Serializable{

	private static final int BULK_INSERT_BATCH_SIZE = 50;
	
	// "[...] The biggest benefit of using Transaction Scoped Entity Manager is that it is stateless. This also makes the Transaction Scoped EntityManager threadsafe and thus virtually maintenance free. [...]"
	@Inject
    protected EntityManager em;

    public void persist(DBEntity entity) {
    	em.persist(entity);
    }

	public Object merge(DBEntity entity) {
		return em.merge(entity);
	}
 

	
	public DBEntity saveOrUpdate(DBEntity entity) {
		if (entity != null) {
			if (entity.getId() != null && entity.getId().longValue() > 0) {	
				return this.em.merge(entity);
			}
			this.em.persist(entity);
		}
		return entity;
	}
	
	public void persistBulk(Collection<DBEntity> entities) {
		int i = 0;
		for (DBEntity entity : entities) {
			persist(entity);
			if (++i % BULK_INSERT_BATCH_SIZE == 0) {
				flush();
				clear();
			}
		}
		flush();
		clear();
	}
	
	public void remove(Object entity) {
		em.remove(entity);
	}
	
	public void flush(){
		em.flush();
	}

	public void clear() {
		em.clear();
	}

	public <T> List<T> nativeSqlQuery(String sqlQuery, Class<T> clazz){
		Query query = em.createNativeQuery(sqlQuery, clazz);
		@SuppressWarnings("unchecked")
		List<T> resultList = query.getResultList();
		return  resultList;
	}

	public List<?> nativeSqlQuery(String sqlQuery){
		Query query = em.createNativeQuery(sqlQuery);
		return  query.getResultList();
	}
	
	

}

