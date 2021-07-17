package activitytracker;

import lombok.RequiredArgsConstructor;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

@RequiredArgsConstructor
public class CityDao {

    private final EntityManagerFactory factory;

    public void save(City city) {
        EntityManager em = factory.createEntityManager();
        em.getTransaction().begin();
        em.persist(city);
        em.getTransaction().commit();
        em.close();
    }
}
