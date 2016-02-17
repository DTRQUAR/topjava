package ru.javawebinar.topjava.repository.mock;

import org.springframework.stereotype.Repository;
import ru.javawebinar.topjava.LoggedUser;
import ru.javawebinar.topjava.LoggerWrapper;
import ru.javawebinar.topjava.model.UserMeal;
import ru.javawebinar.topjava.repository.UserMealRepository;
import ru.javawebinar.topjava.util.UserMealsUtil;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collector;
import java.util.stream.Collectors;

/**
 * GKislin
 * 15.09.2015.
 */

@Repository
public class InMemoryUserMealRepositoryImpl implements UserMealRepository {
    private static final LoggerWrapper LOG = LoggerWrapper.get(InMemoryUserMealRepositoryImpl.class);
    private Map<Integer, UserMeal> repository = new ConcurrentHashMap<>();
    private AtomicInteger counter = new AtomicInteger(0);

    {
        UserMealsUtil.MEAL_LIST.forEach(this::save);
    }

    @Override
    public UserMeal save(UserMeal userMeal) {
        LOG.info("save " + userMeal);
        if (userMeal.isNew()) {
            userMeal.setId(counter.incrementAndGet());
        }
        repository.put(userMeal.getId(), userMeal);
        return userMeal;
    }

    @Override
    public boolean delete(int id) {
        LOG.info("delete " + id);
        if (repository.containsKey(id)){
            if (repository.get(id).getUserId() == LoggedUser.id()){
                repository.remove(id);
                return true;
            }else{
                return false;
            }
        }else{
            return false;
        }
    }

    @Override
    public UserMeal get(int id) {
        LOG.info("get " + id);
        return repository.containsKey(id) ? ( repository.get(id) != null ? ( LoggedUser.id() != repository.get(id).getUserId() ? null : repository.get(id) ) : null ) : null;
    }

    @Override
    public List<UserMeal> getAll() {
        LOG.info("getAll");
        Collection<UserMeal> collect = repository.values();
        if (collect != null) {
            collect = ( collect.stream()
                    .sorted((o1, o2) -> o1.getDateTime().toLocalTime().isAfter(o2.getDateTime().toLocalTime()) ? 1 : ( o1.getDateTime().toLocalTime() == (o2.getDateTime().toLocalTime()) ? 0 : -1))
                    .filter(um -> um.getUserId() == LoggedUser.id())
                    .collect(Collectors.toList()) );
        } else {
            collect = null;
        }

        return collect != null ? (ArrayList) collect : null;
    }
}

