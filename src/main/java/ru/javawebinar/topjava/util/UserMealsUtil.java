package ru.javawebinar.topjava.util;

import ru.javawebinar.topjava.model.UserMeal;
import ru.javawebinar.topjava.model.UserMealWithExceed;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.Month;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

/**
 * GKislin
 * 31.05.2015.
 */
public class UserMealsUtil {
    public static void main(String[] args) {
        List<UserMeal> mealList = Arrays.asList(
                new UserMeal(LocalDateTime.of(2015, Month.MAY, 30,10,0), "Завтрак", 500),
                new UserMeal(LocalDateTime.of(2015, Month.MAY, 30,13,0), "Обед", 1000),
                new UserMeal(LocalDateTime.of(2015, Month.MAY, 30,20,0), "Ужин", 500),
                new UserMeal(LocalDateTime.of(2015, Month.MAY, 31,10,0), "Завтрак", 1000),
                new UserMeal(LocalDateTime.of(2015, Month.MAY, 31,13,0), "Обед", 500),
                new UserMeal(LocalDateTime.of(2015, Month.MAY, 31,20,0), "Ужин", 510)
        );
        List<UserMealWithExceed> resultMeals= getFilteredMealsWithExceeded(mealList, LocalTime.of(7, 0), LocalTime.of(12,0), 2000);
        resultMeals.stream().map((s) -> new String(s.getDateTime() + " " + s.getDescription() + " " + s.getCalories() + " " + s.isExceed()))
                .collect(Collectors.toList()).forEach(System.out::println);
//        .toLocalDate();
//        .toLocalTime();
    }

    public static List<UserMealWithExceed>  getFilteredMealsWithExceeded(List<UserMeal> mealList, LocalTime startTime, LocalTime endTime, int caloriesPerDay) {
        return mealList.stream().map((s) -> new UserMealWithExceed(s.getDateTime(), s.getDescription(), s.getCalories(),
                mealList.stream().filter((c) -> (c.getDateTime().toLocalDate().equals(s.getDateTime().toLocalDate())))
                        .collect(Collectors.toList()).stream().mapToInt((g) -> g.getCalories()).sum() > caloriesPerDay))
                .filter((s)-> TimeUtil.isBetween(s.getDateTime().toLocalTime(), startTime, endTime))
                .collect(Collectors.toList());
    }
}
