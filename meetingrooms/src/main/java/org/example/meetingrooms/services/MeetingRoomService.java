package org.example.meetingrooms.services;

import org.example.meetingrooms.domain.MeetingRoom;
import org.example.meetingrooms.repositories.MeetingRoomRepository;

import java.util.List;

public interface MeetingRoomService {

    MeetingRoom save(MeetingRoom meetingRoom);

    List<String> findAllSortedByName();

    List<String> findAllSortedByNameReverse();

    List<String> findEverySecondSortedByName();

    List<String> findAllSortedByArea();

    String findByName(String name);

    String findByNamePrefix(String name);

    List<String> findBiggerAreaThen(int area);

    MeetingRoom saveWithMeetings(MeetingRoom meetingRoom);

    void deleteAll();
}
