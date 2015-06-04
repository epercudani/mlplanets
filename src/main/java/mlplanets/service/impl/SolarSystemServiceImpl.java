package mlplanets.service.impl;

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
import java.util.List;
import java.util.Set;

public class SolarSystemServiceImpl implements SolarSystemService {

    private static final Logger log = LoggerFactory.getLogger(SolarSystemServiceImpl.class);

    @Autowired
    private WeatherPredictionStrategy weatherPredictionStrategy;

    @Autowired
    private SolarSystemDAO ssDAO;

    @Autowired
    private OrbitService orbitService;

    @Override
    @Transactional(readOnly = true)
    public void predictWeatherForSystem(String name) {

        SolarSystem solarSystem = ssDAO.findByName(name);

        if (solarSystem == null) {
            throw new IllegalArgumentException("El sistema solar " + name + "no existe");
        }

        int daysInLongestYear = getDaysInLongestYearForSystem(solarSystem);

        for (int i = 0; i <= daysInLongestYear; ++i) {

            log.info("Day " + i + ":");

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
    private int getDaysInLongestYearForSystem(SolarSystem system) {

        int daysInLongestYear = 0, daysInYear;

        for (CelestialObject object : system.getCelestialObjects()) {

            daysInYear = orbitService.getYearDurationInDays(object.getOrbit());

            if (daysInYear > daysInLongestYear) {
                daysInLongestYear = daysInYear;
            }
        }

        return daysInLongestYear;
    }
}
