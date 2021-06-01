package org.example.meetingrooms.services;

import org.example.meetingrooms.domain.MeetingRoom;
import org.example.meetingrooms.repositories.MeetingRoomRepository;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public class MeetingRoomServiceImpl implements MeetingRoomService {

    private final MeetingRoomRepository meetingRoomRepository;

    public MeetingRoomServiceImpl(MeetingRoomRepository meetingRoomRepository) {
        this.meetingRoomRepository = meetingRoomRepository;
    }

    @Override
    public MeetingRoom save(MeetingRoom meetingRoom) {
        return meetingRoomRepository.save(meetingRoom);
    }

    @Override
    public List<String> findAllSortedByName() {
        return meetingRoomRepository.findAllSortedByName();
    }

    @Override
    public List<String> findAllSortedByNameReverse() {
        return meetingRoomRepository.findAllSortedByNameReverse();
    }

    @Override
    public List<String> findEverySecondSortedByName() {
        return meetingRoomRepository.findEverySecondSortedByName();
    }

    @Override
    public List<String> findAllSortedByArea() {
        return meetingRoomRepository.findAllSortedByArea()
                .stream().map(MeetingRoom::toNiceString)
                .collect(Collectors.toList());
    }

    @Override
    public void deleteAll() {
        meetingRoomRepository.deleteAll();
    }

    @Override
    public String findByName(String name) {
        Optional<MeetingRoom> meetingRoomOptional = meetingRoomRepository.findByName(name);
        if (meetingRoomOptional.isEmpty()) {
            return null;
        }
        MeetingRoom meetingRoom = meetingRoomOptional.get();
        return meetingRoom.toNiceString();
    }

    @Override
    public String findByNamePrefix(String name) {
        Optional<MeetingRoom> meetingRoomOptional = meetingRoomRepository.findByNamePrefix(name);
        if (meetingRoomOptional.isEmpty()) {
            return null;
        }
        MeetingRoom meetingRoom = meetingRoomOptional.get();
        return meetingRoom.toNiceString();
    }

    @Override
    public List<String> findBiggerAreaThen(int area) {
        return meetingRoomRepository.findBiggerAreaThen(area).stream()
                .map(MeetingRoom::toNiceString)
                .collect(Collectors.toList());
    }
}
