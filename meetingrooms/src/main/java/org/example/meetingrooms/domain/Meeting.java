package org.example.meetingrooms.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
public class Meeting {

    private Long id;
    private String name;
    private LocalDateTime start;
    private LocalDateTime end;

    private MeetingRoom meetingRoom;

    public Meeting(Long id, String name, LocalDateTime start, LocalDateTime end) {
        this.id = id;
        this.name = name;
        this.start = start;
        this.end = end;
    }

    public Meeting(String name) {
        this.name = name;
    }
}
