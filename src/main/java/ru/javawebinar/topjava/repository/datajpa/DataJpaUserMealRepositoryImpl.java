package ru.javawebinar.topjava.repository.datajpa;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Repository;
import ru.javawebinar.topjava.model.User;
import ru.javawebinar.topjava.model.UserMeal;
import ru.javawebinar.topjava.repository.UserMealRepository;

import javax.persistence.PersistenceContext;
import javax.persistence.PersistenceContextType;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

/**
 * GKislin
 * 27.03.2015.
 */
@Repository
public class DataJpaUserMealRepositoryImpl implements UserMealRepository{
    private static final Sort SORT_NAME_DATE = new Sort(Sort.Direction.DESC, "dateTime");

    @Autowired
    private ProxyUserMealRepository proxy;

    @Autowired
    private ProxyUserRepository proxyUser;

    @Override
    public UserMeal save(UserMeal userMeal, int userId) {
        userMeal.setUser(proxyUser.getOne(userId));
        if (userMeal.isNew()){
            return proxy.save(userMeal);
        }else{
            return proxy.findOne(userMeal.getId()).getUser().getId() == userId ? proxy.save(userMeal) : null;
        }
    }

    @Override
    public boolean delete(int id, int userId) {
        return proxy.findOne(id).getUser().getId() == userId ? proxy.delete(id) != 0 : false;
    }

    @Override
    public UserMeal get(int id, int userId) {
        UserMeal userMeal = proxy.findOne(id);
        return userMeal.getUser().getId() != userId ? null : userMeal;
    }

    @Override
    public List<UserMeal> getAll(int userId) {
        return proxy.findAll(SORT_NAME_DATE).stream().filter(userMeal -> userMeal.getUser().getId() == userId).collect(Collectors.toList());
    }

    @Override
    public List<UserMeal> getBetween(LocalDateTime startDate, LocalDateTime endDate, int userId) {
        return proxy.findAll(SORT_NAME_DATE).stream().filter(userMeal -> userMeal.getDateTime().isAfter(startDate) && userMeal.getDateTime().isBefore(endDate)).collect(Collectors.toList());
    }
}
