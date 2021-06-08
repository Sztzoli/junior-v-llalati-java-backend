package org.example.meetingrooms.repositories.jdbctemplate;

import org.example.meetingrooms.domain.Meeting;
import org.example.meetingrooms.domain.MeetingRoom;
import org.example.meetingrooms.repositories.MeetingRoomRepository;
import org.flywaydb.core.Flyway;
import org.mariadb.jdbc.MariaDbDataSource;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.ResultSetExtractor;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;

import java.sql.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class MariadbMeetingRoomRepository implements MeetingRoomRepository {

    private JdbcTemplate jdbcTemplate;

    public MariadbMeetingRoomRepository() {
        MariaDbDataSource dataSource;
        try {
            dataSource = new MariaDbDataSource();
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
        jdbcTemplate = new JdbcTemplate(dataSource);
    }

    @Override
    public MeetingRoom save(MeetingRoom meetingRoom) {


        Long id = saveAndGetId(meetingRoom);

        return jdbcTemplate
                .queryForObject("select id, room_name, width, length from meeting_rooms where id = ?",
                        new Object[]{id}, MariadbMeetingRoomRepository::mapRowMeetingRoom);
    }

    @Override
    public List<String> findAllSortedByName() {
        String sql = "select room_name from meeting_rooms order by room_name";
        return jdbcTemplate
                .query(sql, (rs, i) -> rs.getString(1));
    }

    @Override
    public List<String> findAllSortedByNameReverse() {
        String sql = "select room_name from meeting_rooms order by room_name desc";
        return jdbcTemplate
                .query(sql, (rs, i) -> rs.getString(1));
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
        return jdbcTemplate
                .query(sql, (rs, i) -> rs.getString("room_name"));
    }

    @Override
    public List<MeetingRoom> findAllSortedByArea() {
        String sql = "select id, room_name, width, length from meeting_rooms order by (width*length) desc";
        return jdbcTemplate.query(sql, MariadbMeetingRoomRepository::mapRowMeetingRoom);
    }

    @Override
    public Optional<MeetingRoom> findByName(String name) {
        String sql = "select id, room_name, width, length from meeting_rooms where room_name = ?";
        MeetingRoom meetingRoom;

        try {
            meetingRoom = jdbcTemplate.queryForObject(sql,
                    new Object[]{name},
                    MariadbMeetingRoomRepository::mapRowMeetingRoom);
        } catch (EmptyResultDataAccessException e) {
            return Optional.empty();
        }
        return Optional.of(meetingRoom);
    }

    @Override
    public Optional<MeetingRoom> findByNamePrefix(String name) {
        String sql = "select id, room_name, width, length from meeting_rooms where room_name like ?";
        MeetingRoom meetingRoom;

        try {
            meetingRoom = jdbcTemplate.queryForObject(sql,
                    new Object[]{name + "%"},
                    MariadbMeetingRoomRepository::mapRowMeetingRoom);
        } catch (EmptyResultDataAccessException e) {
            return Optional.empty();
        }
        return Optional.of(meetingRoom);
    }

    @Override
    public List<MeetingRoom> findBiggerAreaThen(int area) {
        String sql = "select id, room_name, width, length from meeting_rooms where (width * length) > ?";
        return jdbcTemplate.query(sql,
                new Object[]{area},
                MariadbMeetingRoomRepository::mapRowMeetingRoom);

    }

    @Override
    public List<MeetingRoom> findAllWithMeetings() {
        String sql = """
                SELECT mt.id, mt.room_name, mt.width, mt.length, m.id, m.name, m.time_start, m.time_end, m.room_id
                FROM meeting_rooms AS mt
                LEFT JOIN meetings AS m ON mt.id = m.room_id
                ORDER by mt.id""";
        return jdbcTemplate.query(sql, resultSet -> {
             List<MeetingRoom> list = new ArrayList<>();
             Long idActual = 0L;
             int counter = -1;
             while (resultSet.next()) {
                 Long id = resultSet.getLong("mt.id");
                 MeetingRoom meetingRoom = new MeetingRoom();
                 if (!idActual.equals(id)){
                     meetingRoom.setId(id);
                     meetingRoom.setName(resultSet.getString("mt.room_name"));
                     meetingRoom.setWidth(resultSet.getInt("mt.width"));
                     meetingRoom.setLength(resultSet.getInt("mt.length"));
                     list.add(meetingRoom);
                     idActual = id;
                     counter++;
                 }
                 Long meetingId = resultSet.getLong("m.id");
                 if (meetingId!=null) {
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
         }
         );
    }


    @Override
    public MeetingRoom saveWithMeeting(MeetingRoom meetingRoom) {
        Long roomId = saveAndGetId(meetingRoom);
        for (Meeting meeting :meetingRoom.getMeetings()) {
            jdbcTemplate.update("insert into meetings (name, time_start, time_end, room_id) value (?,?,?,?)",
                    meeting.getName(),
                    meeting.getStart(),
                    meeting.getEnd(),
                    roomId);
        }




        MeetingRoom savedRoom = jdbcTemplate
                .queryForObject("select id, room_name, width, length from meeting_rooms where id = ?",
                        MariadbMeetingRoomRepository::mapRowMeetingRoom, new Object[]{roomId});
        List<Meeting> savedMeetings = jdbcTemplate
                .query("select id, name, time_start, time_end, room_id from meetings where room_id = ?",
                                                (rs,i) -> new Meeting(
                                rs.getLong("id"),
                                rs.getString("name"),
                                rs.getTimestamp("time_start").toLocalDateTime(),
                                rs.getTimestamp("time_end").toLocalDateTime()
                                ),
                        new Object[]{roomId});
       savedRoom.setMeetings(savedMeetings);
       return savedRoom;
    }

    private Long saveAndGetId(MeetingRoom meetingRoom) {
        String sql = "insert into meeting_rooms(room_name, width, length) values (?,?,?)";
        KeyHolder keyHolder = new GeneratedKeyHolder();

        jdbcTemplate.update(connection -> {
                    PreparedStatement ps =
                            connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
                    ps.setString(1, meetingRoom.getName());
                    ps.setInt(2, meetingRoom.getWidth());
                    ps.setInt(3, meetingRoom.getLength());
                    return ps;
                }, keyHolder
        );

        return keyHolder.getKey().longValue();
    }


    @Override
    public void deleteAll() {
        jdbcTemplate.update("delete from meeting_rooms");

    }


    private static MeetingRoom mapRowMeetingRoom(ResultSet rs, int i) throws SQLException {
        return MeetingRoom.builder()
                .id(rs.getLong("id"))
                .name(rs.getString("room_name"))
                .width(rs.getInt("width"))
                .length(rs.getInt("length"))
                .build();
    }
}
