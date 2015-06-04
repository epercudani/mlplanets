package com.mlinc.mlplanets.domain.service;

import com.mlinc.mlplanets.domain.model.Prediction;
import com.mlinc.mlplanets.domain.model.SolarSystem;

public interface SolarSystemService {

    void predictWeatherForSystem(String name, long startingDay);

    Prediction getPredictionForDay(SolarSystem solarSystem, long day);

    Prediction getPredictionForDay(long day);
}
