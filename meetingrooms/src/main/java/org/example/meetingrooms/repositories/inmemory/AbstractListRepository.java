package org.example.meetingrooms.repositories.inmemory;

import org.example.meetingrooms.domain.MeetingRoom;

import java.text.Collator;
import java.util.*;
import java.util.stream.Collectors;

public class AbstractListRepository<T extends MeetingRoom> {

    public static final Collator INSTANCE_HU = Collator.getInstance(new Locale("hu", "HU"));
    protected List<T> list = new ArrayList<>();

    T save(T object) {
        if (object != null) {
            if (object.getId() == null) {
                object.setId(nextId());
            }
            list.add(object);
        } else {
            throw new RuntimeException("Object cannot be null");
        }
        return object;
    }

    List<T> findAllSortedBy(Comparator<T> comparator) {
        return list.stream().sorted(comparator).collect(Collectors.toList());
    }

    private Long nextId() {
        Long nextId = (long) (list.size() + 1);
        return nextId;
    }

    Optional<T> findByName(String name) {
        return list.stream()
                .filter(t -> t.getName().equals(name))
                .findFirst();
    }

    Optional<T> findByNamePrefix(String name) {
        return list.stream()
                .filter(t -> t.getName().toUpperCase().startsWith(name.toUpperCase()))
                .findFirst();
    }

    List<T> findBiggerAreaThen(int area){
        return list.stream()
                .filter(t -> t.getArea()>area)
                .collect(Collectors.toList());
    }

    void deleteAll() {
        list.clear();
    }
}
