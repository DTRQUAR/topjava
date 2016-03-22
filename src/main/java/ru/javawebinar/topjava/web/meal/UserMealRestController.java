package ru.javawebinar.topjava.web.meal;

import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import ru.javawebinar.topjava.model.User;
import ru.javawebinar.topjava.model.UserMeal;
import ru.javawebinar.topjava.to.UserMealWithExceed;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

/**
 * GKislin
 * 06.03.2015.
 */
@RestController
@RequestMapping(UserMealRestController.REST_URL)
public class UserMealRestController extends AbstractUserMealController {
    static final String REST_URL = "/rest";

    @Override
    @RequestMapping(value = "/meals/{id}", method = RequestMethod.GET)
    public UserMeal get(@PathVariable("id") int id){
        return super.get(id);
    }

    @Override
    @RequestMapping(value = "/meals", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public List<UserMealWithExceed> getAll(){
        return super.getAll();
    }

    @Override
    @RequestMapping(value = "/meals/delete/{id}", method = RequestMethod.DELETE)
    public void delete(@PathVariable("id") int id) {
         super.delete(id);
    }

    @Override
    @RequestMapping(value = "/meals/update/{id}", method = RequestMethod.PUT, consumes = MediaType.APPLICATION_JSON_VALUE)
    public void update(@RequestBody UserMeal meal, @PathVariable("id") int id) {
        super.update(meal, id);
    }

    @Override
    @RequestMapping(value = "/meals", method = RequestMethod.PUT)
    public UserMeal create(@RequestBody UserMeal meal){
        return super.create(meal);
    }

    @Override
    @RequestMapping(value = "/meals/{sd}/{st}/{ed}/{et}")
    public List<UserMealWithExceed> getBetween(@DateTimeFormat LocalDate startDate, @DateTimeFormat LocalTime startTime,
                                               @DateTimeFormat LocalDate endDate, @DateTimeFormat LocalTime endTime){
        return super.getBetween(startDate, startTime, endDate, endTime);
    }

}