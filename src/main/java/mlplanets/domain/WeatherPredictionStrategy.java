package mlplanets.domain;

import mlplanets.enums.WeatherType;

import javax.transaction.NotSupportedException;
import java.util.List;

public interface WeatherPredictionStrategy {

    WeatherType predict(long day, List<CelestialObject> celestialObjects) throws NotSupportedException;
}
