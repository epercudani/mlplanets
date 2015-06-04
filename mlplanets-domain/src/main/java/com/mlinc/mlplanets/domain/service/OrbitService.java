package com.mlinc.mlplanets.domain.service;

import com.mlinc.mlplanets.domain.model.Orbit;

import java.awt.geom.Point2D;

public interface OrbitService {

    Point2D getPositionForDay(Orbit orbit, long day);

    long getYearDurationInDays(Orbit orbit);

}
