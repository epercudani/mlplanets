package com.mlinc.mlplanets.domain.service.impl;

import com.mlinc.mlplanets.domain.dao.PredictionDAO;
import com.mlinc.mlplanets.domain.dao.SolarSystemDAO;
import com.mlinc.mlplanets.domain.enums.WeatherType;
import com.mlinc.mlplanets.domain.model.Prediction;
import com.mlinc.mlplanets.domain.model.SolarSystem;
import com.mlinc.mlplanets.domain.service.WeatherPredictionStrategy;
import com.mlinc.mlplanets.domain.model.CelestialObject;
import com.mlinc.mlplanets.domain.service.OrbitService;
import com.mlinc.mlplanets.domain.service.SolarSystemService;
import com.mlinc.mlplanets.transport.PredictionDTO;
import com.mlinc.mlplanets.transport.PredictionSummaryDTO;
import com.mlinc.mlplanets.transport.TransformUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import javax.transaction.NotSupportedException;
import java.util.List;
import java.util.TimeZone;

@Transactional(readOnly = false)
public class SolarSystemServiceImpl implements SolarSystemService {

    private static final Logger log = LoggerFactory.getLogger(SolarSystemServiceImpl.class);

    private static final int YEARS_TO_PREDICT = 10;

    @Autowired
    @SuppressWarnings("UnusedDeclaration")
    private WeatherPredictionStrategy weatherPredictionStrategy;

    @Autowired
    @SuppressWarnings("UnusedDeclaration")
    private SolarSystemDAO solarSystemDAO;

    @Autowired
    @SuppressWarnings("UnusedDeclaration")
    private OrbitService orbitService;

    @Autowired
    @SuppressWarnings("UnusedDeclaration")
    private PredictionDAO predictionDAO;

    @Override
    public void predictWeatherForSystem(SolarSystem solarSystem, long startingDay) {

        long daysInLongesYear = getDaysInLongestYearForSystem(solarSystem);
        Long lastPredictedDay = predictionDAO.getLastPredictedDay(solarSystem);
        if (lastPredictedDay == null) {
            lastPredictedDay = -1L;
        }
        long predictUpTo = startingDay + (daysInLongesYear * YEARS_TO_PREDICT);

        for (long i = lastPredictedDay + 1; i <= predictUpTo; ++i) {

            try {
                createPredictionForDay(solarSystem, i);
            } catch (NotSupportedException e) {
                e.printStackTrace();
                break;
            }
        }
    }

    private void createPredictionForDay(SolarSystem solarSystem, long day) throws NotSupportedException {

        WeatherType weatherType = weatherPredictionStrategy.predict(day, solarSystem.getCelestialObjects());

        Prediction prediction = predictionDAO.findByDay(solarSystem, day);
        if (prediction == null) {
            prediction = new Prediction();
        }

        prediction.setSolarSystem(solarSystem);
        prediction.setDay(day);
        prediction.setWeatherType(weatherType);

        predictionDAO.addOrUpdate(prediction);
    }

    @Override
    public void predictWeatherForSystem(long startingDay) throws NotSupportedException {
        predictWeatherForSystem(getUniqueSolarSystem(), startingDay);
    }

    @Override
    public void predictWeatherForNextDay() throws NotSupportedException {

        SolarSystem solarSystem = getUniqueSolarSystem();

        TimeZone tz = TimeZone.getTimeZone("America/Argentina/Buenos_Aires");
        log.info(tz.getDisplayName());
        Long lastPredictedDay = predictionDAO.getLastPredictedDay(solarSystem);

        log.info("Predicting weather for day " + (lastPredictedDay + 1));

        createPredictionForDay(getUniqueSolarSystem(), lastPredictedDay + 1);
    }

    @Override
    public PredictionDTO getPredictionForDay(SolarSystem solarSystem, long day) {
        return TransformUtils.transformToPredictionDTOFromPrediction(predictionDAO.findByDay(solarSystem, day));
    }

    @Override
    public PredictionDTO getPredictionForDay(long day) throws NotSupportedException {
        return getPredictionForDay(getUniqueSolarSystem(), day);
    }

    @Override
    public PredictionSummaryDTO getPredictionSummarySince(SolarSystem solarSystem, long startingDay) {

        long endDay = startingDay + getDaysInLongestYearForSystem(solarSystem) * YEARS_TO_PREDICT;

        predictWeatherForSystem(solarSystem, startingDay);

        PredictionSummaryDTO predictionSummaryDTO = new PredictionSummaryDTO();
        predictionSummaryDTO.setPeriodosDeSequia(predictionDAO.getQuantityOfDaysForTypeBetween(solarSystem, WeatherType.DROUGHT, startingDay, endDay));
        predictionSummaryDTO.setPeriodosDeLluvia(predictionDAO.getQuantityOfDaysForTypeBetween(solarSystem, WeatherType.RAIN, startingDay, endDay));
        predictionSummaryDTO.setPeriodosDeClimaOptimo(predictionDAO.getQuantityOfDaysForTypeBetween(solarSystem, WeatherType.OPTIMAL, startingDay, endDay));

        List<Long> daysList = predictionDAO.getDaysOfMaxRain(solarSystem, startingDay, endDay);
        Long[] daysArray = new Long[daysList.size()];
        daysList.toArray(daysArray);

        predictionSummaryDTO.setDiasDeMaximasLluvias(daysArray);

        return predictionSummaryDTO;
    }

    @Override
    public PredictionSummaryDTO getPredictionSummarySince(long startingDay) throws NotSupportedException {
        return getPredictionSummarySince(getUniqueSolarSystem(), startingDay);
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

    private SolarSystem getUniqueSolarSystem() throws NotSupportedException {
        SolarSystem solarSystem = solarSystemDAO.findUnique();

        if (solarSystem == null) {
            throw new NotSupportedException("Existe mas de un sistema solar definido");
        }

        return solarSystem;
    }
}
