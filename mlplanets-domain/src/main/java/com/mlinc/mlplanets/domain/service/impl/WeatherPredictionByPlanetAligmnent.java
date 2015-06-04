package com.mlinc.mlplanets.domain.service.impl;

import com.mlinc.mlplanets.domain.enums.WeatherType;
import com.mlinc.mlplanets.domain.model.CelestialObject;
import com.mlinc.mlplanets.domain.service.CelestialObjectService;
import com.mlinc.mlplanets.domain.service.WeatherPredictionStrategy;
import com.mlinc.mlplanets.domain.util.Triangle2D;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import javax.transaction.NotSupportedException;
import java.awt.geom.Point2D;
import java.util.List;

public class WeatherPredictionByPlanetAligmnent implements WeatherPredictionStrategy {

    @SuppressWarnings("UnusedDeclaration")
    private static final Logger log = LoggerFactory.getLogger(WeatherPredictionByPlanetAligmnent.class);

    private static final int SUPPORTED_OBJECTS_QUANTITY = 3;

    @Autowired
    CelestialObjectService coService;

    @Override
    public WeatherType predict(long day, List<CelestialObject> celestialObjects) throws NotSupportedException {

        if (celestialObjects.size() != SUPPORTED_OBJECTS_QUANTITY) {
            throw new NotSupportedException("La estrategia de prediccion actual solo soporta sistemas con " +
                    SUPPORTED_OBJECTS_QUANTITY + "planetas.");
        }

        Point2D[] todayPositionsPoints = getPositionsAsPointsArray(day, celestialObjects);
        Triangle2D todayTriangle = new Triangle2D(todayPositionsPoints);

        Point2D[] tomorrowPositionsPoints = getPositionsAsPointsArray(day + 1, celestialObjects);
        Triangle2D tomorrowTriangle = new Triangle2D(tomorrowPositionsPoints);

        WeatherType result;

        if (areObjectsAligned(todayTriangle, tomorrowTriangle)) {
            if (isObjectsAlignedWithOrbitCenter(todayTriangle, tomorrowTriangle)) {
                result = WeatherType.DROUGHT;
            } else {
                result = WeatherType.OPTIMAL;
            }
        } else if (todayTriangle.contains(new Point2D.Double(0, 0))) {
            if (isCyclicSystem(celestialObjects) && isTriangleWithMaxPerimeter(todayTriangle, celestialObjects)) {
                result = WeatherType.MAX_INTENSITY_RAIN;
            } else {
                result = WeatherType.RAIN;
            }
        } else {
            result = WeatherType.NORMAL;
        }

        return result;
    }

    private int getMaxOrbitPeriod(List<CelestialObject> celestialObjects) {

        int result = -1;

        for (CelestialObject celestialObject : celestialObjects) {

            int period = 360 / Math.abs(celestialObject.getOrbit().getAngularSpeed());

            if (period > result) {
                result = period;
            }
        }

        return result;
    }

    private double getMaxTrianglePerimeter(List<CelestialObject> celestialObjects) throws NotSupportedException {

        int maxPeriod = getMaxOrbitPeriod(celestialObjects);
        double result = -1;

        for (int i = 0; i < maxPeriod; ++i) {
            Point2D[] todayPositionsPoints = getPositionsAsPointsArray(i, celestialObjects);
            Triangle2D todayTriangle = new Triangle2D(todayPositionsPoints);
            if (todayTriangle.getPerimeter() - result > Triangle2D.EPSILON) {
                result = todayTriangle.getPerimeter();
            }
        }

        return result;
    }

    private boolean isCyclicSystem(List<CelestialObject> celestialObjects) {

        int maxPeriod = getMaxOrbitPeriod(celestialObjects);

        for (CelestialObject celestialObject : celestialObjects) {

            int period = 360 / Math.abs(celestialObject.getOrbit().getAngularSpeed());

            if (maxPeriod % period != 0) {
                return false;
            }
        }

        return true;
    }

    private boolean isTriangleWithMaxPerimeter(Triangle2D triangle, List<CelestialObject> celestialObjects) throws NotSupportedException {
        return (Math.abs(triangle.getPerimeter() - getMaxTrianglePerimeter(celestialObjects)) < Triangle2D.EPSILON);
    }

    private Point2D[] getPositionsAsPointsArray(long day, List<CelestialObject> celestialObjects) {

        Point2D[] positionsList = new Point2D[SUPPORTED_OBJECTS_QUANTITY];

        for (int i = 0; i < celestialObjects.size(); ++i) {

            if (celestialObjects.get(i).getOrbit().getDistance() != 0) {
                positionsList[i] = coService.getPositionForDay(celestialObjects.get(i), day);
            }
        }

        return positionsList;
    }

    private boolean areObjectsAligned(Triangle2D todayTriangle, Triangle2D tomorrowTriangle) {

        return
                (Math.abs(todayTriangle.getArea()) <= Triangle2D.EPSILON) ||
                (
                        (Math.abs(tomorrowTriangle.getArea()) > Triangle2D.EPSILON) &&
                                (Math.signum(todayTriangle.getArea()) != Math.signum(tomorrowTriangle.getArea()))
                );
    }

    private boolean isObjectsAlignedWithOrbitCenter(Triangle2D todayTriangle, Triangle2D tomorrowTriangle) {

        if (Math.abs(tomorrowTriangle.getArea()) <= Triangle2D.EPSILON) {
            return false;
        } else {

            Triangle2D tempTodayTriangle = new Triangle2D(todayTriangle);
            Triangle2D tempTomorrowTriangle = new Triangle2D(tomorrowTriangle);

            for (int i = 0; i < 3; ++i) {

                tempTodayTriangle.setPoints(todayTriangle.getPoints());
                tempTodayTriangle.getPoints()[i].setLocation(0.0, 0.0);

                tempTomorrowTriangle.setPoints(tomorrowTriangle.getPoints());
                tempTomorrowTriangle.getPoints()[i].setLocation(0.0, 0.0);

                if (Math.signum(tempTodayTriangle.getArea()) != Math.signum(tempTomorrowTriangle.getArea())) {
                    return true;
                }
            }

            return false;
        }
    }

}
