package com.mlinc.mlplanets.domain.model;

import javax.persistence.Column;
import javax.persistence.FetchType;
import javax.persistence.OneToMany;
import javax.persistence.OrderBy;
import java.util.ArrayList;
import java.util.List;

@javax.persistence.Entity
public class SolarSystem extends Entity {

    @Column(nullable = false)
    private String name;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "solarSystem")
    @OrderBy("orbit.distance")
    private List<CelestialObject> celestialObjects = new ArrayList<CelestialObject>();

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<CelestialObject> getCelestialObjects() {
        return celestialObjects;
    }

    public void setCelestialObjects(List<CelestialObject> celestialObjects) {
        this.celestialObjects = celestialObjects;
    }
}
