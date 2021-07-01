package org.training360.musicstore;

import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.stereotype.Service;

import java.lang.reflect.Type;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicLong;
import java.util.stream.Collectors;

@Service
public class MusicStoreService {

    private AtomicLong idGenerator = new AtomicLong();

    private final ModelMapper mapper;

    private List<Instrument> instruments = new ArrayList<>();

    public MusicStoreService(ModelMapper mapper) {
        this.mapper = mapper;
    }


    public List<InstrumentDTO> getInstruments(Optional<InstrumentType> type, Optional<Integer> price) {
        Type targetListType = new TypeToken<List<InstrumentDTO>>() {
        }.getType();
        List<Instrument> result = instruments.stream()
                .filter(i -> type.isEmpty() || i.getType() == type.get())
                .filter(i -> price.isEmpty() || i.getPrice() == price.get())
                .collect(Collectors.toList());
        return mapper.map(result, targetListType);
    }

    public InstrumentDTO addInstrument(CreateInstrumentCommand command) {
        Instrument instrument =
                new Instrument(idGenerator.incrementAndGet(), command.getBrand(),
                        command.getType(), command.getPrice(), LocalDate.now());
        instruments.add(instrument);
        return mapper.map(instrument, InstrumentDTO.class);
    }

    public void deleteInstrument() {
        instruments.clear();
        idGenerator = new AtomicLong();
    }

    public InstrumentDTO findById(Long id) {
        Instrument instrument = findInstrumentById(id);
        return mapper.map(instrument, InstrumentDTO.class);
    }

    public InstrumentDTO updatePriceById(Long id, UpdatePriceCommand command) {
        Instrument instrument = findInstrumentById(id);
        System.out.println(instrument);
        if (instrument.getPrice() != command.getPrice()) {
            instrument.setPostDate(LocalDate.now());
            instrument.setPrice(command.getPrice());
        }
        System.out.println(instrument);
        return mapper.map(instrument, InstrumentDTO.class);
    }

    public void deleteById(Long id) {
        Instrument instrument = findInstrumentById(id);
        instruments.remove(instrument);
    }

    private Instrument findInstrumentById(Long id) {
        return instruments.stream()
                .filter(i -> i.getId().equals(id))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("Instrument not fond by id: " + id));
    }
}
