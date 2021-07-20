package rooms;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class MeetingRoomsRepositoryImplIT {

    MeetingRoomsRepository meetingRoomsRepository;

    @BeforeEach
    void setUp() {
        EntityManagerFactory factory = Persistence.createEntityManagerFactory("pu");
        meetingRoomsRepository = new MeetingRoomsRepositoryImpl(factory);
    }

    @Test
    void saveAndGet() {
        meetingRoomsRepository.save("name1", 1, 2);
        meetingRoomsRepository.save("name2", 2, 4);
        List<String> result = meetingRoomsRepository.getMeetingroomsOrderedByName();

        assertEquals(2, result.size());
        assertEquals("name1", result.get(0));
    }

    @Test
    void getEverySecondMeetingRoom() {
        meetingRoomsRepository.save("nameA1", 1, 2);
        meetingRoomsRepository.save("nameB2", 2, 4);
        meetingRoomsRepository.save("nameC3", 3, 6);
        meetingRoomsRepository.save("nameD4", 4, 8);

        List<String> result = meetingRoomsRepository.getEverySecondMeetingRoom();
        assertEquals(2, result.size());
        assertEquals(List.of("nameB2", "nameD4"), result);
    }

    @Test
    void getMeetingRooms() {
        meetingRoomsRepository.save("nameA1", 1, 2);
        meetingRoomsRepository.save("nameB2", 2, 4);

        List<MeetingRoom> result = meetingRoomsRepository.getMeetingRooms();
        assertEquals(2, result.size());
        assertEquals("nameA1", result.get(0).getName());
    }

    @Test
    void getExactMeetingRoomByName() {
        meetingRoomsRepository.save("nameA1", 1, 2);
        meetingRoomsRepository.save("nameA1", 2, 4);
        meetingRoomsRepository.save("nameA13", 3, 6);

        List<MeetingRoom> result = meetingRoomsRepository.getExactMeetingRoomByName("nameA1");
        assertEquals(2, result.size());
        assertEquals("nameA1", result.get(0).getName());
    }

    @Test
    void getMeetingRoomsByPrefix() {
        meetingRoomsRepository.save("nameA1", 1, 2);
        meetingRoomsRepository.save("nameA1", 2, 4);
        meetingRoomsRepository.save("nameA13", 3, 6);

        List<MeetingRoom> result = meetingRoomsRepository.getMeetingRoomsByPrefix("nameA1");
        assertEquals(3, result.size());
        assertEquals("nameA1", result.get(0).getName());
    }

    @Test
    void deleteAll() {
        meetingRoomsRepository.save("nameA1", 1, 2);
        meetingRoomsRepository.save("nameA1", 2, 4);
        meetingRoomsRepository.save("nameA13", 3, 6);

        meetingRoomsRepository.deleteAll();
        List<MeetingRoom> meetingRooms = meetingRoomsRepository.getMeetingRooms();

        assertEquals(0,meetingRooms.size());
    }


}