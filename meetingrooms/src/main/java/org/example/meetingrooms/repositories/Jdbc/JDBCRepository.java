package org.example.meetingrooms.repositories.Jdbc;

import org.example.meetingrooms.domain.Meeting;
import org.example.meetingrooms.domain.MeetingRoom;
import org.example.meetingrooms.repositories.MeetingRoomRepository;
import org.flywaydb.core.Flyway;
import org.mariadb.jdbc.MariaDbDataSource;

import java.sql.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Optional;

public class JDBCRepository implements MeetingRoomRepository {

    private final MariaDbDataSource dataSource;

    public JDBCRepository() {
        try {
            dataSource = new MariaDbDataSource();
            dataSource.setUrl("jdbc:mariadb://localhost:3306/employees?useUnicode=true");
            dataSource.setUser("employees");
            dataSource.setPassword("employees");
        } catch (SQLException e) {
            throw new IllegalStateException("Cannot create datasource", e);
        }
        Flyway flyway = Flyway.configure().dataSource(dataSource).load();
        flyway.clean();
        flyway.migrate();
    }

    @Override
    public MeetingRoom save(MeetingRoom meetingRoom) {
        Long id;
        try (Connection conn = dataSource.getConnection()) {
            id = saveAndGetId(meetingRoom, conn);
            return findById(id, conn);
        } catch (SQLException sqlException) {
            throw new IllegalArgumentException("Error by insert", sqlException);
        }
    }

    @Override
    public List<String> findAllSortedByName() {
        String sql = "select room_name from meeting_rooms order by room_name";
        return sortedRoomList(sql);
    }

    @Override
    public List<String> findAllSortedByNameReverse() {
        String sql = "select room_name from meeting_rooms order by room_name desc";
        return sortedRoomList(sql);
    }

    @Override
    public List<String> findEverySecondSortedByName() {
        String sql = """
                SELECT *\s
                FROM (\s
                    SELECT\s
                        @row := @row +1 AS rownum, room_name\s
                    FROM (\s
                        SELECT @row :=0) r, meeting_rooms\s
                    ) ranked\s
                WHERE rownum % 2 = 0
                """;
        return sortedRoomList(sql);
    }

    @Override
    public List<MeetingRoom> findAllSortedByArea() {
        String sql = "select id, room_name, width, length from meeting_rooms order by (width*length) desc";
        try (Connection conn = dataSource.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            return getMeetingRooms(rs);
        } catch (SQLException sqlException) {
            throw new IllegalArgumentException("Error by select:", sqlException);
        }
    }

    @Override
    public Optional<MeetingRoom> findByName(String name) {
        String sql = "select id, room_name, width, length from meeting_rooms where room_name = ?";
        return getOptionalMeetingRoom(name, sql);
    }

    @Override
    public Optional<MeetingRoom> findByNamePrefix(String name) {
        String sql = "select id, room_name, width, length from meeting_rooms where room_name like ?";
        return getOptionalMeetingRoom(name + "%", sql);
    }

    @Override
    public List<MeetingRoom> findBiggerAreaThen(int area) {
        String sql = "select id, room_name, width, length from meeting_rooms where (width * length) > ?";
        return getMeetingRooms(area, sql);
    }

    @Override
    public void deleteAll() {
        String sql = "delete from meeting_rooms";
        try (Connection conn = dataSource.getConnection();
             Statement stmt = conn.createStatement()) {
            stmt.executeUpdate(sql);
        } catch (SQLException sqlException) {
            throw new IllegalArgumentException("Error by delete", sqlException);
        }
    }

    @Override
    public MeetingRoom saveWithMeeting(MeetingRoom meetingRoom) {
        try (Connection conn = dataSource.getConnection()) {
            conn.setAutoCommit(false);
            Long roomId = saveAndGetId(meetingRoom, conn);
            saveMeetings(meetingRoom, conn, roomId);
            MeetingRoom savedMeetingRoom = findById(roomId, conn);
            List<Meeting> meetings = getMeetings(conn, roomId);
            savedMeetingRoom.setMeetings(meetings);
            return savedMeetingRoom;
        } catch (SQLException sqlException) {
            throw new IllegalArgumentException("Error by insert", sqlException);
        }
    }

    private List<Meeting> getMeetings(Connection conn, Long roomId) throws SQLException {
        try (PreparedStatement stmt = conn.prepareStatement("select id, name, time_start, time_end, room_id from meetings where room_id = ?")) {
            stmt.setLong(1, roomId);
            try (ResultSet rs = stmt.executeQuery()) {
                List<Meeting> meetings = new ArrayList<>();
                while (rs.next()) {
                    meetings.add(new Meeting(
                            rs.getLong("id"),
                            rs.getString("name"),
                            rs.getTimestamp("time_start").toLocalDateTime(),
                            rs.getTimestamp("time_end").toLocalDateTime()
                    ));
                }
                return meetings;
            }
        }
    }

    private void saveMeetings(MeetingRoom meetingRoom, Connection conn, Long roomId) throws SQLException {
        try (PreparedStatement stmt = conn.prepareStatement("insert into meetings (name, time_start, time_end, room_id) value (?,?,?,?)")) {
            Iterator<Meeting> it = meetingRoom.getMeetings().iterator();
            while (it.hasNext()) {
                Meeting meeting = it.next();
                Timestamp start = null;
                if (meeting.getStart() != null) {
                    start = Timestamp.valueOf(meeting.getStart());
                }
                Timestamp end = null;
                if (meeting.getEnd() != null) {
                    start = Timestamp.valueOf(meeting.getEnd());
                }
                stmt.setString(1, meeting.getName());
                stmt.setTimestamp(2, start);
                stmt.setTimestamp(3, end);
                stmt.setLong(4, roomId);
                stmt.executeUpdate();
            }
            conn.commit();
        } catch (SQLException sqlException) {
            conn.rollback();
        }
    }

