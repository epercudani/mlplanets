package com.mlinc.mlplanets.domain.util;

import com.mlinc.mlplanets.domain.model.Orbit;
import com.mlinc.mlplanets.domain.model.SolarSystem;
import com.mlinc.mlplanets.domain.model.CelestialObject;

public class CelestialObjectFactory {

    public static CelestialObject create(String name, Orbit orbit, SolarSystem solarSystem) {
        CelestialObject celestialObject = new CelestialObject();
        celestialObject.setName(name);
        celestialObject.setOrbit(orbit);
        celestialObject.setSolarSystem(solarSystem);
        solarSystem.getCelestialObjects().add(celestialObject);

        return celestialObject;
    }
}
