package ru.javawebinar.topjava.service.Tests.JPA;

import org.springframework.test.context.ActiveProfiles;
import ru.javawebinar.topjava.service.AbstractUserTestClass;
import ru.javawebinar.topjava.Profiles;

/**
 * Created by Qouer on 10.03.2016.
 */

@ActiveProfiles(Profiles.jpa_postgres)
//@ActiveProfiles(Profiles.jpa_hsqldb)
public class JpaUserServiceTest extends AbstractUserTestClass {
}
