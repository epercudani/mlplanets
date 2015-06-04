package com.mlinc.mlplanets.transport;

import com.mlinc.mlplanets.domain.model.Prediction;

public class TransformUtils {

    public static PredictionDTO transformToPredictionDTOFromPrediction(Prediction prediction) {

        PredictionDTO predictionDTO = new PredictionDTO();
        predictionDTO.setSolarSystem(prediction.getSolarSystem().getName());
        predictionDTO.setDay(prediction.getDay());
        predictionDTO.setWeatherType(prediction.getWeatherType().toString());

        return predictionDTO;
    }
}
