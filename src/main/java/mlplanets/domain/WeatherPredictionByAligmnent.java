package mlplanets.domain;

import mlplanets.enums.WeatherType;
import mlplanets.service.CelestialObjectService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Collection;

public class WeatherPredictionByAligmnent implements WeatherPredictionStrategy {

    private static final Logger log = LoggerFactory.getLogger(WeatherPredictionByAligmnent.class);

    @Autowired
    CelestialObjectService coService;

    @Override
    public WeatherType predict(int day, Collection<CelestialObject> celestialObjects) {

        for (CelestialObject celestialObject : celestialObjects) {
            if (celestialObject.getOrbit().getDistance() != 0) {
                log.info("Planet: " + celestialObject.getName() + " at: " + coService.getPositionForDay(celestialObject, day));
            }
        }

        return null;
    }
}
