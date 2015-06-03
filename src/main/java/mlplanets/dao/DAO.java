package mlplanets.dao;

import org.hibernate.Criteria;
import org.hibernate.criterion.DetachedCriteria;

import java.util.List;

public interface DAO<T> {

    List<T> getAll();

    T findById(int id);

    long add(T newEntity);

    void update(T entity);

    void delete(T entity);

    Criteria getAttachedCriteria();

    DetachedCriteria getDetachedCriteria();
}
