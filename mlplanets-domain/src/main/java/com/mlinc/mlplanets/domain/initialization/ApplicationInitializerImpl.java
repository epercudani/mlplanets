package com.mlinc.mlplanets.domain.initialization;

import com.mlinc.mlplanets.domain.Application;
import com.mlinc.mlplanets.domain.dao.CelestialObjectDAO;
import com.mlinc.mlplanets.domain.dao.SolarSystemDAO;
import com.mlinc.mlplanets.domain.model.CelestialObject;
import com.mlinc.mlplanets.domain.model.SolarSystem;
import com.mlinc.mlplanets.domain.service.CelestialObjectService;
import com.mlinc.mlplanets.domain.service.OrbitService;
import com.mlinc.mlplanets.domain.service.SolarSystemService;
import com.mlinc.mlplanets.domain.util.CelestialObjectFactory;
import com.mlinc.mlplanets.domain.util.OrbitFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.transaction.annotation.Transactional;

public class ApplicationInitializerImpl implements ApplicationListener<ContextRefreshedEvent>, ApplicationInitializer {

    private static Logger log = LoggerFactory.getLogger(ApplicationInitializerImpl.class);

    // Referencia a si mismo pero con aspectos
    private ApplicationInitializer self;

    boolean initialized = false;

    private Object lock = new Object();

    @Autowired
    private SolarSystemDAO ssDAO;

    @Autowired
    private CelestialObjectDAO coDAO;

    @Autowired
    private CelestialObjectService coService;

    @Autowired
    private OrbitService orbitService;

    @Autowired
    SolarSystemService ssService;

    @Override
    public void onApplicationEvent(ContextRefreshedEvent event) {

        synchronized (lock) {
            if (initialized) {
                return;
            }

            initialized = true;
        }

        log.info("Application context started");

        if (self == null)
            self = event.getApplicationContext().getBean(ApplicationInitializer.class);

        self.initialize();
    }

    @Override
    @Transactional
    public void initialize() {

        SolarSystem ss = ssDAO.findByName("ML");

        if (ss == null) {
            ss = new SolarSystem();
            ss.setName("ML");
            ssDAO.add(ss);
        }

        CelestialObject co;

        co = coDAO.findByName("Ferengi");
        if (co == null) {
            co = CelestialObjectFactory.create("Ferengi", OrbitFactory.create(500, -1), ss);
            coDAO.add(co);
        }

        co = coDAO.findByName("Betasoide");
        if (co == null) {
            co = CelestialObjectFactory.create("Betasoide", OrbitFactory.create(2000, -3), ss);
            coDAO.add(co);
        }

        co = coDAO.findByName("Vulcano");
        if (co == null) {
            co = CelestialObjectFactory.create("Vulcano", OrbitFactory.create(1000, 5), ss);
            coDAO.add(co);
        }

        ssDAO.update(ss);

        try {
            ssService.predictWeatherForSystem(0L);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
