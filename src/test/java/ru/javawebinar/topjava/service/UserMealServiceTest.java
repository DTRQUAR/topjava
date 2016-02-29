package ru.javawebinar.topjava.service;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import ru.javawebinar.topjava.model.User;
import ru.javawebinar.topjava.model.UserMeal;
import ru.javawebinar.topjava.util.DbPopulator;
import ru.javawebinar.topjava.util.TimeUtil;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;

import static ru.javawebinar.topjava.MealTestData.*;

import static org.junit.Assert.*;

/**
 * Created by Qouer on 29.02.2016.
 */
@ContextConfiguration({
        "classpath:spring/spring-app.xml",
        "classpath:spring/spring-db.xml"
})
@RunWith(SpringJUnit4ClassRunner.class)
public class UserMealServiceTest {

    @Autowired
    protected UserMealService service;

    @Autowired
    private DbPopulator dbPopulator;

    @Before
    public void setUp() throws Exception {
        dbPopulator.execute();
    }
    @Test
    public void testGet() throws Exception {
        UserMeal userMeal = service.get(MEAL_ID_1, USER_ID);
        MATCHER.assertEquals(UserMeal_1, userMeal);
    }

    @Test
    public void testDelete() throws Exception {
        service.delete(MEAL_ID_1, USER_ID);
        MATCHER.assertCollectionEquals(Arrays.asList(UserMeal_3, UserMeal_2), service.getAll(USER_ID));
    }

    @Test
    public void testGetBetweenDates() throws Exception {
        Collection<UserMeal> all = service.getBetweenDates(LocalDate.parse("2016-02-26"), LocalDate.parse("2016-02-27"), 100000);
        MATCHER.assertCollectionEquals(Arrays.asList(UserMeal_2, UserMeal_1), all);
    }

    @Test
    public void testGetBetweenDateTimes() throws Exception {
        Collection<UserMeal> all = service.getBetweenDateTimes(LocalDateTime.parse("2016-02-25 10:00", TimeUtil.DATE_TME_FORMATTER),
                LocalDateTime.parse("2016-02-26 12:00", TimeUtil.DATE_TME_FORMATTER), 100000);
        MATCHER.assertCollectionEquals(Arrays.asList(UserMeal_3, UserMeal_1), all);
    }

    @Test
    public void testGetAll() throws Exception {
        Collection<UserMeal> all = service.getAll(100000);
        MATCHER.assertCollectionEquals(Arrays.asList(UserMeal_3, UserMeal_2, UserMeal_1), all);
    }

    @Test
    public void testUpdate() throws Exception {
        UserMeal actual = service.update(new UserMeal(100000, LocalDateTime.parse("2016-02-26 11:00", TimeUtil.DATE_TME_FORMATTER), "Завтрак666", 666), 100000);
        MATCHER.assertEquals(service.get(100000, 100000), actual);
    }

    @Test
    public void testSave() throws Exception {
        UserMeal actual = service.save(new UserMeal(LocalDateTime.parse("2016-02-26 11:00", TimeUtil.DATE_TME_FORMATTER), "Завтрак666", 666), 100000);
        MATCHER.assertEquals(service.get(100004, 100000), actual);
    }
}