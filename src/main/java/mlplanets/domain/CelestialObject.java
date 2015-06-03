package mlplanets.domain;

import javax.persistence.*;

@javax.persistence.Entity
public class CelestialObject extends Entity {

    @Column(unique = true, nullable = false)
    private String name;

    @Embedded
    @Column(nullable = false)
    private Orbit orbit;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "solarSystemId", nullable = false, updatable = false, foreignKey = @ForeignKey(name = "FK_CelObj_SolarSyst"))
    private SolarSystem solarSystem;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Orbit getOrbit() {
        return orbit;
    }

    public void setOrbit(Orbit orbit) {
        this.orbit = orbit;
    }

    public SolarSystem getSolarSystem() {
        return solarSystem;
    }

    public void setSolarSystem(SolarSystem solarSystem) {
        this.solarSystem = solarSystem;
    }

}
