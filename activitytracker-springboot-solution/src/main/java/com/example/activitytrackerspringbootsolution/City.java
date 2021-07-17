package com.example.activitytrackerspringbootsolution;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Data
@NoArgsConstructor
@Table(name = "cities")
public class City {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    private int population;

    @ManyToOne
    private Area area;

    public City(String name, int population) {
        this.name = name;
        this.population = population;
    }
}
