package com.mlinc.mlplanets.domain.dao.impl;

import com.mlinc.mlplanets.domain.model.Entity;
import com.mlinc.mlplanets.domain.dao.NamedEntityDAO;
import org.hibernate.criterion.Restrictions;

public class AbstractNamedEntityDAO<T extends Entity> extends AbstractDAO<T> implements NamedEntityDAO<T> {

    @Override
    public T findByName(String name) {
        return (T) getAttachedCriteria().add(Restrictions.eq("name", name)).uniqueResult();
    }
}
