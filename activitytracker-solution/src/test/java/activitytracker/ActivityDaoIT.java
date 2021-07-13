package activitytracker;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;

class ActivityDaoIT {

    ActivityDao activityDao;

    @BeforeEach
    void setUp() {
        EntityManagerFactory factory = Persistence.createEntityManagerFactory("pu");
        activityDao = new ActivityDao(factory);
    }

    @Test
    void saveThenFind() {
        Activity activity = new Activity(LocalDateTime.now(),"description", Type.BIKING);
        activityDao.saveActivity(activity);
        Activity loadedActivity = activityDao.findActivityById(activity.getId());

        assertEquals("description", loadedActivity.getDesc());
    }
}