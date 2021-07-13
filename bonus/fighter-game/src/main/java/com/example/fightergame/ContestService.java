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
public class ContestService {

    public List<Match> matches = new ArrayList<>();
    private AtomicLong idGenerator = new AtomicLong();

    private final ModelMapper mapper;
    private final FighterService fighterService;

    public MatchDto fight(Long fighter1Id, Long fighter2Id) {
        Fighter fighterOne = fighterService.getFighterById(fighter1Id);
        Fighter fighterTwo = fighterService.getFighterById(fighter2Id);
        Match match = new Match(idGenerator.getAndIncrement(), fighterOne, fighterTwo);
        return mapper.map(match, MatchDto.class);
    }

    public List<MatchDto> receiveAll() {
        return matches.stream().map(m -> mapper.map(m, MatchDto.class)).collect(Collectors.toList());
    }

    public MatchDto findMatchById(Long id) {
        Match match = matches.stream().filter(m -> m.getId().equals(id)).findFirst().orElseThrow(() -> new MatchNotFoundException(id));
        return mapper.map(match, MatchDto.class);
    }

    public void deleteAll(){
        fighterService.deleteAll();
        matches.clear();
        idGenerator = new AtomicLong();
    }
}
