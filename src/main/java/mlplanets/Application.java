package mlplanets;

import mlplanets.dao.SolarSystemDAO;
import mlplanets.domain.SolarSystem;
import mlplanets.service.SolarSystemService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.stereotype.Component;

@Component
public class Application {

    private static final Logger log = LoggerFactory.getLogger(Application.class);

    public static void main(String args[]) {

        if (args.length == 1) {
            ApplicationContext context = new ClassPathXmlApplicationContext(new String[] {"application_context.xml"});
            Application application = context.getBean(Application.class);
            application.run();
        } else {
            throw new IllegalArgumentException("Must provide solar system name");
        }
    }

    @Autowired
    private ApplicationInitializer initializer;

    @Autowired
    private SolarSystemService ssService;

    public void run() {
        initializer.initialize();
        ssService.predictWeatherForSystem("ML");
    }

    /*
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
    */
}
