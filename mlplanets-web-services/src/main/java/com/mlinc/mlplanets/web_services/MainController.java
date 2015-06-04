package com.mlinc.mlplanets.web_services;

import com.mlinc.mlplanets.domain.dao.PredictionDAO;
import com.mlinc.mlplanets.domain.model.Prediction;
import com.mlinc.mlplanets.domain.service.SolarSystemService;
import com.mlinc.mlplanets.transport.PredictionDTO;
import com.mlinc.mlplanets.transport.TransformUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("main")
@SuppressWarnings("UnusedDeclaration")
public class MainController {

    @SuppressWarnings("UnusedDeclaration")
    private Logger log = LoggerFactory.getLogger(MainController.class);

    @Autowired
    @SuppressWarnings("UnusedDeclaration")
    private SolarSystemService solarSystemService;

    @Autowired
    @SuppressWarnings("UnusedDeclaration")
    private PredictionDAO predictionDAO;

    @RequestMapping(value = "prediction-for-day/{day}", method = RequestMethod.GET)
    public ResponseEntity<PredictionDTO> predictionForDay(@PathVariable long day) {

        Prediction prediction = solarSystemService.getPredictionForDay(day);

        if (prediction != null) {
            PredictionDTO predictionDTO = TransformUtils.transformToPredictionDTOFromPrediction(prediction);
            return new ResponseEntity<>(predictionDTO, HttpStatus.OK);
        }

        return null;
    }


}
