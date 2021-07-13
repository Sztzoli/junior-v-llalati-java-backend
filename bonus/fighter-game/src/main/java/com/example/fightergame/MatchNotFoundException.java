package com.example.fightergame;

import org.zalando.problem.AbstractThrowableProblem;
import org.zalando.problem.Status;

import java.net.URI;

public class MatchNotFoundException extends AbstractThrowableProblem {
    public MatchNotFoundException(Long id) {

        super(URI.create("fighter-game/match-not-found"),
                "Not Found",
                Status.NOT_FOUND,
                String.format("match not found by id: %d",id));
    }
}
