package com.mlinc.mlplanets.domain.dao.impl;

import com.mlinc.mlplanets.domain.dao.PredictionDAO;
import com.mlinc.mlplanets.domain.model.Prediction;
import com.mlinc.mlplanets.domain.model.SolarSystem;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;

public class PredictionDAOImpl extends AbstractDAO<Prediction> implements PredictionDAO {

    public Long getLastPredictedDay(SolarSystem solarSystem) {

        return (Long) getAttachedCriteria()
                .add(Restrictions.eq("solarSystem", solarSystem))
                .setProjection(Projections.max("day"))
                .uniqueResult();
    }

    public Prediction findByDay(SolarSystem solarSystem, long day) {
        return (Prediction) getAttachedCriteria()
                .add(Restrictions.eq("solarSystem", solarSystem))
                .add(Restrictions.eq("day", day))
                .uniqueResult();
    }
}
