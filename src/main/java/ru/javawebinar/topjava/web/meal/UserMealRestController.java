package ru.javawebinar.topjava.web.meal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import ru.javawebinar.topjava.model.UserMeal;
import ru.javawebinar.topjava.service.UserMealServiceImpl;
import ru.javawebinar.topjava.util.exception.ExceptionUtil;
import ru.javawebinar.topjava.util.exception.NotFoundException;

import java.util.List;

/**
 * GKislin
 * 06.03.2015.
 */

@Controller
public class UserMealRestController {

    @Autowired
    private UserMealServiceImpl service;

    public UserMeal save(UserMeal userMeal) {
        return service.save(userMeal);
    }

    public void delete(int id){
        service.delete(id);
    }

    public UserMeal get(int id){
        return service.get(id);
    }

    public List<UserMeal> getAll(){
        return service.getAll();
    }

    public void update(UserMeal userMeal) {
        service.save(userMeal);
    }

}
