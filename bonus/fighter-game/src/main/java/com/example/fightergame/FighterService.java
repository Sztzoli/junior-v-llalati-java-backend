package com.example.fightergame;

import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicLong;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class FighterService {

    private List<Fighter> fighters = new ArrayList<>();
    private AtomicLong idGenerate = new AtomicLong();
    private final ModelMapper mapper;

    public List<FighterDto> receiveAll() {
        return fighters.stream().map(f -> mapper.map(f, FighterDto.class)).collect(Collectors.toList());
    }

    public FighterDto findFighterById(Long id) {
        Fighter fighter = getFighterById(id);
        return mapper.map(fighter, FighterDto.class);
    }

    public FighterDto addFighter(SaveFighterCommand command) {
        Fighter fighter = new Fighter(idGenerate.incrementAndGet(), command.getName(), command.getStamina(), command.getDamage());
        fighters.add(fighter);
        return mapper.map(fighter, FighterDto.class);
    }

    public FighterDto updateFighter(Long id, UpdateFighterCommand command) {
        Fighter fighter = getFighterById(id);
        fighter.setName(command.getName());
        fighter.setDamage(command.getDamage());
        fighter.setStamina(command.getStamina());
        return mapper.map(fighter, FighterDto.class);
    }

    public void deleteFighterById(Long id) {
        Fighter fighter = getFighterById(id);
        fighters.remove(fighter);
    }

    public Fighter getFighterById(Long id) {
        return fighters.stream().filter(f -> f.getId().equals(id)).findFirst().orElseThrow(() -> new FighterNotFoundException(id));
    }

    public void deleteAll() {
        fighters.clear();
        idGenerate = new AtomicLong();
    }

}
