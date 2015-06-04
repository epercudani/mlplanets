package com.mlinc.mlplanets.domain.util;

import com.mlinc.mlplanets.domain.model.Orbit;

public class OrbitFactory {

    public static Orbit create(double distance, int angularSpeed) {
        Orbit orbit = new Orbit();
        orbit.setDistance(distance);
        orbit.setAngularSpeed(angularSpeed);

        return orbit;
    }
}
