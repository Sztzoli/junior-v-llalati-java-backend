package com.example.activitytrackerspringbootsolution;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Entity
@Data
@NoArgsConstructor
@Table(name = "areas")
public class Area {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    public Area(String name) {
        this.name = name;
    }

    @ManyToMany(mappedBy = "areas")
    private List<Activity> activities = new ArrayList<>();

    @OneToMany(mappedBy = "area", cascade = {CascadeType.PERSIST, CascadeType.REMOVE})
    @MapKey(name = "name")
    private Map<String, City> cityMap = new HashMap<>();


    public void addCity(City city) {
        cityMap.put(city.getName(), city);
        city.setArea(this);
    }
}
