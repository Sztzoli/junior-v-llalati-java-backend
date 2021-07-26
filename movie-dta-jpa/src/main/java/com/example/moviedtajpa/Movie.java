package com.example.moviedtajpa;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Entity
@Data
@NoArgsConstructor
@Table(name = "movies")
public class Movie {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String title;

    @ElementCollection
    private List<Integer> ratings;

    public void addRating(int rating) {
        if (ratings == null) {
            ratings = new ArrayList<>();
        }
        ratings.add(rating);
    }

    public Movie(String title) {
        this.title = title;
    }

}
