package com.mlinc.mlplanets.domain.dao.impl;

import com.mlinc.mlplanets.domain.dao.DAO;
import com.mlinc.mlplanets.domain.model.Entity;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.DetachedCriteria;
import org.springframework.beans.factory.annotation.Autowired;

import java.lang.reflect.ParameterizedType;
import java.util.List;

public class AbstractDAO<T extends Entity> implements DAO<T> {

    @Autowired
    private SessionFactory sessionFactory;

    private final Class<T> genericClass;

    @SuppressWarnings("unchecked")
    public AbstractDAO() {
        this.genericClass = (Class<T>) ((ParameterizedType) getClass()
                .getGenericSuperclass()).getActualTypeArguments()[0];
    }

    @Override
    public List<T> getAll() {
        return (List<T>) getAttachedCriteria().list();
    }

    protected Session getCurrentSession() {
        return sessionFactory.getCurrentSession();
    }

    @Override
    @SuppressWarnings("unchecked")
    public T findById(int id) {
        return (T) getCurrentSession().load(genericClass, id);
    }

    @Override
    public long add(T newEntity) {
        return (Long) this.getCurrentSession().save(newEntity);
    }

    @Override
    public void update(T entity) {
        this.getCurrentSession().update(entity);
    }

    @Override
    public void addOrUpdate(T entity) {
        this.getCurrentSession().saveOrUpdate(entity);
    }

    @Override
    public void delete(T entity) {
        this.getCurrentSession().delete(entity);
    }

    @Override
    public Criteria getAttachedCriteria() {
        return getCurrentSession().createCriteria(getGenericClass());
    }

    @Override
    public DetachedCriteria getDetachedCriteria() {
        return DetachedCriteria.forClass(getGenericClass());
    }

    protected Class<T> getGenericClass() {
        return genericClass;
    }
}
