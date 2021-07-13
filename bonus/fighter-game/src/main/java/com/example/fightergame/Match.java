package com.example.fightergame;

import lombok.Data;

@Data
public class Match {

    private Long id;
    private Fighter fighterOne;
    private Fighter fighterTwo;
    private Result result;

    public Match(Long id, Fighter fighterOne, Fighter fighterTwo) {
        this.id = id;
        this.fighterOne = fighterOne;
        this.fighterTwo = fighterTwo;
        fight();
    }

    public void fight() {
        this.result = fighterOne.attack(fighterTwo);
    }
}
