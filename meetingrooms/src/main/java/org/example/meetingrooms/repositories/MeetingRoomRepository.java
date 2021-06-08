package org.example.meetingrooms.repositories;

import org.example.meetingrooms.domain.Meeting;
import org.example.meetingrooms.domain.MeetingRoom;

import java.util.List;
import java.util.Optional;

public interface MeetingRoomRepository {

    MeetingRoom save(MeetingRoom meetingRoom);

    List<String> findAllSortedByName();

    List<String> findAllSortedByNameReverse();

    List<String> findEverySecondSortedByName();

    List<MeetingRoom> findAllSortedByArea();

    Optional<MeetingRoom> findByName(String name);

    Optional<MeetingRoom> findByNamePrefix(String name);

    List<MeetingRoom> findBiggerAreaThen(int area);

    MeetingRoom saveWithMeeting(MeetingRoom meetingRoom);

    List<MeetingRoom> findAllWithMeetings();

    void deleteAll();

}
