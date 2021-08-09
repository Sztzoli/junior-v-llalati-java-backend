package org.training360.com.moviesquerypractice;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.time.LocalDate;
import java.util.List;

public interface ActorRepository extends JpaRepository<Actor, Long> {

    @Query("select a from Actor a where a.dateOfBirth> :date")
    List<Actor> findAllActorAfterDate(LocalDate date);

    @Query("select distinct a from Actor a left join fetch a.movies m where size(m)>1")
    List<Actor> findAllActorPlayMovies();

    @Query("select a from Actor a left join fetch a.movies m where m.id= :movieId")
    List<Actor> findAllActorPlayInMovie(Long movieId);


}
