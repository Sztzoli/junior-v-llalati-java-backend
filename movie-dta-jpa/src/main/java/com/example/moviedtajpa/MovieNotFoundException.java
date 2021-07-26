package com.example.moviedtajpa;

import org.zalando.problem.AbstractThrowableProblem;
import org.zalando.problem.Status;

import java.net.URI;

public class MovieNotFoundException extends AbstractThrowableProblem {

    public MovieNotFoundException(Long id) {
        super(
                URI.create("movies/movie-not-found"),
                "Not Found",
                Status.NOT_FOUND,
                String.format("movie not found by id %d", id)
        );
    }
}
