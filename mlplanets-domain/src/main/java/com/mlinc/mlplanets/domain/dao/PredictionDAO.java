package com.mlinc.mlplanets.domain.dao;

import com.mlinc.mlplanets.domain.model.Prediction;
import com.mlinc.mlplanets.domain.model.SolarSystem;

public interface PredictionDAO extends DAO<Prediction> {

    Long getLastPredictedDay(SolarSystem solarSystem);

    Prediction findByDay(SolarSystem solarSystem, long day);
}
