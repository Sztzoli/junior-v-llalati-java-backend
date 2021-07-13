package activitytracker;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import java.time.LocalDateTime;

public class ActivityTrackerMain {

    public void saveActivity(EntityManagerFactory factory, Activity activity) {
        EntityManager em = factory.createEntityManager();
        em.getTransaction().begin();
        em.persist(activity);
        em.getTransaction().commit();
        em.close();
    }

    public static void main(String[] args) {
        ActivityTrackerMain trackerMain = new ActivityTrackerMain();
        EntityManagerFactory factory = Persistence.createEntityManagerFactory("pu");
        Activity activity = new Activity(LocalDateTime.now(), "description", Type.BIKING);
        trackerMain.saveActivity(factory, activity);
        factory.close();
    }
}
