package com.mlinc.mlplanets.domain.service;

import com.mlinc.mlplanets.domain.model.SolarSystem;
import com.mlinc.mlplanets.transport.PredictionDTO;
import com.mlinc.mlplanets.transport.PredictionSummaryDTO;

import javax.transaction.NotSupportedException;

public interface SolarSystemService {

    void predictWeatherForSystem(SolarSystem solarSystem, long startingDay);

    void predictWeatherForSystem(long startingDay) throws NotSupportedException;

    PredictionDTO getPredictionForDay(SolarSystem solarSystem, long day);

    PredictionDTO getPredictionForDay(long day) throws NotSupportedException;

    PredictionSummaryDTO getPredictionSummarySince(SolarSystem solarSystem, long startingDay);

    PredictionSummaryDTO getPredictionSummarySince(long startingDay) throws NotSupportedException;
}
