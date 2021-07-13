package com.example.fightergame;

import lombok.Data;

@Data
public class Fighter {

    public Fighter(Long id, String name, int stamina, int damage) {
        this.id = id;
        this.name = name;
        this.stamina = stamina;
        this.damage = damage;
    }

    private Long id;
    private String name;
    private int stamina;
    private int damage;
    private int point;


    public Result attack(Fighter another) {
        int oneLife = this.stamina;
        int anotherLife = another.stamina;
        while (oneLife > 0 && anotherLife > 0) {
            oneLife -= another.damage;
            anotherLife -= this.damage;
        }
        if (oneLife>0) {
            this.point += 1;
            another.point -= 1;
            return Result.FIGHTER_ONE_WINNER;
        }
        if (anotherLife>0) {
            this.point -= 1;
            another.point += 1;
            return Result.FIGHTER_TWO_WINNER;
        }
        else {
            return Result.DRAW;
        }
    }
}
