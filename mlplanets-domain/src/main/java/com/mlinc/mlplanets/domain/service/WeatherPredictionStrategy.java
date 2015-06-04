package com.mlinc.mlplanets.domain.service;

import com.mlinc.mlplanets.domain.enums.WeatherType;
import com.mlinc.mlplanets.domain.model.CelestialObject;

import javax.transaction.NotSupportedException;
import java.util.List;

public interface WeatherPredictionStrategy {

    WeatherType predict(long day, List<CelestialObject> celestialObjects) throws NotSupportedException;
}
