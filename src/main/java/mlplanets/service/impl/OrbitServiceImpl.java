package mlplanets.service.impl;

import mlplanets.domain.Orbit;
import mlplanets.service.OrbitService;

import java.awt.geom.Point2D;

public class OrbitServiceImpl implements OrbitService {

    private static final int CIRCLE_DEGRESS = 360;

    @Override
    public Point2D getPositionForDay(Orbit orbit, int day) {
        return new Point2D.Double(
                orbit.getDistance() * Math.cos(getOrbitAngleInRadians(orbit, day)),
                orbit.getDistance() * Math.sin(getOrbitAngleInRadians(orbit, day))
        );
    }

    @Override
    public int getYearDurationInDays(Orbit orbit) {
        // 360 grados / velocidad angular en grados
        return CIRCLE_DEGRESS / orbit.getAngularSpeed();
    }

    private double getOrbitAngleInRadians(Orbit orbit, int day) {
        return Math.toRadians(orbit.getAngularSpeed() * day);
    }
}
