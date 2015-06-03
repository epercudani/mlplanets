package mlplanets.dao.impl;

import mlplanets.dao.NamedEntityDAO;
import mlplanets.domain.Entity;
import org.hibernate.criterion.Restrictions;

public class AbstractNamedEntityDAO<T extends Entity> extends AbstractDAO<T> implements NamedEntityDAO<T> {

    @Override
    public T findByName(String name) {
        return (T) getAttachedCriteria().add(Restrictions.eq("name", name)).uniqueResult();
    }
}
