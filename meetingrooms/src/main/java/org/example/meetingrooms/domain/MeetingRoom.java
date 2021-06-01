package org.example.meetingrooms.domain;

import lombok.*;

@NoArgsConstructor
@Getter
@Setter
@ToString
public class MeetingRoom extends BaseEntity {

    private String name;
    private int width;
    private int length;

    @Builder
    public MeetingRoom(Long id, String name, int width, int length) {
        super(id);
        this.name = name;
        this.width = width;
        this.length = length;

    }

    public int getArea() {
        return width * length;
    }

    public String toNiceString() {
        return String.format("Meeting room name:%s\nMeeting room width:%d\nMeeting room length:%d\nMeeting room area:%d\n", name, width, length, getArea());
    }
}
