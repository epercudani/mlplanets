package com.mlinc.mlplanets.domain.model;

import javax.persistence.Embeddable;

@Embeddable
public class Orbit {

    private Double distance;

    private Integer angularSpeed;

    public Double getDistance() {
        return distance;
    }

    public void setDistance(Double distance) {
        this.distance = distance;
    }

    public Integer getAngularSpeed() {
        return angularSpeed;
    }

    public void setAngularSpeed(Integer angularSpeed) {
        this.angularSpeed = angularSpeed;
    }

}
