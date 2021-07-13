package activitytracker;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.persistence.*;
import java.time.LocalDate;

@Entity
@Data
@NoArgsConstructor
public class TrackPoint {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private LocalDate time;

    private int lat;

    private int lon;

    @ManyToOne
    @ToString.Exclude
    private Activity activity;

    public TrackPoint(LocalDate time, int lat, int lon) {
        this.time = time;
        this.lat = lat;
        this.lon = lon;
    }
}
