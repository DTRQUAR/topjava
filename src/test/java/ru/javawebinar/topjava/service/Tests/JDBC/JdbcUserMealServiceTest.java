package ru.javawebinar.topjava.service.Tests.JDBC;

import org.springframework.test.context.ActiveProfiles;
import ru.javawebinar.topjava.service.AbstractUserMealTestClass;
import ru.javawebinar.topjava.Profiles;

/**
 * Created by Qouer on 10.03.2016.
 */

@ActiveProfiles(Profiles.jdbc_postgres)
//@ActiveProfiles(Profiles.jdbc_hsqldb)
public class JdbcUserMealServiceTest extends AbstractUserMealTestClass {
}
