create table meetings (id bigint not null auto_increment,
                        name varchar(255),
                        time_start timestamp,
                        time_end timestamp,
                        room_id bigint,
                        primary key (id),
                        CONSTRAINT `fk_meeting_meeting_room`
                            foreign key(room_id)
                                references meeting_rooms (id) ON DELETE CASCADE ON UPDATE RESTRICT)
