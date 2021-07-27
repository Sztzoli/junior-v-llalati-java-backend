package com.example.libraryapp;

import org.zalando.problem.AbstractThrowableProblem;
import org.zalando.problem.Status;

import java.net.URI;

public class AuthorNotFoundException extends AbstractThrowableProblem {
    public AuthorNotFoundException(Long id) {
        super(
                URI.create("author/author-not-found"),
                "Not Found",
                Status.NOT_FOUND,
                String.format("author not found by id: %d", id)
        );
    }
}
