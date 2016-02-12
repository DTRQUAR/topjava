package ru.javawebinar.topjava.web;

import ru.javawebinar.topjava.LoggerWrapper;
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

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        LOG.debug("redirect to mealList");
        request.setAttribute("storeMeals", UserMealsUtil.getFilteredMealsWithExceeded(UserMealsUtil.storeMeals, LocalTime.of(7, 0), LocalTime.of(12, 0), 2000));
        request.getRequestDispatcher("/mealList.jsp").forward(request, response);
    }
}
