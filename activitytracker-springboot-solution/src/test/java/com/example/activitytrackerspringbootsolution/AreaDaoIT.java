package com.example.activitytrackerspringbootsolution;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
class AreaDaoIT {


    @Autowired
    AreaDao areaDao;

    @Autowired
    CityDao cityDao;


    @Test
    void addToMap() {
        Area area = new Area("Alföld");
        areaDao.save(area);
        City city = new City("Kecskemét",125000);
        areaDao.addCityToArea(area.getId(),city);

        Area loadedArea = areaDao.findAreaWithCity(area.getId());
        assertEquals(125000,loadedArea.getCityMap().get("Kecskemét").getPopulation());
    }

    @Test
    void saveWith() {
        Area area = new Area("Alföld");
        City city = new City("Kecskemét",125000);
        area.addCity(city);
        areaDao.save(area);

        Area loadedArea = areaDao.findAreaWithCity(area.getId());
        assertEquals(125000,loadedArea.getCityMap().get("Kecskemét").getPopulation());
    }

}