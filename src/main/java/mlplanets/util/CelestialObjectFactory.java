package mlplanets.util;

import mlplanets.domain.CelestialObject;
import mlplanets.domain.Orbit;
import mlplanets.domain.SolarSystem;

public class CelestialObjectFactory {

    public static CelestialObject create(String name, Orbit orbit, SolarSystem solarSystem) {
        CelestialObject celestialObject = new CelestialObject();
        celestialObject.setName(name);
        celestialObject.setOrbit(orbit);
        celestialObject.setSolarSystem(solarSystem);

        return celestialObject;
    }
}
