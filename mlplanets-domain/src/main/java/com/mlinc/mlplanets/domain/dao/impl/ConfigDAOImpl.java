package com.mlinc.mlplanets.domain.dao.impl;

import com.mlinc.mlplanets.domain.dao.ConfigDAO;
import com.mlinc.mlplanets.domain.model.Config;
import org.hibernate.criterion.Restrictions;

public class ConfigDAOImpl extends AbstractDAO<Config> implements ConfigDAO {

    @Override
    public Config findByKey(String key) {
        return (Config) getAttachedCriteria()
                .add(Restrictions.eq("key", key))
                .uniqueResult();
    }
}
