package ru.javawebinar.topjava.repository.datajpa;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;
import ru.javawebinar.topjava.model.User;
import ru.javawebinar.topjava.model.UserMeal;

import javax.persistence.PersistenceContext;
import javax.persistence.PersistenceContextType;
import java.util.List;

/**
 * Created by Qouer on 09.03.2016.
 */

@Transactional(readOnly = true)
public interface ProxyUserMealRepository extends JpaRepository<UserMeal, Integer> {

    @Transactional
    @Modifying
    @Query("DELETE FROM UserMeal m WHERE m.id=:id")
    int delete(@Param("id") int id);


    @Transactional
    @Override
    UserMeal save(UserMeal meal);

    @Override
    UserMeal findOne(Integer id);

    @Override
    List<UserMeal> findAll();

    List<UserMeal> getBetween();


}
