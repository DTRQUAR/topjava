package ru.javawebinar.topjava.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.context.event.ContextStartedEvent;
import org.springframework.context.event.EventListener;
import org.springframework.core.env.ConfigurableEnvironment;
import org.springframework.stereotype.Component;
import ru.javawebinar.topjava.LoggerWrapper;
import ru.javawebinar.topjava.Profiles;

/**
 * Created by Qouer on 10.03.2016.
 */

@Component
public class ApplicationListenerBean implements ApplicationListener<ContextRefreshedEvent> {
    private static final LoggerWrapper LOG = LoggerWrapper.get(ApplicationListenerBean.class);

    public void onApplicationEvent(ContextRefreshedEvent event) {
        LOG.info("Try to setActiveProfiles!");
        ContextRefreshedEvent eventStart = event;
        ConfigurableEnvironment env = (ConfigurableEnvironment) eventStart.getApplicationContext().getEnvironment();
        env.setActiveProfiles(Profiles.datajpa_postgresql);
        System.err.println("!!!!!!!!!!!!!!!!!!!!!!!!!!!!!");
    }
}
