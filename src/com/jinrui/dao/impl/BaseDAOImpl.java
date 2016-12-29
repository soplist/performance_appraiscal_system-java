package com.jinrui.dao.impl;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.springframework.beans.factory.annotation.Autowired;

import com.jinrui.dao.BaseDAO;

public class BaseDAOImpl<T> implements BaseDAO<T>{
	  
    private SessionFactory sessionFactory;

    @Autowired
    public void setSessionFactory(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    public Session getCurrentSession() {
        return sessionFactory.getCurrentSession();
    }

    public Session qryCurrentSesion() {
        return sessionFactory.openSession();
    }

    public void delete(T cls) {

        // illegally attempted to associate a proxy with
        // two open Sessions
        Object obj = this.getCurrentSession().merge(cls);
        this.getCurrentSession().delete(obj);
    }

    public void update(T cls) {
        this.getCurrentSession().update(cls);
    }

    public void add(T cls) {
    	Session session = null;
    	try{
    	    System.out.println("BaseDAOImpl add");
    	    session = this.qryCurrentSesion();
    	    session.beginTransaction();  
    	    session.save(cls);
    	    session.getTransaction().commit();
    	}catch(Exception e){
    		e.printStackTrace();
    		session.getTransaction().rollback();
    	}finally{
    		if(session != null){
    			if(session.isOpen()){
    				session.close();
    			}
    		}
    	}
    }
    
    @SuppressWarnings("unchecked")
    public List<T> qryInfo(String hql) {
    	Session session = this.qryCurrentSesion();
		List<T> list = session.createQuery(hql).list();
		session.close();
		return list;
    }


    public void setQueryParams(Query qry, Object[] params) {
        if (params != null) {
            for (int i = 0; i < params.length; i++) {
                qry.setParameter(i, params[i]);
            }
        }
    }
    
    @SuppressWarnings("unchecked")
    public T get(Class<T> c, Serializable id) {  
        return (T) this.getCurrentSession().get(c, id);  
    }  
    
    
}
