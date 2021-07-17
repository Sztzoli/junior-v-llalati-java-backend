package com.example.activitytrackerspringbootsolution;


import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Data
@NoArgsConstructor
@Table(name = "activities")
@SecondaryTable(name = "activity_details",
        pkJoinColumns =@PrimaryKeyJoinColumn(name = "act_id"))
public class Activity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
//    @GeneratedValue(generator = "Act_Gen")
//    @TableGenerator(name = "Act_Gen",table = "act_id_gen",pkColumnName = "id_gen",pkColumnValue = "id_val")
    private Long id;

    @Column(name = "start_time", nullable = false)
    private LocalDateTime startTime;

    @Column(name = "description", nullable = false, length = 200)
    private String desc;

    @CreationTimestamp
    private LocalDateTime createdAt;

    @UpdateTimestamp
    private LocalDateTime updateAt;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 20)
    private Type type;

    @ElementCollection
    private List<String> labels;

    @OneToMany(cascade = {CascadeType.PERSIST, CascadeType.REMOVE},
            mappedBy = "activity")
    @OrderBy(value = "time")
    private List<TrackPoint> trackPoints;

    @ManyToMany(cascade = {CascadeType.PERSIST, CascadeType.REMOVE})
    private List<Area> areas = new ArrayList<>();

    @Column(table = "activity_details")
    private int distance;

    @Column(table = "activity_details")
    private int duration;

    public Activity(LocalDateTime startTime, String desc, Type type) {
        this.startTime = startTime;
        this.desc = desc;
        this.type = type;
    }

    public Activity(LocalDateTime startTime, String desc, Type type, List<String> labels) {
        this.startTime = startTime;
        this.desc = desc;
        this.type = type;
        this.labels = labels;
    }

    public Activity(LocalDateTime startTime, String desc, Type type, int distance, int duration) {
        this.startTime = startTime;
        this.desc = desc;
        this.type = type;
        this.distance = distance;
        this.duration = duration;
    }

    public void addTrackPoint(TrackPoint trackPoint) {
        if (trackPoints == null) {
            trackPoints = new ArrayList<>();
        }
        trackPoint.setActivity(this);
        this.trackPoints.add(trackPoint);
    }

    public void addArea(Area area) {
        area.getActivities().add(this);
        this.areas.add(area);
    }
}
