package mlplanets;

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
        ApplicationContext context = new ClassPathXmlApplicationContext(new String[] {"application_context.xml"});
        Application application = context.getBean(Application.class);
        application.run();
    }

    @Autowired
    private ApplicationInitializer initializer;

    private void run() {
        initializer.initialize();
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