    private long executeAndGetGeneratedKey(PreparedStatement stmt) {
        try (
                ResultSet rs = stmt.getGeneratedKeys();
        ) {
            if (rs.next()) {
                return rs.getLong(1);
            }
            throw new IllegalStateException("Cannot get id");
        } catch (SQLException es) {
            throw new IllegalStateException("Error by insert", es);
        }
    }

    private Long saveAndGetId(MeetingRoom meetingRoom, Connection conn) throws SQLException {
        Long id;
        try (PreparedStatement stmt = conn.prepareStatement("insert into meeting_rooms(room_name, width, length) values(?,?,?)",
                Statement.RETURN_GENERATED_KEYS)) {
            stmt.setString(1, meetingRoom.getName());
            stmt.setInt(2, meetingRoom.getWidth());
            stmt.setInt(3, meetingRoom.getLength());
            stmt.executeUpdate();

            id = executeAndGetGeneratedKey(stmt);
        }
        return id;
    }

    private MeetingRoom findById(Long id, Connection conn) throws SQLException {
        MeetingRoom meetingRoom = new MeetingRoom();
        try (PreparedStatement stmt2 = conn.prepareStatement("select room_name, width, length from meeting_rooms where id = ?")) {
            stmt2.setLong(1, id);
            try (ResultSet rs = stmt2.executeQuery()) {
                if (rs.next()) {
                    meetingRoom.setId(id);
                    meetingRoom.setName(rs.getString("room_name"));
                    meetingRoom.setWidth(rs.getInt("width"));
                    meetingRoom.setLength(rs.getInt("length"));
                }
            }
        }
        return meetingRoom;
    }

    private List<String> sortedRoomList(String sql) {
        try (
                Connection conn = dataSource.getConnection();
                Statement stmt = conn.createStatement();
                ResultSet rs = stmt.executeQuery(sql)
        ) {
            List<String> roomNames = new ArrayList<>();
            while (rs.next()) {
                String name = rs.getString("room_name");
                roomNames.add(name);
            }
            return roomNames;
        } catch (SQLException sqlException) {
            throw new IllegalArgumentException("Error by select", sqlException);
        }
    }

    private Optional<MeetingRoom> getOptionalMeetingRoom(String name, String sql) {
        try (Connection conn = dataSource.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, name);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return Optional.of(MeetingRoom.builder()
                            .id(rs.getLong("id"))
                            .name(rs.getString("room_name"))
                            .width(rs.getInt("width"))
                            .length(rs.getInt("length"))
                            .build());
                }
            }
        } catch (SQLException sqlException) {
            throw new IllegalArgumentException("Error by select", sqlException);
        }
        return Optional.empty();
    }

    private List<MeetingRoom> getMeetingRooms(int area, String sql) {
        try (Connection conn = dataSource.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, area);
            try (ResultSet rs = stmt.executeQuery()) {
                return getMeetingRooms(rs);
            }
        } catch (SQLException sqlException) {
            throw new IllegalArgumentException("Error by select", sqlException);
        }
    }

    private List<MeetingRoom> getMeetingRooms(ResultSet rs) throws SQLException {
        List<MeetingRoom> meetingRooms = new ArrayList<>();
        while (rs.next()) {
            meetingRooms.add(MeetingRoom.builder()
                    .id(rs.getLong("id"))
                    .name(rs.getString("room_name"))
                    .width(rs.getInt("width"))
                    .length(rs.getInt("length"))
                    .build());
        }
        return meetingRooms;
    }

    @Override
    public List<MeetingRoom> findAllWithMeetings() {
        String sql = """
                SELECT mt.id, mt.room_name, mt.width, mt.length, m.id, m.name, m.time_start, m.time_end, m.room_id
                FROM meeting_rooms AS mt
                LEFT JOIN meetings AS m ON mt.id = m.room_id
                ORDER by mt.id""";

        try (Connection conn = dataSource.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet resultSet = stmt.executeQuery(sql)) {
            List<MeetingRoom> list = new ArrayList<>();
            Long idActual = 0L;
            int counter = -1;
            while (resultSet.next()) {
                Long id = resultSet.getLong("mt.id");
                MeetingRoom meetingRoom = new MeetingRoom();
                if (!idActual.equals(id)) {
                    meetingRoom.setId(id);
                    meetingRoom.setName(resultSet.getString("mt.room_name"));
                    meetingRoom.setWidth(resultSet.getInt("mt.width"));
                    meetingRoom.setLength(resultSet.getInt("mt.length"));
                    list.add(meetingRoom);
                    idActual = id;
                    counter++;
                }
                Long meetingId = resultSet.getLong("m.id");
                if (meetingId != null) {
                    LocalDateTime start = null;
                    if (resultSet.getTimestamp("m.time_start") != null) {
                        start = resultSet.getTimestamp("m.time_start").toLocalDateTime();
                    }
                    LocalDateTime end = null;
                    if (resultSet.getTimestamp("m.time_end") != null) {
                        end = resultSet.getTimestamp("m.time_end").toLocalDateTime();
                    }
                    MeetingRoom meetingRoom1 = list.get(counter);
                    Meeting meeting = new Meeting();
                    meeting.setId(meetingId);
                    meeting.setName(resultSet.getString("m.name"));
                    meeting.setStart(start);
                    meeting.setEnd(end);
                    meeting.setMeetingRoom(meetingRoom1);
                    meetingRoom1.addMeeting(meeting);
                }

            }
            return list;

        } catch (SQLException sqlException) {
            throw new IllegalArgumentException("Error by select", sqlException);
        }
    }
}
