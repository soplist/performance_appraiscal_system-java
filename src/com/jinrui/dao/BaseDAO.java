package com.jinrui.dao;

import java.io.Serializable;
import java.util.List;

import org.hibernate.Query;


public interface BaseDAO<T> {
    public void delete(T cls);
    public void update(T cls);
    public void add(T cls);
    public void setQueryParams(Query qry, Object[] params);
    public T get(Class<T> c, Serializable id);
    public List<T> qryInfo(String hql);
}
