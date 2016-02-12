package ru.javawebinar.topjava.util;

import ru.javawebinar.topjava.model.UserMeal;
import ru.javawebinar.topjava.model.UserMealWithExceed;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.Month;
import java.util.*;

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
        List<UserMealWithExceed> resultList = getFilteredMealsWithExceeded(mealList, LocalTime.of(7, 0), LocalTime.of(12,0), 2000);

        //Выводим результат в консоль
        for (int i = 0; i < resultList.size(); i++){
            System.out.println(resultList.get(i).getDateTime() + "; " + resultList.get(i).getDescription()
                    + "; " + resultList.get(i).getCalories() + "; " + resultList.get(i).isExceed());
        }
    }

    public static List<UserMealWithExceed> getFilteredMealsWithExceeded(List<UserMeal> mealList, LocalTime startTime, LocalTime endTime, int caloriesPerDay) {
        List<UserMealWithExceed> mealWithExceeds = new ArrayList<UserMealWithExceed>();
        Map<LocalDate, Integer> newMeals = new HashMap<LocalDate, Integer>();
        //Считаем калории для каждого дня
        for (int i = 0; i < mealList.size(); i++){
            LocalDate mealDate = mealList.get(i).getDateTime().toLocalDate();
            if (!newMeals.containsKey(mealDate)){
                newMeals.put(mealDate, mealList.get(i).getCalories());
            }else{
                newMeals.replace(mealDate, newMeals.get(mealDate), newMeals.get(mealDate) + mealList.get(i).getCalories());
            }
        }

        //Формируем конечный список, с указанием превышения калорий
        for (int i = 0; i < mealList.size(); i++){
            if (TimeUtil.isBetween(mealList.get(i).getDateTime().toLocalTime(), startTime, endTime)){
                mealWithExceeds.add(new UserMealWithExceed(mealList.get(i).getDateTime(), mealList.get(i).getDescription(), mealList.get(i).getCalories(),
                        newMeals.get(mealList.get(i).getDateTime().toLocalDate()) > caloriesPerDay));
            }
        }
        return mealWithExceeds;
    }
}
