package org.training360.com.moviesquerypractice;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.time.LocalDate;
import java.util.List;

public interface MovieRepository extends JpaRepository<Movie,Long> {

    @Query("select m from Movie m left join fetch m.actors a where a.id = :actorId")
    List<Movie> findAllFilmByActor(Long actorId);

    @Query("select m from Movie m where m.studio.id = :studioId and m.year = :year")
    List<Movie> findAllMovieByStudioAndYear(Long studioId, int year);
}
