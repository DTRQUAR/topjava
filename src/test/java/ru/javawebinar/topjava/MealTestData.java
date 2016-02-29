package ru.javawebinar.topjava;

import ru.javawebinar.topjava.matcher.ModelMatcher;
import ru.javawebinar.topjava.model.UserMeal;
import ru.javawebinar.topjava.util.TimeUtil;

import java.time.LocalDateTime;

import static ru.javawebinar.topjava.model.BaseEntity.START_SEQ;

/**
 * GKislin
 * 13.03.2015.
 */
public class MealTestData {

    public static final ModelMatcher<UserMeal, String> MATCHER = new ModelMatcher<>(UserMeal::toString);

    public static final int MEAL_ID_1 = 100000;
    public static final int MEAL_ID_2 = MEAL_ID_1 + 1;
    public static final int MEAL_ID_3 = MEAL_ID_2 + 1;
    public static final int MEAL_ID_4 = MEAL_ID_3 + 1;

    public static final int USER_ID = 100000;
    public static final int ADMIN_ID = USER_ID + 1;

    public static  UserMeal UserMeal_1 = new UserMeal(MEAL_ID_1, LocalDateTime.parse("2016-02-26 11:00", TimeUtil.DATE_TME_FORMATTER), "Завтрак", 560);

    public static  UserMeal UserMeal_2 = new UserMeal(MEAL_ID_2, LocalDateTime.parse("2016-02-26 14:00", TimeUtil.DATE_TME_FORMATTER), "Обед", 400);

    public static  UserMeal UserMeal_3 = new UserMeal(MEAL_ID_3, LocalDateTime.parse("2016-02-25 18:00", TimeUtil.DATE_TME_FORMATTER), "Ужин", 460);

    public static  UserMeal UserMeal_4 = new UserMeal(MEAL_ID_4, LocalDateTime.parse("2016-02-25 19:00", TimeUtil.DATE_TME_FORMATTER), "Ужин", 520);

}
