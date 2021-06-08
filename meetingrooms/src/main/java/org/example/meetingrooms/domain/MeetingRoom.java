package org.example.meetingrooms.domain;

import lombok.*;

import java.util.ArrayList;
import java.util.List;

@NoArgsConstructor
@Getter
@Setter
@ToString
public class MeetingRoom {

    private Long id;
    private String name;
    private int width;
    private int length;

    private List<Meeting> meetings = new ArrayList<>();

    @Builder
    public MeetingRoom(Long id, String name, int width, int length) {
        this.id = id;
        this.name = name;
        this.width = width;
        this.length = length;
    }

    public int getArea() {
        return width * length;
    }

    public void addMeeting(Meeting meeting) {
        meetings.add(meeting);
    }

    public String toNiceString() {
        return String.format("Meeting room name:%s\nMeeting room width:%d\nMeeting room length:%d\nMeeting room area:%d\n", name, width, length, getArea());
    }
}
