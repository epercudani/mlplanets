package com.mlinc.mlplanets.domain;

import com.mlinc.mlplanets.domain.service.SolarSystemService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.stereotype.Component;

@Component
public class Application {

    @SuppressWarnings("UnusedDeclaration")
    private static final Logger log = LoggerFactory.getLogger(Application.class);

    public static void main(String args[]) {

        if (args.length >= 1) {
            ApplicationContext context = new ClassPathXmlApplicationContext(new String[] {"domain-context.xml"});
            Application application = context.getBean(Application.class);

            String solarSystemName = args[0];
            long startFromDay = 0;
            if (args.length == 2) {
                startFromDay = Long.valueOf(args[1]);
            }
            application.run(solarSystemName, startFromDay);
        } else {
            throw new IllegalArgumentException("Must provide solar system name");
        }
    }

    @Autowired
    @SuppressWarnings("UnusedDeclaration")
    private ApplicationInitializer initializer;

    @Autowired
    @SuppressWarnings("UnusedDeclaration")
    private SolarSystemService ssService;

    public void run(String solarSystemName, long startingDay) {
        initializer.initialize();
        //ssService.predictWeatherForSystem(solarSystemName, startingDay);
    }
}
