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
        meetingRoomsRepository.save("name1",1,2);
        meetingRoomsRepository.save("name2",2,4);
        List<String> result = meetingRoomsRepository.getMeetingroomsOrderedByName();

        assertEquals(2, result.size());
        assertEquals("name1",result.get(0));
    }


}