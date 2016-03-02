package ru.javawebinar.topjava.model;

import org.hibernate.annotations.Type;
import org.hibernate.validator.constraints.NotEmpty;
import ru.javawebinar.topjava.util.LocalDateTimePersistenceConverter;

import javax.persistence.*;
import javax.validation.constraints.Digits;
import java.security.Timestamp;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;
import java.util.concurrent.TimeUnit;

/**
 * GKislin
 * 11.01.2015.
 */
//@NamedQueries({
//        @NamedQuery(name = UserMeal.DELETE, query = "DELETE FROM UserMeal um WHERE um.id=:id AND um.user.id=:user_id"),
//        @NamedQuery(name = UserMeal.ALL_SORTED, query = "SELECT um FROM UserMeal um LEFT JOIN FETCH um.user WHERE um.user.id=:user_id ORDER BY um.dateTime"),
////        @NamedQuery(name = UserMeal.GET_BETWEEN, query = "SELECT um FROM UserMeal um LEFT JOIN FETCH um.user " +
////                "WHERE um.dateTime BETWEEN :stDate AND :edDate ORDER BY um.dateTime"),
//})
@Entity
@Table(name = "meals", uniqueConstraints = @UniqueConstraint(columnNames = {"date_time", "user_id"}, name = "meals_unique_user_datetime_idx"))
public class UserMeal extends BaseEntity {

    public static final String DELETE = "UserMeal.delete";
    public static final String ALL_SORTED = "UserMeal.getAllSorted";
    public static final String GET_BETWEEN = "User.getBetween";

    @Id
    @SequenceGenerator(name = "global_meal_seq", sequenceName = "global_seq", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "global_meal_seq")
    protected Integer id;

    @Column(name = "date_time", nullable = false)
    @NotEmpty
    protected LocalDateTime dateTime;

    @Column(name = "description", nullable = false)
    @NotEmpty
    protected String description;

    @Column(name = "calories", nullable = false)
    @Digits(fraction = 0, integer = 4)
    @NotEmpty
    protected int calories;

    @ManyToOne(fetch = FetchType.LAZY)
    public User user;

    public UserMeal(){
    }

    public UserMeal(UserMeal um){
        this(um.getId(), um.getDateTime(), um.getDescription(), um.getCalories());
    }

    public UserMeal(LocalDateTime dateTime, String description, int calories) {
        this(null, dateTime, description, calories);
    }

    public UserMeal(Integer id, LocalDateTime dateTime, String description, int calories) {
        super(id);
        this.dateTime = dateTime;
        this.description = description;
        this.calories = calories;
    }

    public void setId(int id) {
        this.id = id;
    }

    public LocalDateTime getDateTime() {
        return dateTime;
    }

    public String getDescription() {
        return description;
    }

    public int getCalories() {
        return calories;
    }

    public Integer getId() {
        return id;
    }

    public void setDateTime(LocalDateTime dateTime) {
        this.dateTime = dateTime;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setCalories(int calories) {
        this.calories = calories;
    }

    @Override
    public String toString() {
        return "UserMeal{" +
                "id=" + id +
                ", dateTime=" + dateTime +
                ", description='" + description + '\'' +
                ", calories=" + calories +
                '}';
    }


}