package com.mlinc.mlplanets.domain.dao;

import com.mlinc.mlplanets.domain.enums.WeatherType;
import com.mlinc.mlplanets.domain.model.Prediction;
import com.mlinc.mlplanets.domain.model.SolarSystem;

import java.util.List;

public interface PredictionDAO extends DAO<Prediction> {

    Prediction findByDay(SolarSystem solarSystem, long day);

    Long getLastPredictedDay(SolarSystem solarSystem);

    Long getQuantityOfDaysForTypeBetween(SolarSystem solarSystem, WeatherType weatherType, long fromDay, long toDay);

    List<Long> getDaysOfMaxRain(SolarSystem solarSystem, long fromDay, long toDay);
}
