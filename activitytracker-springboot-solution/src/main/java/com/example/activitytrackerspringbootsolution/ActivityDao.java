package com.example.activitytrackerspringbootsolution;

import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;
import java.time.LocalDateTime;
import java.util.List;


@Repository
public class ActivityDao {

    @PersistenceContext
    private EntityManager em;

    @Transactional
    public void saveActivity(Activity activity) {
        em.persist(activity);
    }

    public Activity findActivityById(long id) {
        return em.find(Activity.class, id);
    }

    public List<Activity> listActivities() {
        return em.createQuery("select a from Activity a order by a.desc", Activity.class).getResultList();
    }

    @Transactional
    public void updateActivity(long id, String desc) {
        Activity activity = em.find(Activity.class, id);
        activity.setDesc(desc);
    }


    public Activity findActivityByIdWithLabels(long id) {
        return em.createQuery("select a from Activity a left join fetch a.labels where a.id = :id order by a.desc", Activity.class)
                .setParameter("id", id)
                .getSingleResult();
    }

    @Transactional
    public Activity addTrackPointsToActivity(Long id, TrackPoint trackPoint) {
        Activity activity = em.find(Activity.class, id);
        activity.addTrackPoint(trackPoint);
        return activity;
    }

    @Transactional
    public Activity addAreaToActivity(Long id, Area area) {
        Activity activity = em.find(Activity.class, id);
        activity.addArea(area);
        return activity;
    }

    public Activity findActivityByIdWithTrackPoints(long id) {
        return em.createQuery("select a from Activity a left join fetch a.trackPoints where a.id = :id order by a.desc", Activity.class)
                .setParameter("id", id)
                .getSingleResult();
    }

    public Activity findActivityByIdWithArea(long id) {
        return em.createQuery("select a from Activity a left join fetch a.areas where a.id = :id order by a.desc", Activity.class)
                .setParameter("id", id)
                .getSingleResult();
    }

    public List<Object[]> findTrackPointCountByActivity(Activity activity) {
        return em.createQuery("select count(t.activity) from TrackPoint t where t.activity.id = :id", Object[].class)
                .setParameter("id", activity.getId())
                .getResultList();
    }

    @Transactional
    public void removeActivitiesByDateAndType(LocalDateTime afterThis, Type type) {
        em.createQuery("delete from Activity a where a.startTime > :time and a.type = :type")
                .setParameter("time", afterThis)
                .setParameter("type", type)
                .executeUpdate();
    }

}
