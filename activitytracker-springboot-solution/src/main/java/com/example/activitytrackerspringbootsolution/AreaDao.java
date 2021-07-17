package com.example.activitytrackerspringbootsolution;

import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;

@Repository
public class AreaDao {

    @PersistenceContext
    private EntityManager em;

    @Transactional
    public void save(Area area) {
        em.persist(area);
    }

    @Transactional
    public void addCityToArea(Long id, City city) {
        Area area = em.find(Area.class, id);
        area.addCity(city);
    }

    public Area findAreaWithCity(Long id) {
        return em.createQuery("select a from Area a left join fetch a.cityMap where a.id = :id", Area.class)
                .setParameter("id", id)
                .getSingleResult();
    }
}
