package mlplanets.dao;

import mlplanets.domain.Prediction;
import mlplanets.domain.SolarSystem;

public interface PredictionDAO extends DAO<Prediction> {

    Long getLastPredictedDay(SolarSystem solarSystem);
}
