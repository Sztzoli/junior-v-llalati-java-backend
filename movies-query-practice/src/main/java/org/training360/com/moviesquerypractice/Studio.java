package org.training360.com.moviesquerypractice;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name="studios")
public class Studio {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    public Studio(String name) {
        this.name = name;
    }

    public Studio(Long id, String name) {
        this.id = id;
        this.name = name;
    }

    @OneToMany(mappedBy = "studio")
    @EqualsAndHashCode.Exclude
    private Set<Movie> movies= new HashSet<>();


    public void addMovie(Movie movie){
        movie.setStudio(this);
        movies.add(movie);
    }


}

