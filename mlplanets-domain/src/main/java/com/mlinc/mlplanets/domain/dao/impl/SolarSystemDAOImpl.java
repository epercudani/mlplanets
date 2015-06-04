package com.mlinc.mlplanets.domain.dao.impl;

import com.mlinc.mlplanets.domain.dao.SolarSystemDAO;
import com.mlinc.mlplanets.domain.model.SolarSystem;
import org.hibernate.criterion.Projections;

public class SolarSystemDAOImpl extends AbstractNamedEntityDAO<SolarSystem> implements SolarSystemDAO {

    @Override
    public SolarSystem findUnique() {

        long solarSystemsDefined = (Long) getAttachedCriteria().setProjection(Projections.rowCount()).uniqueResult();

        if (solarSystemsDefined == 1) {
            return (SolarSystem) getAttachedCriteria().uniqueResult();
        }

        return null;
    }
}
