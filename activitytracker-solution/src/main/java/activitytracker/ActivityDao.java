package activitytracker;

import lombok.RequiredArgsConstructor;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import java.util.List;

@RequiredArgsConstructor
public class ActivityDao {

    private final EntityManagerFactory entityManagerFactory;

    public void saveActivity(Activity activity) {
        EntityManager em = entityManagerFactory.createEntityManager();
        em.getTransaction().begin();
        em.persist(activity);
        em.getTransaction().commit();
        em.close();
    }

    public Activity findActivityById(long id) {
        EntityManager em = entityManagerFactory.createEntityManager();
        Activity activity = em.find(Activity.class, id);
        em.close();
        return activity;
    }

    public List<Activity> listActivities() {
        EntityManager em = entityManagerFactory.createEntityManager();
        List<Activity> resultList = em.createQuery("select a from Activity a order by a.desc", Activity.class).getResultList();
        em.close();
        return resultList;
    }

      public void updateActivity(long id, String desc) {
        EntityManager em = entityManagerFactory.createEntityManager();
        em.getTransaction().begin();
        Activity activity = em.find(Activity.class, id);
        activity.setDesc(desc);
        em.getTransaction().commit();
        em.close();
    }

    public Activity findActivityByIdWithLabels(long id) {
        EntityManager em = entityManagerFactory.createEntityManager();
        Activity activity = em.createQuery("select a from Activity a left join fetch a.labels where a.id = :id order by a.desc", Activity.class)
                .setParameter("id", id)
                .getSingleResult();
        em.close();
        return activity;
    }

    public Activity addTrackPointsToActivity(Long id, TrackPoint trackPoint) {
        EntityManager em = entityManagerFactory.createEntityManager();
        em.getTransaction().begin();
        Activity activity = em.find(Activity.class, id);
        activity.addTrackPoint(trackPoint);
        em.getTransaction().commit();
        em.close();
        return activity;
    }

    public Activity addAreaToActivity(Long id, Area area) {
        EntityManager em = entityManagerFactory.createEntityManager();
        em.getTransaction().begin();
        Activity activity = em.find(Activity.class, id);
        activity.addArea(area);
        em.getTransaction().commit();
        em.close();
        return activity;
    }

    public Activity findActivityByIdWithTrackPoints(long id) {
        EntityManager em = entityManagerFactory.createEntityManager();
        Activity activity = em.createQuery("select a from Activity a left join fetch a.trackPoints where a.id = :id order by a.desc", Activity.class)
                .setParameter("id", id)
                .getSingleResult();
        em.close();
        return activity;
    }

    public Activity findActivityByIdWithArea(long id) {
        EntityManager em = entityManagerFactory.createEntityManager();
        Activity activity = em.createQuery("select a from Activity a left join fetch a.areas where a.id = :id order by a.desc", Activity.class)
                .setParameter("id", id)
                .getSingleResult();
        em.close();
        return activity;
    }

}
