package org.training360.com.moviesquerypractice;

import lombok.*;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "actors")
public class Actor {



    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;


    private String name;

    private LocalDate dateOfBirth;

    public Actor(String name, LocalDate dateOfBirth) {
        this.name = name;
        this.dateOfBirth = dateOfBirth;
    }

    @ManyToMany()
    @EqualsAndHashCode.Exclude
    @ToString.Exclude
    private Set<Movie> movies = new HashSet<>();
}
