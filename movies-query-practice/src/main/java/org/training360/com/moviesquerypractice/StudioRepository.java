package org.training360.com.moviesquerypractice;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface StudioRepository extends JpaRepository<Studio, Long> {

    @Query("select s from Studio s where size(s.movies)>1 ")
    List<Studio> findAllStudioHaveFilms();

    @Query("select distinct s from Studio s left join fetch s.movies m left join fetch m.actors a where a.id= :actorId")
    List<Studio> findAllStudioByActor(Long actorId);

    @Query("select distinct s from Studio s left join fetch s.movies m left join fetch m.actors a where " +
            "(select count(mo) from Movie mo  where mo.studio.id = s.id and a.id= :actorId)>1 ")
    List<Studio> findAllStudioByActorWithMinTwoFilm(Long actorId);


//    SELECT DISTINCT studios.id from actor_movies
//LEFT JOIN movies ON movies.id = actor_movies.movies_id LEFT JOIN studios ON studios.id = movies.studio_id
// LEFT JOIN actor ON actor.id = actor_movies.actors_id
// GROUP BY actor.name, movies.studio_id
// HAVING COUNT(studios.name)>1


    @Query("select distinct s from Movie m left join  m.studio s left join  m.actors a  " +
            "group by a.name ,m.studio.id having count(s.name)>1")
    List<Studio> findAllStudioActorWithMinTwoFilm();




}
