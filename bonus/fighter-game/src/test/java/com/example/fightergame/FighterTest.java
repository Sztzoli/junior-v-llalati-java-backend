package com.example.fightergame;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class FighterTest {

    @Test
    void fight() {
        Fighter one = new Fighter(1L, "John Doe", 100, 50);
        Fighter two = new Fighter(2L, "John Doe", 100, 10);

        Result result = one.attack(two);
        assertEquals(Result.FIGHTER_ONE_WINNER, result);
        assertEquals(100, one.getStamina());
        assertEquals(100, two.getStamina());
        assertEquals(1, one.getPoint());
        assertEquals(-1, two.getPoint());
    }

    @Test
    void fightTwoWin() {
        Fighter one = new Fighter(1L, "John Doe", 100, 10);
        Fighter two = new Fighter(2L, "John Doe", 100, 50);

        Result result = one.attack(two);
        assertEquals(Result.FIGHTER_TWO_WINNER, result);
        assertEquals(100, one.getStamina());
        assertEquals(100, two.getStamina());
        assertEquals(-1, one.getPoint());
        assertEquals(1, two.getPoint());
    }

    @Test
    void fightDraw() {
        Fighter one = new Fighter(1L, "John Doe", 100, 50);
        Fighter two = new Fighter(2L, "John Doe", 100, 50);

        Result result = one.attack(two);
        assertEquals(Result.DRAW, result);
        assertEquals(100, one.getStamina());
        assertEquals(100, two.getStamina());
        assertEquals(0, one.getPoint());
        assertEquals(0, two.getPoint());
    }
}