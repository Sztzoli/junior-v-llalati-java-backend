package com.example.activitytrackerspringbootsolution;

import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;

@Repository
public class CityDao {

    @PersistenceContext
    private EntityManager em;

    @Transactional
    public void save(City city) {
        em.persist(city);
    }
}
