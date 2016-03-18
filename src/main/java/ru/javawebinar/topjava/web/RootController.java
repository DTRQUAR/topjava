package ru.javawebinar.topjava.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import ru.javawebinar.topjava.LoggedUser;
import ru.javawebinar.topjava.model.UserMeal;
import ru.javawebinar.topjava.service.UserMealService;
import ru.javawebinar.topjava.service.UserService;

import javax.servlet.http.HttpServletRequest;
import java.time.LocalDateTime;
import java.util.Objects;

/**
 * User: gkislin
 * Date: 22.08.2014
 */
@Controller
public class RootController {
    @Autowired
    private UserService service;

    @Autowired
    private UserMealService mealService;

    @RequestMapping(value = "/", method = RequestMethod.GET)
    public String root()     {
        return "index";
    }

    @RequestMapping(value = "/users", method = RequestMethod.GET)
    public String userList(Model model) {
        model.addAttribute("userList", service.getAll());
        return "userList";
    }

    @RequestMapping(value = "/users", method = RequestMethod.POST)
    public String setUser(HttpServletRequest request) {
        int userId = Integer.valueOf(request.getParameter("userId"));
        LoggedUser.setId(userId);
        return "redirect:meals";
    }

    @RequestMapping(value = "/meals", method = RequestMethod.GET)
    public String mealList(Model model) {
//        String action = request.getParameter("action");
//        if (action == null) {
//            request.setAttribute("mealList", mealService.getAll(LoggedUser.id()));
//            return "mealList";
//        }else{
//            return deleteMeal(request);
        model.addAttribute("mealList", service.getAll());
        return "mealList";
    }

    @RequestMapping(value = "/meals", method = RequestMethod.GET)
    public String deleteMeal(HttpServletRequest request) {
        String action = request.getParameter("action");
        if (action.equals("delete")) {
            int id = getId(request);
            mealService.delete(id, LoggedUser.id());
            return "redirect:meals";
        }else{
            return createMeal(request);
        }
    }

    @RequestMapping(value = "/meals", method = RequestMethod.GET)
    public String createMeal(HttpServletRequest request) {
            String action = request.getParameter("action");
            final UserMeal meal = action.equals("create") ?
                    new UserMeal(LocalDateTime.now(), "", 1000) :
                    mealService.get(getId(request), LoggedUser.id());
            request.setAttribute("meal", meal);
            return "mealEdit";
    }

    private int getId(HttpServletRequest request) {
        String paramId = Objects.requireNonNull(request.getParameter("id"));
        return Integer.valueOf(paramId);
    }
}
