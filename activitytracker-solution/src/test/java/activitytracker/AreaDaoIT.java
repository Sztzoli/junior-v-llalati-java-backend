package activitytracker;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

import static org.junit.jupiter.api.Assertions.*;

class AreaDaoIT {


    AreaDao areaDao;
    CityDao cityDao;

    @BeforeEach
    void setUp() {
        EntityManagerFactory factory = Persistence.createEntityManagerFactory("pu");
        areaDao = new AreaDao(factory);
        cityDao = new CityDao(factory);
    }

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