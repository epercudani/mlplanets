package mlplanets;

import mlplanets.dao.CelestialObjectDAO;
import mlplanets.dao.SolarSystemDAO;
import mlplanets.dao.impl.SolarSystemDAOImpl;
import mlplanets.domain.CelestialObject;
import mlplanets.domain.SolarSystem;
import mlplanets.service.CelestialObjectService;
import mlplanets.service.OrbitService;
import mlplanets.util.CelestialObjectFactory;
import mlplanets.util.OrbitFactory;
import org.hibernate.Transaction;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.Set;

@Component
public class Application {

    private static final Logger log = LoggerFactory.getLogger(Application.class);

    public static void main(String args[]) {
        ApplicationContext context = new ClassPathXmlApplicationContext(new String[] {"application_context.xml"});
        Application application = context.getBean(Application.class);
        application.run();
    }

    @Autowired
    SolarSystemDAO ssDAO;

    @Autowired
    CelestialObjectDAO coDAO;

    @Autowired
    CelestialObjectService coService;

    @Autowired
    OrbitService orbitService;

    private void run() {
        initializeCelestialObjects();
        test();
    }

    @Transactional
    private void initializeCelestialObjects() {

        System.out.println("ssDAO " + ssDAO);
        System.out.println("coDAO " + coDAO);

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

    @Transactional
    public void test() {

        SolarSystem ss = ssDAO.getAll().get(0);

        Set<CelestialObject> celestialObjects = ss.getCelestialObjects();
        for (CelestialObject celestialObject : celestialObjects) {
            if (celestialObject.getOrbit().getDistance() != 0) {
                for (int i = 0; i <= orbitService.getYearDurationInDays(celestialObject.getOrbit()); ++i) {
                    log.info(
                            celestialObject.getName() + " is at " + coService.getPositionForDay(celestialObject, i) + " on day " + i
                    );
                }
            }
        }
    }
}
