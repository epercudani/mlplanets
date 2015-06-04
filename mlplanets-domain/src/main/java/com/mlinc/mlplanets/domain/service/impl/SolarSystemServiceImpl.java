package com.mlinc.mlplanets.domain.service.impl;

import com.mlinc.mlplanets.domain.dao.PredictionDAO;
import com.mlinc.mlplanets.domain.dao.SolarSystemDAO;
import com.mlinc.mlplanets.domain.enums.WeatherType;
import com.mlinc.mlplanets.domain.model.Prediction;
import com.mlinc.mlplanets.domain.model.SolarSystem;
import com.mlinc.mlplanets.domain.model.WeatherPredictionStrategy;
import com.mlinc.mlplanets.domain.model.CelestialObject;
import com.mlinc.mlplanets.domain.service.OrbitService;
import com.mlinc.mlplanets.domain.service.SolarSystemService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import javax.transaction.NotSupportedException;

public class SolarSystemServiceImpl implements SolarSystemService {

    private static final Logger log = LoggerFactory.getLogger(SolarSystemServiceImpl.class);

    private static final int YEARS_TO_PREDICT = 10;

    @Autowired
    @SuppressWarnings("UnusedDeclaration")
    private WeatherPredictionStrategy weatherPredictionStrategy;

    @Autowired
    @SuppressWarnings("UnusedDeclaration")
    private SolarSystemDAO ssDAO;

    @Autowired
    @SuppressWarnings("UnusedDeclaration")
    private OrbitService orbitService;

    @Autowired
    @SuppressWarnings("UnusedDeclaration")
    private PredictionDAO predictionDAO;

    @Override
    @Transactional(readOnly = false)
    public void predictWeatherForSystem(String name, long startingDay) {

        SolarSystem solarSystem = ssDAO.findByName(name);

        if (solarSystem == null) {
            throw new IllegalArgumentException("El sistema solar " + name + "no existe");
        }

        long daysInLongesYear = getDaysInLongestYearForSystem(solarSystem);
        Long lastPredictedDay = predictionDAO.getLastPredictedDay(solarSystem);
        if (lastPredictedDay == null) {
            lastPredictedDay = -1L;
        }
        long predictUpTo = startingDay + (daysInLongesYear * YEARS_TO_PREDICT);

        for (long i = lastPredictedDay + 1; i <= predictUpTo; ++i) {

            try {
                WeatherType weatherType = weatherPredictionStrategy.predict(i, solarSystem.getCelestialObjects());

                Prediction prediction = predictionDAO.findByDay(solarSystem, i);
                if (prediction == null) {
                    prediction = new Prediction();
                }

                prediction.setSolarSystem(solarSystem);
                prediction.setDay(i);
                prediction.setWeatherType(weatherType);

                predictionDAO.addOrUpdate(prediction);
            } catch (NotSupportedException e) {
                log.error(e.getLocalizedMessage());
                break;
            }
        }
    }

    /*
        Obtiene la cantidad de dias que le toma al planeta con orbita mas larga dar una vuelta completa
            alrededor del centro.
     */
    private long getDaysInLongestYearForSystem(SolarSystem system) {

        long daysInLongestYear = 0, daysInYear;

        for (CelestialObject object : system.getCelestialObjects()) {

            daysInYear = orbitService.getYearDurationInDays(object.getOrbit());

            if (daysInYear > daysInLongestYear) {
                daysInLongestYear = daysInYear;
            }
        }

        return daysInLongestYear;
    }
}
