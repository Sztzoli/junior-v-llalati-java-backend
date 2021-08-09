package org.training360.com.moviesquerypractice;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.jdbc.Sql;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
public class RepoIT {

    @Autowired
    ActorRepository actorRepository;

    @Autowired
    MovieRepository movieRepository;

    @Autowired
    StudioRepository studioRepository;

    Actor actor1;
    Actor actor2;
    Actor actor3;
    Movie movie1;
    Movie movie2;
    Movie movie3;
    Movie movie4;
    Studio studio1;
    Studio studio2;

    @BeforeEach
    void init() {
        actor1 = new Actor("name1", LocalDate.now());
        actor2 = new Actor("name2", LocalDate.now());
        actor3 = new Actor("name3", LocalDate.now().minusYears(10));
        movie1 = new Movie("title1", 2020);
        movie2 = new Movie("title2", 2019);
        movie3 = new Movie("title3", 2019);
        movie4 = new Movie("title4", 2020);
        studio1 = new Studio("studio1");
        studio2 = new Studio("studio2");
        movie1.addActor(actor1);
        movie1.addActor(actor2);
        movie2.addActor(actor1);
        movie3.addActor(actor3);
        movie4.addActor(actor1);
        movie4.addActor(actor3);
        studio1.addMovie(movie1);
        studio1.addMovie(movie2);
        studio1.addMovie(movie3);
        studio2.addMovie(movie4);
        studioRepository.save(studio1);
        studioRepository.save(studio2);
        movieRepository.save(movie1);
        movieRepository.save(movie2);
        movieRepository.save(movie3);
        movieRepository.save(movie4);
        actorRepository.save(actor1);
        actorRepository.save(actor2);
        actorRepository.save(actor3);
    }

    @Test
    void first() {
        List<Movie> result = movieRepository.findAllFilmByActor(actor1.getId());

        assertThat(result)
                .hasSize(3)
                .extracting(Movie::getTitle)
                .containsExactly("title1", "title2", "title4");
    }

    @Test
    void test2() {
        List<Actor> result = actorRepository.findAllActorAfterDate(LocalDate.now().minusYears(5));

        assertThat(result)
                .hasSize(2)
                .extracting(Actor::getName)
                .containsExactly("name1", "name2");
    }

    @Test
    void test3() {
        List<Actor> result = actorRepository.findAllActorPlayMovies();

        assertThat(result)
                .hasSize(2)
                .extracting(Actor::getName)
                .containsExactly("name1", "name3");
    }

    @Test
    void test4() {
        List<Studio> result = studioRepository.findAllStudioHaveFilms();

        assertThat(result)
                .hasSize(1)
                .extracting(Studio::getName)
                .containsExactly("studio1");
    }

    @Test
    void test5() {
        List<Actor> result = actorRepository.findAllActorPlayInMovie(movie4.getId());

        assertThat(result)
                .hasSize(2)
                .extracting(Actor::getName)
                .containsExactly("name1", "name3");
    }

    @Test
    void test6() {
        List<Movie> result = movieRepository.findAllMovieByStudioAndYear(studio1.getId(), 2019);

        assertThat(result)
                .hasSize(2)
                .extracting(Movie::getTitle)
                .containsExactly("title2", "title3");
    }

    @Test
    void test7() {
        List<Studio> result = studioRepository.findAllStudioByActor(actor3.getId());

        assertThat(result).
                hasSize(2).
                extracting(Studio::getName)
                .containsExactly("studio1", "studio2");
    }

    @Test
    void testBonus() {
        List<Studio> result = studioRepository.findAllStudioByActorWithMinTwoFilm(actor1.getId());

        assertThat(result)
                .hasSize(1)
                .extracting(Studio::getName)
                .containsExactly("studio1");
    }

    @Test
    void testBonus2() {
        List<Studio> result = studioRepository.findAllStudioActorWithMinTwoFilm();
        assertThat(result)
                .hasSize(1)
                .extracting(Studio::getName)
                .containsExactly("studio1");

    }


}
