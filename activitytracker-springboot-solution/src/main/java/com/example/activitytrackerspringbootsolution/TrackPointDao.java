package com.example.activitytrackerspringbootsolution;

import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;
import java.time.LocalDate;
import java.util.List;

@Repository
public class TrackPointDao {

    @PersistenceContext
    private EntityManager em;


    @Transactional
    public void saveTrackPoint(TrackPoint trackPoint) {
        em.persist(trackPoint);
    }

    public List<Coordinate> findTrackPointCoordinatesByDate(LocalDate afterThis, int start, int max) {
        return em.createNamedQuery("findTrackPointWithCoordinate", Coordinate.class)
                .setParameter("time",afterThis)
                .setFirstResult(start)
                .setMaxResults(max)
                .getResultList();
    }
}
