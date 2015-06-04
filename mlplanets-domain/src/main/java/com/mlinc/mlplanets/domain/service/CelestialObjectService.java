package com.mlinc.mlplanets.domain.service;

import com.mlinc.mlplanets.domain.model.CelestialObject;

import java.awt.geom.Point2D;

public interface CelestialObjectService {

    Point2D getPositionForDay(CelestialObject object, long day);

}
