package ru.javawebinar.topjava.web;

import ru.javawebinar.topjava.LoggerWrapper;
import ru.javawebinar.topjava.model.UserMeal;
import ru.javawebinar.topjava.model.UserMealWithExceed;
import ru.javawebinar.topjava.util.UserMealsUtil;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.time.LocalTime;

/**
 * Created by Qouer on 12.02.2016.
 */
public class MealServlet extends HttpServlet{
    private static final LoggerWrapper LOG = LoggerWrapper.get(MealServlet.class);

    private static String postStatus = "";

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        LOG.debug("redirect to mealList. POST");

        String descrition = request.getParameter("descrition");
        int calories = Integer.parseInt(request.getParameter("calories"));
        LOG.debug(Integer.toString(calories));

        int mealID = Integer.parseInt(request.getParameter("mealID"));
        LOG.debug(Integer.toString(mealID));

        int mealID_1 = Integer.parseInt(request.getParameter("mealID_1"));
        LOG.debug(Integer.toString(mealID_1));

        UserMealsUtil.storeMeals.get(mealID).setDescription(descrition);
        UserMealsUtil.storeMeals.get(mealID).setCalories(calories);

        UserMealsUtil.filteredMeals = UserMealsUtil.getFilteredMealsWithExceeded(UserMealsUtil.storeMeals, LocalTime.of(7, 0), LocalTime.of(12, 0), 2000);
        for (int i = 0; i < UserMealsUtil.filteredMeals.size(); i++) {
            LOG.debug(UserMealsUtil.filteredMeals.get(i).toString());
        }

        UserMealsUtil.mapUserMealWithExceed.replace(mealID_1,
                UserMealsUtil.filteredMeals.get(mealID_1));

        doGet(request, response);
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        LOG.debug("redirect to mealList");

        String action;
        int mealID;

        //Проверяем значение параметров
        if (request.getParameter("action") != null){
            action = request.getParameter("action");
            mealID = Integer.parseInt(request.getParameter("mealID"));
        }else{
            action = "";
            mealID = -1;
        }

        //Выполняем различные действия в зависимости от значения полученных параметров
        if (action.equals("edit")){ //Если нажали edit
            UserMealWithExceed mealWithExceed = UserMealsUtil.mapUserMealWithExceed.get(mealID);
            UserMeal userMeal;
            for (int i = 0; i < UserMealsUtil.storeMeals.size(); i++) {
                if ((mealWithExceed.getDateTime().equals(UserMealsUtil.storeMeals.get(i).getDateTime())) &&
                        (mealWithExceed.getDescription().equals(UserMealsUtil.storeMeals.get(i).getDescription())) &&
                                (mealWithExceed.getCalories() == UserMealsUtil.storeMeals.get(i).getCalories())){
                    request.setAttribute("mealToEdit", UserMealsUtil.storeMeals.get(i));
                    request.setAttribute("mealID", i);
                    request.setAttribute("mealID_1", mealID);
                }
            }
            request.getRequestDispatcher("/editMeal.jsp").forward(request, response);
        }else if (action.equals("delete")){ //Если нажали delete
            UserMealsUtil.mapUserMealWithExceed.remove(mealID);
            request.setAttribute("mapMeals", UserMealsUtil.mapUserMealWithExceed);
            request.getRequestDispatcher("/mealList.jsp").forward(request, response);
        }else{ //Если ничего не нажали
            request.setAttribute("mapMeals", UserMealsUtil.mapUserMealWithExceed);
            request.getRequestDispatcher("/mealList.jsp").forward(request, response);
        }
    }
}
