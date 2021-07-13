package com.example.fightergame;

import org.zalando.problem.AbstractThrowableProblem;
import org.zalando.problem.Status;

import java.net.URI;

public class FighterNotFoundException extends AbstractThrowableProblem {
    public FighterNotFoundException(Long id) {
        super(URI.create("fighter-game/fighter-not-found"),
                "Not Found",
                Status.NOT_FOUND,
                String.format("fighter not found by id: %d",id));
    }
}
