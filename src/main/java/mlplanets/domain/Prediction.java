package mlplanets.domain;

import mlplanets.enums.WeatherType;
import org.hibernate.annotations.Type;
import org.joda.time.LocalDateTime;

import javax.persistence.*;

@javax.persistence.Entity
public class Prediction extends Entity {

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "solarSystemId", nullable = false, updatable = false, foreignKey = @ForeignKey(name = "FK_Prediction_SolarSyst"))
    private SolarSystem solarSystem;

    @Type(type="org.jadira.usertype.dateandtime.joda.PersistentLocalDateTime")
    private Long day;

    @Enumerated
    private WeatherType weatherType;

    public SolarSystem getSolarSystem() {
        return solarSystem;
    }

    public void setSolarSystem(SolarSystem solarSystem) {
        this.solarSystem = solarSystem;
    }

    public Long getDay() {
        return day;
    }

    public void setDay(LocalDateTime day) {
        this.day = this.day;
    }

    public WeatherType getWeatherType() {
        return weatherType;
    }

    public void setWeatherType(WeatherType weatherType) {
        this.weatherType = weatherType;
    }
}
