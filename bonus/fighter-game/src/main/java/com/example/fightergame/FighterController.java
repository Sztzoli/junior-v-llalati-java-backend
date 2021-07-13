package com.example.fightergame;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/fighter")
@RequiredArgsConstructor
public class FighterController {

    private final FighterService fighterService;

    @GetMapping()
    public List<FighterDto> receiveAll() {
        return fighterService.receiveAll();
    }

    @GetMapping("/{id}")
    public FighterDto receiveFighterById(@PathVariable Long id) {
        return fighterService.findFighterById(id);
    }

    @PostMapping()
    @ResponseStatus(HttpStatus.CREATED)
    public FighterDto addFighter(@RequestBody SaveFighterCommand command) {
        return fighterService.addFighter(command);
    }

    @PutMapping("/{id}")
    public FighterDto updateFighter(
            @PathVariable Long id,
            @RequestBody UpdateFighterCommand command
    ) {
        return fighterService.updateFighter(id, command);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteFighter(@PathVariable Long id) {
        fighterService.deleteFighterById(id);
    }
}
