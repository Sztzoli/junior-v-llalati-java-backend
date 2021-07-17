package activitytracker;

import lombok.RequiredArgsConstructor;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.TypedQuery;

@RequiredArgsConstructor
public class AreaDao {

    private final EntityManagerFactory factory;

    public void save(Area area) {
        EntityManager em = factory.createEntityManager();
        em.getTransaction().begin();
        em.persist(area);
        em.getTransaction().commit();
        em.close();
    }

    public void addCityToArea(Long id, City city) {
        EntityManager em = factory.createEntityManager();
        em.getTransaction().begin();
        Area area = em.find(Area.class, id);
        area.addCity(city);
        em.getTransaction().commit();
        em.close();
    }

    public Area findAreaWithCity(Long id) {
        EntityManager em = factory.createEntityManager();
        Area area = em.createQuery("select a from Area a left join fetch a.cityMap where a.id = :id", Area.class)
                .setParameter("id", id)
                .getSingleResult();
        em.close();
        return area;
    }
}
