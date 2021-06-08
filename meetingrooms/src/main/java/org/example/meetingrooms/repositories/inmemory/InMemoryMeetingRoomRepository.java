package org.example.meetingrooms.repositories.inmemory;

import org.example.meetingrooms.domain.MeetingRoom;
import org.example.meetingrooms.repositories.MeetingRoomRepository;

import java.text.Collator;
import java.util.Comparator;
import java.util.List;
import java.util.Locale;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.IntStream;


public class InMemoryMeetingRoomRepository extends AbstractListRepository<MeetingRoom> implements MeetingRoomRepository {

    public static final Collator INSTANCE_HU = Collator.getInstance(new Locale("hu", "HU"));

    @Override
    public MeetingRoom save(MeetingRoom meetingRoom) {
        return super.save(meetingRoom);
    }

    @Override
    public List<String> findAllSortedByName() {
        return super.findAllSortedBy(Comparator.comparing(MeetingRoom::getName, INSTANCE_HU)).stream()
                .map(MeetingRoom::getName)
                .collect(Collectors.toList());
    }

    @Override
    public List<String> findAllSortedByNameReverse() {
        return super.findAllSortedBy(Comparator.comparing(MeetingRoom::getName, INSTANCE_HU).reversed()).stream()
                .map(MeetingRoom::getName)
                .collect(Collectors.toList());
    }

    @Override
    public List<String> findEverySecondSortedByName() {
        return IntStream.range(0, list.size())
                .filter(i -> i%2 != 0)
                .mapToObj(i -> findAllSortedByName().get(i))
                .collect(Collectors.toList());
    }

    @Override
    public List<MeetingRoom> findAllSortedByArea() {
        return super.findAllSortedBy(Comparator.comparing(MeetingRoom::getArea).reversed());
    }

    @Override
    public Optional<MeetingRoom> findByName(String name) {
        return super.findByName(name);
    }

    @Override
    public Optional<MeetingRoom> findByNamePrefix(String name) {
        return super.findByNamePrefix(name);
    }

    @Override
    public List<MeetingRoom> findBiggerAreaThen(int area) {
        return super.findBiggerAreaThen(area);
    }

    @Override
    public void deleteAll() {
        super.deleteAll();
    }

    @Override
    public MeetingRoom saveWithMeeting(MeetingRoom meetingRoom) {
        return null;
    }

    @Override
    public List<MeetingRoom> findAllWithMeetings() {
        return null;
    }
}
