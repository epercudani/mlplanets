package com.mlinc.mlplanets.domain.service.impl;

import com.mlinc.mlplanets.domain.model.CelestialObject;
import com.mlinc.mlplanets.domain.service.CelestialObjectService;
import com.mlinc.mlplanets.domain.service.OrbitService;
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
