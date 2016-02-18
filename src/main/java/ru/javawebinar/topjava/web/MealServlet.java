package ru.javawebinar.topjava.web;

import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.format.datetime.DateFormatter;
import ru.javawebinar.topjava.LoggedUser;
import ru.javawebinar.topjava.LoggerWrapper;
import ru.javawebinar.topjava.model.UserMeal;
import ru.javawebinar.topjava.util.UserMealsUtil;
import ru.javawebinar.topjava.web.meal.UserMealRestController;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.Objects;

/**
 * User: gkislin
 * Date: 19.08.2014
 */
public class MealServlet extends HttpServlet {
    private static final LoggerWrapper LOG = LoggerWrapper.get(MealServlet.class);
    private ConfigurableApplicationContext appCtx = new ClassPathXmlApplicationContext("spring/spring-app.xml");
    private UserMealRestController userMealRestController;

    @Override
    public void init(ServletConfig config) throws ServletException {
        super.init(config);
        userMealRestController = appCtx.getBean(UserMealRestController.class);
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws javax.servlet.ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        String id = request.getParameter("id");

        UserMeal userMeal = new UserMeal(id.isEmpty() ? null : Integer.valueOf(id),
                LoggedUser.id(),
                LocalDateTime.parse(request.getParameter("dateTime")),
                request.getParameter("description"),
                Integer.valueOf(request.getParameter("calories")));

        LOG.info(userMeal.isNew() ? "Create {}" : "Update {}", userMeal);
        userMealRestController.save(userMeal);
        response.sendRedirect("meals");
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String action = request.getParameter("action");

        String strDate1 = request.getParameter("date1"); LocalDate date1;
        String strDate2 = request.getParameter("date2"); LocalDate date2;
        String strTime1 = request.getParameter("time1"); LocalTime time1;
        String strTime2 = request.getParameter("time2"); LocalTime time2;

        if (strDate1 == null || !strDate1.matches("\\d\\d\\d\\d\\-\\d\\d\\-\\d\\d")) date1 = LocalDate.MIN;
        else date1 = LocalDate.parse(strDate1);
        if (strDate2 == null || !strDate2.matches("\\d\\d\\d\\d\\-\\d\\d\\-\\d\\d")) date2 = LocalDate.MAX;
        else date2 = LocalDate.parse(strDate2);
        if (strTime1 == null || strTime1.equals("")) time1 = LocalTime.MIN;
        else time1 = LocalTime.parse(strTime1);
        if (strTime2 == null || strTime1.equals("")) time2 = LocalTime.MAX;
        else time2 = LocalTime.parse(strTime2);
        LocalDateTime ldt1 = LocalDateTime.of(date1, time1);
        LocalDateTime ldt2 = LocalDateTime.of(date2, time2);

        if (action == null) {
            LOG.info("getAll");
            request.setAttribute("mealList",
                    UserMealsUtil.getWithExceededDateTime(userMealRestController.getAll(), ldt1, ldt2, UserMealsUtil.DEFAULT_CALORIES_PER_DAY));
            request.getRequestDispatcher("/mealList.jsp").forward(request, response);
        } else if (action.equals("delete")) {
            int id = getId(request);
            LOG.info("Delete {}", id);
            userMealRestController.delete(id);
            response.sendRedirect("meals");
        } else {
            final UserMeal meal = action.equals("create") ?
                    new UserMeal(1, LocalDateTime.now(), "", 1000) :
                    userMealRestController.get(getId(request));
            request.setAttribute("meal", meal);
            request.getRequestDispatcher("mealEdit.jsp").forward(request, response);
        }
    }

    private int getId(HttpServletRequest request) {
        String paramId = Objects.requireNonNull(request.getParameter("id"));
        return Integer.valueOf(paramId);
    }
}
