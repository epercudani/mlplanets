package mlplanets;

import mlplanets.dao.CelestialObjectDAO;
import mlplanets.dao.SolarSystemDAO;
import mlplanets.domain.CelestialObject;
import mlplanets.domain.SolarSystem;
import mlplanets.service.CelestialObjectService;
import mlplanets.service.OrbitService;
import mlplanets.util.CelestialObjectFactory;
import mlplanets.util.OrbitFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

public class ApplicationInitializer {

    @Autowired
    private SolarSystemDAO ssDAO;

    @Autowired
    private CelestialObjectDAO coDAO;

    @Autowired
    private CelestialObjectService coService;

    @Autowired
    private OrbitService orbitService;

    @Transactional()
    public void initialize() {

        SolarSystem ss = null;
        SolarSystem solarSystem = ssDAO.findByName("ML");

        if (solarSystem == null) {
            ss = new SolarSystem();
            ss.setName("ML");
            ssDAO.add(ss);
        }

        CelestialObject co;

        co = coDAO.findByName("Sol");
        if (co == null) {
            co = CelestialObjectFactory.create("Sol", OrbitFactory.create(0, 0), ss);
            coDAO.add(co);
        }

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
    }
}