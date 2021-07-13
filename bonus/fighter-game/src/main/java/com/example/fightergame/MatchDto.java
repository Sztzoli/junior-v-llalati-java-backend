package com.example.fightergame;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class MatchDto {

    private Long id;
    private Fighter fighterOne;
    private Fighter fighterTwo;
    private Result result;
}
