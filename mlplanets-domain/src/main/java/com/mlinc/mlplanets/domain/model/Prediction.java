package com.mlinc.mlplanets.domain.model;

import com.mlinc.mlplanets.domain.enums.WeatherType;

import javax.persistence.*;

@javax.persistence.Entity
public class Prediction extends Entity {

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "solarSystemId", nullable = false, updatable = false, foreignKey = @ForeignKey(name = "FK_Prediction_SolarSyst"))
    private SolarSystem solarSystem;

    @Column(unique = true)
    private Long day;

    @Enumerated(EnumType.STRING)
    private WeatherType weatherType;

    public SolarSystem getSolarSystem() {
        return solarSystem;
    }

    public void setSolarSystem(SolarSystem solarSystem) {
        this.solarSystem = solarSystem;
    }

    public long getDay() {
        return day;
    }

    public void setDay(long day) {
        this.day = day;
    }

    public WeatherType getWeatherType() {
        return weatherType;
    }

    public void setWeatherType(WeatherType weatherType) {
        this.weatherType = weatherType;
    }
}
