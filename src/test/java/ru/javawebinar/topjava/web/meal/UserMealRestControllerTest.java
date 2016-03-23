package ru.javawebinar.topjava.web.meal;

import org.junit.Test;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.ResultActions;
import ru.javawebinar.topjava.TestUtil;
import ru.javawebinar.topjava.UserTestData;
import ru.javawebinar.topjava.model.User;
import ru.javawebinar.topjava.model.UserMeal;
import ru.javawebinar.topjava.web.AbstractControllerTest;

import java.util.Arrays;
import java.util.Collections;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import ru.javawebinar.topjava.MealTestData;
import ru.javawebinar.topjava.web.json.JsonUtil;

import static ru.javawebinar.topjava.UserTestData.USER;
import static ru.javawebinar.topjava.UserTestData.USER_ID;

import static ru.javawebinar.topjava.MealTestData.*;
import static ru.javawebinar.topjava.MealTestData.MATCHER;
import static ru.javawebinar.topjava.UserTestData.ADMIN;

/**
 * Created by Qouer on 22.03.2016.
 */
public class UserMealRestControllerTest extends AbstractControllerTest {

    public static final String REST_URL = UserMealRestController.REST_URL + "/meals/";
    public static final String AJ_UTF8 = MediaType.APPLICATION_JSON + ";charset=UTF-8";

    @Test
    public void testGet() throws Exception {
        mockMvc.perform(get(REST_URL + MEAL1_ID))
                .andExpect(status().isOk())
                .andDo(print())
                .andExpect(content().contentType(AJ_UTF8))
                .andExpect(MATCHER.contentMatcher(MEAL1));
    }

    @Test
    public void testGetAll() throws Exception {
        TestUtil.print(mockMvc.perform(get(REST_URL).contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(MATCHER.contentListMatcher(MEAL6, MEAL5, MEAL4, MEAL3, MEAL2, MEAL1)));
    }

    @Test
    public void testDelete() throws Exception {
        mockMvc.perform(delete(REST_URL + "delete/" + MEAL1_ID).contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk());
        MealTestData.MATCHER.assertCollectionEquals((Arrays.asList(MEAL6, MEAL5, MEAL4, MEAL3, MEAL2)), userMealService.getAll(USER_ID));
    }

    @Test
    public void testUpdate() throws Exception {
        UserMeal meal = new UserMeal(MEAL1.getId(), MEAL1.getDateTime(), MEAL1.getDescription(), MEAL1.getCalories());
        meal.setDescription("Ночник");
        mockMvc.perform(put(REST_URL + "update/" + MEAL1_ID)
                .contentType(MediaType.APPLICATION_JSON)
                .content(JsonUtil.writeValue(meal)))
                .andExpect(status().isOk());
    }

    @Test
    public void testCreate() throws Exception {
        UserMeal mealCreated = MealTestData.getCreated();
        ResultActions action = mockMvc.perform(post("/rest")
                .contentType(MediaType.APPLICATION_JSON)
                .content(JsonUtil.writeValue(mealCreated)))
                .andExpect(status()
                        .isCreated());

        UserMeal returned = MealTestData.MATCHER.fromJsonAction(action);
        mealCreated.setId(returned.getId());

        MealTestData.MATCHER.assertEquals(mealCreated, returned);
        MealTestData.MATCHER.assertCollectionEquals(Arrays.asList(mealCreated, MEAL6, MEAL5, MEAL4, MEAL3, MEAL2, MEAL1), userMealService.getAll(USER_ID));
    }

    @Test
    public void testGetBetween() throws Exception {
        //2015-30-05T10:00:00/2015-31-05T14:00:00
        mockMvc.perform(get(REST_URL + "filter/2015-05-30/10:00:00/2015-05-31/14:00:00").contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(MATCHER.contentListMatcher(MEAL5, MEAL4, MEAL2, MEAL1));
    }
}