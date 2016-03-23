package ru.javawebinar.topjava.web.meal;

import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import ru.javawebinar.topjava.model.User;
import ru.javawebinar.topjava.model.UserMeal;
import ru.javawebinar.topjava.to.UserMealWithExceed;

import java.net.URI;
import java.time.LocalDate;
import java.time.LocalDateTime;
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

    @RequestMapping(method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<UserMeal> createMeal(@RequestBody UserMeal meal){
        UserMeal created =  super.create(meal);
        URI uriOfNewResource = ServletUriComponentsBuilder.fromCurrentContextPath()
                .path(REST_URL + "/meals/{id}")
                .buildAndExpand(created.getId()).toUri();
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setLocation(uriOfNewResource);

        return new ResponseEntity<>(created, httpHeaders, HttpStatus.CREATED);
    }

    @RequestMapping(value = "/meals/filter/{sd}/{st}/{ed}/{et}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
//    public List<UserMealWithExceed> getBetween(@PathVariable("sd") @DateTimeFormat LocalDate startDate, @PathVariable("st") @DateTimeFormat LocalTime startTime,
//                                               @PathVariable("ed") @DateTimeFormat LocalDate endDate, @PathVariable("et") @DateTimeFormat LocalTime endTime){
//        return super.getBetween(startDate, startTime, endDate, endTime);
//    }
    public List<UserMealWithExceed> getBetween(@DateTimeFormat(iso = DateTimeFormat.ISO.DATE) @PathVariable("sd")  LocalDate localDateStart,
                                               @DateTimeFormat(iso = DateTimeFormat.ISO.TIME) @PathVariable("st")  LocalTime localTimeStart,
                                               @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) @PathVariable("ed")  LocalDate localDateEnd,
                                               @DateTimeFormat(iso = DateTimeFormat.ISO.TIME) @PathVariable("et")  LocalTime localTimeEnd) {
        return super.getBetween(localDateStart, localTimeStart, localDateEnd, localTimeEnd);
    }

}