package com.example.fightergame;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("api/contest")
@RequiredArgsConstructor
public class ContestController {

    private final ContestService contestService;

    @GetMapping("/fighter1/{fighter1Id}/fighter2/{fighter2Id}")
    public MatchDto fight(
            @PathVariable Long fighter1Id,
            @PathVariable Long fighter2Id
    ) {
        return contestService.fight(fighter1Id, fighter2Id);
    }

    @GetMapping()
    public List<MatchDto> receiveAll(){
        return contestService.receiveAll();
    }

    @GetMapping("/{id}")
    public MatchDto receiveMatch(@PathVariable Long id) {
        return contestService.findMatchById(id);
    }
}
