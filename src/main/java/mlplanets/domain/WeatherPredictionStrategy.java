package mlplanets.domain;

import mlplanets.enums.WeatherType;

import java.util.Collection;

public interface WeatherPredictionStrategy {

    WeatherType predict(int day, Collection<CelestialObject> celestialObjects);
}
