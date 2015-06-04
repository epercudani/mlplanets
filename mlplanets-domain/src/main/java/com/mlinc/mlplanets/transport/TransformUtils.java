package com.mlinc.mlplanets.transport;

import com.mlinc.mlplanets.domain.model.Prediction;

public class TransformUtils {

    public static PredictionDTO transformToPredictionDTOFromPrediction(Prediction prediction) {

        if (prediction == null) {
            return null;
        }

        PredictionDTO predictionDTO = new PredictionDTO();
        predictionDTO.setSistemaSolar(prediction.getSolarSystem().getName());
        predictionDTO.setDia(prediction.getDay());
        predictionDTO.setClima(prediction.getWeatherType().toString());

        return predictionDTO;
    }
}
