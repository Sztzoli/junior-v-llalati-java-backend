package com.example.moviedtajpa;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/movies")
@RequiredArgsConstructor
public class MovieController {

    private final MovieService movieService;

    @GetMapping
    public List<MovieDto> listMovies() {
        return movieService.listMovies();
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public MovieDto createFilm(@RequestBody CreateFilmCommand command) {
        return movieService.createFilm(command);
    }

    @PostMapping("/{id}/rating")
    @ResponseStatus(HttpStatus.CREATED)
    public MovieDto addRating(
            @PathVariable Long id,
            @RequestBody AddRatingCommand command) {
        return movieService.addRating(id, command);
    }
}
