package mlplanets.service;

import mlplanets.domain.CelestialObject;

import java.awt.geom.Point2D;

public interface CelestialObjectService {

    Point2D getPositionForDay(CelestialObject object, int day);

}
