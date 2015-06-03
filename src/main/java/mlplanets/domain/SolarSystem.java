package mlplanets.domain;

import mlplanets.enums.WeatherType;

import javax.persistence.Column;
import javax.persistence.FetchType;
import javax.persistence.OneToMany;
import javax.persistence.Transient;
import java.util.HashSet;
import java.util.Set;

@javax.persistence.Entity
public class SolarSystem extends Entity {

    @Transient
    WeatherPredictionStrategy weatherPredictionStrategy = new WeatherPredictionByAligmnent();

    @Column(nullable = false)
    public String name;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "solarSystem")
    public Set<CelestialObject> celestialObjects = new HashSet<CelestialObject>();

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Set<CelestialObject> getCelestialObjects() {
        return celestialObjects;
    }

    public void setCelestialObjects(Set<CelestialObject> celestialObjects) {
        this.celestialObjects = celestialObjects;
    }

    public WeatherType predictWeather(int day) {
        return weatherPredictionStrategy.predict(day, celestialObjects);
    }
}
