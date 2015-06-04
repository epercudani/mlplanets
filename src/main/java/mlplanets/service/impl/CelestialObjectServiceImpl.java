package mlplanets.service.impl;

import mlplanets.domain.CelestialObject;
import mlplanets.service.CelestialObjectService;
import mlplanets.service.OrbitService;
import org.springframework.beans.factory.annotation.Autowired;

import java.awt.geom.Point2D;

public class CelestialObjectServiceImpl implements CelestialObjectService {

    @Autowired
    OrbitService orbitService;

    @Override
    public Point2D getPositionForDay(CelestialObject object, long day) {
        return orbitService.getPositionForDay(object.getOrbit(), day);
    }
}
