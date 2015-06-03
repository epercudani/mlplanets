package mlplanets.service;

import mlplanets.domain.Orbit;

import java.awt.geom.Point2D;

public interface OrbitService {

    Point2D getPositionForDay(Orbit orbit, int day);

    int getYearDurationInDays(Orbit orbit);

}
