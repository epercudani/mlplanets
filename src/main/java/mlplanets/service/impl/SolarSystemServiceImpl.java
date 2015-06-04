package mlplanets.service.impl;

import mlplanets.dao.PredictionDAO;
import mlplanets.dao.SolarSystemDAO;
import mlplanets.domain.CelestialObject;
import mlplanets.domain.SolarSystem;
import mlplanets.domain.WeatherPredictionStrategy;
import mlplanets.service.OrbitService;
import mlplanets.service.SolarSystemService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import javax.transaction.NotSupportedException;

public class SolarSystemServiceImpl implements SolarSystemService {

    private static final Logger log = LoggerFactory.getLogger(SolarSystemServiceImpl.class);

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
    @Transactional(readOnly = true)
    public void predictWeatherForSystem(String name, long startingDay) {

        SolarSystem solarSystem = ssDAO.findByName(name);

        if (solarSystem == null) {
            throw new IllegalArgumentException("El sistema solar " + name + "no existe");
        }

        long daysInLongesYear = getDaysInLongestYearForSystem(solarSystem);
        Long lastPredictedDay = predictionDAO.getLastPredictedDay(solarSystem);
        if (lastPredictedDay == null) {
            lastPredictedDay = 0L;
        }
        long predictUpTo = startingDay + (daysInLongesYear * 10);

        for (long i = lastPredictedDay; i <= predictUpTo; ++i) {

            log.info("Prediction for day " + i + ":");

            try {
                weatherPredictionStrategy.predict(i, solarSystem.getCelestialObjects());
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
