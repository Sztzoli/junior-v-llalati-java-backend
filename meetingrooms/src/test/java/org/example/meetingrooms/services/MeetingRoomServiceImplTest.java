package org.example.meetingrooms.services;

import org.example.meetingrooms.domain.MeetingRoom;
import org.example.meetingrooms.repositories.inmemory.InMemoryMeetingRoomRepository;
import org.example.meetingrooms.repositories.jdbctemplate.MariadbMeetingRoomRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.jdbc.core.JdbcTemplate;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class MeetingRoomServiceImplTest {

    public static final String ÁÁÁ = "ÁÁÁ";
    public static final String EEE = "EEE";
    public static final String NiceStringTest = "Meeting room name:ÁÁÁ\nMeeting room width:1\nMeeting room length:1\nMeeting room area:1\n";

    MeetingRoomService meetingRoomService = new MeetingRoomServiceImpl(new MariadbMeetingRoomRepository());

//    MeetingRoomService meetingRoomService = new MeetingRoomServiceImpl(new InMemoryMeetingRoomRepository());

    MeetingRoom savedMeetingRoom1;
    MeetingRoom savedMeetingRoom2;

    @BeforeEach
    void setUp() {

        meetingRoomService.deleteAll();
        MeetingRoom testRoom1 = MeetingRoom.builder().name(ÁÁÁ).length(1).width(1).build();
        MeetingRoom testRoom2 = MeetingRoom.builder().name(EEE).length(2).width(2).build();

        savedMeetingRoom1 = meetingRoomService.save(testRoom1);
        savedMeetingRoom2 = meetingRoomService.save(testRoom2);
    }

    @Test
    void testSave() {
        assertEquals(1L, savedMeetingRoom1.getId());
        assertEquals(ÁÁÁ, savedMeetingRoom1.getName());
        assertEquals(1, savedMeetingRoom1.getWidth());
        assertEquals(1, savedMeetingRoom1.getLength());
    }

    @Test
    void testSaveTwo() {
        assertEquals(2, meetingRoomService.findAllSortedByName().size());
    }

    @Test
    void testFindAllSortedByName() {
        assertEquals(ÁÁÁ, meetingRoomService.findAllSortedByName().get(0));
        assertEquals(EEE, meetingRoomService.findAllSortedByName().get(1));
    }

    @Test
    void testFindAllSortedByNameReverse() {
        assertEquals(EEE, meetingRoomService.findAllSortedByNameReverse().get(0));
        assertEquals(ÁÁÁ, meetingRoomService.findAllSortedByNameReverse().get(1));
    }

    @Test
    void testFindEverySecondSortedByName() {
        MeetingRoom testRoom3 = MeetingRoom.builder().name("XXX").length(1).width(1).build();
        MeetingRoom testRoom4 = MeetingRoom.builder().name("ZZZ").length(2).width(2).build();

        MeetingRoom savedMeetingRoom3 = meetingRoomService.save(testRoom3);
        MeetingRoom savedMeetingRoom4 = meetingRoomService.save(testRoom4);

        List<String> testNames = meetingRoomService.findEverySecondSortedByName();

        assertEquals(2, testNames.size());
        System.out.println(testNames);
        assertEquals(EEE, testNames.get(0));
        assertEquals("ZZZ", testNames.get(1));
    }

    @Test
    void testFindAllSortedByArea() {
        List<String> meetingRooms = meetingRoomService.findAllSortedByArea();


        assertEquals(NiceStringTest, meetingRooms.get(1));
    }

    @Test
    void testFindByNameFound() {
        String testText = meetingRoomService.findByName(ÁÁÁ);

        assertNotNull(testText);
        assertEquals(NiceStringTest, testText);
    }

    @Test
    void testFindByNameNotFound() {
        String testText = meetingRoomService.findByName("ÁÁÁÁ");

        assertNull(testText);
    }

    @Test
    void testFindByNamePrefix() {
        String testText = meetingRoomService.findByNamePrefix("ÁÁ");

        assertNotNull(testText);
        assertEquals(NiceStringTest, testText);
    }

    @Test
    void testFindByNamePrefixNotFound() {
        String testText = meetingRoomService.findByNamePrefix("AAAA");

        assertNull(testText);
    }

    @Test
    void testFindBiggerAreaThen() {
        List<String> list = meetingRoomService.findBiggerAreaThen(0);

        assertEquals(2, list.size());
        assertEquals(NiceStringTest,list.get(0));
    }

    @Test
    void testFindBiggerAreaThenEmptyList() {
        List<String> list = meetingRoomService.findBiggerAreaThen(5);

        assertEquals(0, list.size());

    }

}