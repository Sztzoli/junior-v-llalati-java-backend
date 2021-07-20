package rooms;

import lombok.RequiredArgsConstructor;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.TypedQuery;
import java.util.List;
import java.util.stream.IntStream;

@RequiredArgsConstructor
public class MeetingRoomsRepositoryImpl implements MeetingRoomsRepository {

    private final EntityManagerFactory entityManagerFactory;

    @Override
    public MeetingRoom save(String name, int width, int length) {
        MeetingRoom meetingRoom = new MeetingRoom(name, width, length);
        EntityManager em = entityManagerFactory.createEntityManager();
        em.getTransaction().begin();
        em.persist(meetingRoom);
        em.getTransaction().commit();
        em.close();
        return meetingRoom;
    }

    @Override
    public List<String> getMeetingroomsOrderedByName() {
        EntityManager em = entityManagerFactory.createEntityManager();
        List<String> roomsName = em.createQuery("select m.name from MeetingRoom m order by m.name", String.class)
                .getResultList();
        em.close();
        return roomsName;
    }

    @Override
    public List<String> getEverySecondMeetingRoom() {
        EntityManager em = entityManagerFactory.createEntityManager();
        List<String> roomsName = em.createQuery("select m.name from MeetingRoom m order by m.name", String.class)
                .getResultList();
        em.close();
        return IntStream.range(0, roomsName.size())
                .filter(n -> n % 2 == 1)
                .mapToObj(roomsName::get)
                .toList();

    }

    @Override
    public List<MeetingRoom> getMeetingRooms() {
        EntityManager em = entityManagerFactory.createEntityManager();
        List<MeetingRoom> meetingRooms = em.createQuery("select m from MeetingRoom m order by m.name", MeetingRoom.class)
                .getResultList();
        em.close();
        return meetingRooms;
    }

    @Override
    public List<MeetingRoom> getExactMeetingRoomByName(String name) {
        EntityManager em = entityManagerFactory.createEntityManager();
        List<MeetingRoom> meetingRooms = em.createQuery("select m from MeetingRoom m where m.name= :name order by m.name", MeetingRoom.class)
                .setParameter("name", name)
                .getResultList();
        em.close();
        return meetingRooms;
    }

    @Override
    public List<MeetingRoom> getMeetingRoomsByPrefix(String name) {
        EntityManager em = entityManagerFactory.createEntityManager();
        List<MeetingRoom> meetingRooms = em.createQuery("select m from MeetingRoom m where m.name like :name order by m.name", MeetingRoom.class)
                .setParameter("name", name + "%")
                .getResultList();
        em.close();
        return meetingRooms;
    }

    @Override
    public void deleteAll() {
        EntityManager em = entityManagerFactory.createEntityManager();
        em.getTransaction().begin();
        em.createQuery("delete from MeetingRoom")
                .executeUpdate();
        em.getTransaction().commit();
        em.close();
    }
}
