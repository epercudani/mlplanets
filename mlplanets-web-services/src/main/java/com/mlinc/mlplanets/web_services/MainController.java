package com.mlinc.mlplanets.web_services;

import com.mlinc.mlplanets.domain.dao.PredictionDAO;
import com.mlinc.mlplanets.domain.service.SolarSystemService;
import com.mlinc.mlplanets.transport.PredictionDTO;
import com.mlinc.mlplanets.transport.PredictionSummaryDTO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

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
    public ResponseEntity<PredictionDTO> predictionForDay(@PathVariable long day)throws Exception {

        PredictionDTO prediction = solarSystemService.getPredictionForDay(day);

        if (prediction != null) {

            return new ResponseEntity<>(prediction, HttpStatus.OK);
        }

        return null;
    }

    @RequestMapping(value = "prediction-summary", method = RequestMethod.GET)
    public ResponseEntity<PredictionSummaryDTO> predictionSummary() throws Exception {

        PredictionSummaryDTO predictionSummaryDTO = solarSystemService.getPredictionSummarySince(0L);

        return new ResponseEntity<>(predictionSummaryDTO, HttpStatus.OK);
    }

    @RequestMapping(value = "prediction-summary-since/{day}", method = RequestMethod.GET)
    public ResponseEntity<PredictionSummaryDTO> predictionSummary(@PathVariable long day) throws Exception {

        PredictionSummaryDTO predictionSummaryDTO = solarSystemService.getPredictionSummarySince(day);

        return new ResponseEntity<>(predictionSummaryDTO, HttpStatus.OK);
    }
}
