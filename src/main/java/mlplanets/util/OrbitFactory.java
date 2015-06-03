package mlplanets.util;

import mlplanets.domain.Orbit;

public class OrbitFactory {

    public static Orbit create(double distance, int angularSpeed) {
        Orbit orbit = new Orbit();
        orbit.setDistance(distance);
        orbit.setAngularSpeed(angularSpeed);

        return orbit;
    }
}
