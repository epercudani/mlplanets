package com.mlinc.mlplanets.domain.dao.impl;

import com.mlinc.mlplanets.domain.dao.PredictionDAO;
import com.mlinc.mlplanets.domain.enums.WeatherType;
import com.mlinc.mlplanets.domain.model.Prediction;
import com.mlinc.mlplanets.domain.model.SolarSystem;
import org.hibernate.criterion.Projection;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;

import java.util.List;

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

    @Override
    public Long getQuantityOfDaysForTypeBetween(SolarSystem solarSystem, WeatherType weatherType, long fromDay, long toDay) {
        return (Long) getAttachedCriteria()
                .setProjection(Projections.rowCount())
                .add(Restrictions.eq("solarSystem", solarSystem))
                .add(Restrictions.eq("weatherType", weatherType))
                .add(Restrictions.between("day", fromDay, toDay))
                .uniqueResult();
    }

    @Override
    @SuppressWarnings("unchecked")
    public List<Long> getDaysOfMaxRain(SolarSystem solarSystem, long fromDay, long toDay) {
        return (List<Long>) getAttachedCriteria()
                .setProjection(Projections.property("day"))
                .add(Restrictions.eq("solarSystem", solarSystem))
                .add(Restrictions.eq("weatherType", WeatherType.MAX_INTENSITY_RAIN))
                .add(Restrictions.between("day", fromDay, toDay))
                .list();
    }
}
