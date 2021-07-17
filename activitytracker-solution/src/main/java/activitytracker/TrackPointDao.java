package activitytracker;

import lombok.RequiredArgsConstructor;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import java.time.LocalDate;
import java.util.List;

@RequiredArgsConstructor
public class TrackPointDao {

    private final EntityManagerFactory factory;


    public void saveTrackPoint(TrackPoint trackPoint) {
        EntityManager em = factory.createEntityManager();
        em.getTransaction().begin();
        em.persist(trackPoint);
        em.getTransaction().commit();
        em.close();
    }

    public List<Coordinate> findTrackPointCoordinatesByDate(LocalDate afterThis, int start, int max) {
        EntityManager em = factory.createEntityManager();
        List<Coordinate> coordinates = em.createNamedQuery("findTrackPointWithCoordinate",Coordinate.class)
                .setParameter("time",afterThis)
                .setFirstResult(start)
                .setMaxResults(max)
                .getResultList();
        em.close();
        return coordinates;
    }
}
