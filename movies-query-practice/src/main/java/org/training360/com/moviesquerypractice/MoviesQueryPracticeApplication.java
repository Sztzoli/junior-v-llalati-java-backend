package org.training360.com.moviesquerypractice;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.time.LocalDate;

@SpringBootApplication
public class MoviesQueryPracticeApplication {

    public static void main(String[] args) {
        SpringApplication.run(MoviesQueryPracticeApplication.class, args);
    }

//    @Bean
//    public CommandLineRunner init(ActorRepository actorRepository, MovieRepository movieRepository, StudioRepository studioRepository) {
//        return args -> {
//            Actor actor1 = new Actor("name1", LocalDate.now());
//            Actor actor2 = new Actor("name2", LocalDate.now());
//            Actor actor3 = new Actor("name3", LocalDate.now().minusYears(10));
//            Movie movie1 = new Movie("title1", 2020);
//            Movie movie2 = new Movie("title2", 2019);
//            Movie movie3 = new Movie("title3", 2019);
//            Movie movie4 = new Movie("title4", 2020);
//            Studio studio1 = new Studio("studio1");
//            Studio studio2 = new Studio("studio2");
//            movie1.addActor(actor1);
//            movie1.addActor(actor2);
//            movie2.addActor(actor1);
//            movie3.addActor(actor3);
//            movie4.addActor(actor1);
//            movie4.addActor(actor3);
//            studio1.addMovie(movie1);
//            studio1.addMovie(movie2);
//            studio1.addMovie(movie3);
//            studio2.addMovie(movie4);
//            studioRepository.save(studio1);
//            studioRepository.save(studio2);
//            movieRepository.save(movie1);
//            movieRepository.save(movie2);
//            movieRepository.save(movie3);
//            movieRepository.save(movie4);
//            actorRepository.save(actor1);
//            actorRepository.save(actor2);
//            actorRepository.save(actor3);
//        };
//    }

}
