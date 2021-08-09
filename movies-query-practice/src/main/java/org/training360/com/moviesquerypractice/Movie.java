package org.training360.com.moviesquerypractice;

import lombok.*;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name="movies")
public class Movie {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String title;

    private int year;

    public Movie(String title, int year) {
        this.title = title;
        this.year = year;
    }

    @ManyToMany(mappedBy = "movies")
    @EqualsAndHashCode.Exclude
    private Set<Actor> actors = new HashSet<>();


    @ManyToOne
    @ToString.Exclude
    private Studio studio;

    public void addActor(Actor actor){
        actor.getMovies().add(this);
        actors.add(actor);
    }



}

