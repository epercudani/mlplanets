package mlplanets.domain;

import mlplanets.enums.WeatherType;
import mlplanets.service.CelestialObjectService;
import mlplanets.util.Triangle;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import javax.transaction.NotSupportedException;
import java.awt.geom.Point2D;
import java.util.List;

public class WeatherPredictionByPlanetAligmnent implements WeatherPredictionStrategy {

    private static final Logger log = LoggerFactory.getLogger(WeatherPredictionByPlanetAligmnent.class);

    private static final int SUPPORTED_OBJECTS_QUANTITY = 3;

    @Autowired
    CelestialObjectService coService;

    @Override
    public WeatherType predict(int day, List<CelestialObject> celestialObjects) throws NotSupportedException {

        if (celestialObjects.size() != SUPPORTED_OBJECTS_QUANTITY) {
            throw new NotSupportedException("La estrategia de prediccion actual solo soporta sistemas con " +
                    SUPPORTED_OBJECTS_QUANTITY + "planetas.");
        }

        Point2D[] todayPositionsPoints = getPositionsAsPointsArray(day, celestialObjects);
        Triangle todayTriangle = new Triangle(todayPositionsPoints);

        Point2D[] tomorrowPositionsPoints = getPositionsAsPointsArray(day + 1, celestialObjects);
        Triangle tomorrowTriangle = new Triangle(tomorrowPositionsPoints);

        if (isObjectsAligned(todayTriangle, tomorrowTriangle)) {
            log.info("Planetas alineados");
        }

        return null;
    }

    private Point2D[] getPositionsAsPointsArray(int day, List<CelestialObject> celestialObjects) {

        Point2D[] positionsList = new Point2D[SUPPORTED_OBJECTS_QUANTITY];

        for (int i = 0; i < celestialObjects.size(); ++i) {

            if (celestialObjects.get(i).getOrbit().getDistance() != 0) {
                positionsList[i] = coService.getPositionForDay(celestialObjects.get(i), day);
            }
        }

        return positionsList;
    }

    private boolean isObjectsAligned(Triangle todayTriangle, Triangle tomorrowTriangle) {

        if (Math.abs(todayTriangle.getArea()) <= Triangle.EPSILON) {
            return true;
        } else if (Math.abs(tomorrowTriangle.getArea()) <= Triangle.EPSILON) {
            return false;
        } else {
            return Math.signum(todayTriangle.getArea()) != Math.signum(tomorrowTriangle.getArea());
        }
    }
}
