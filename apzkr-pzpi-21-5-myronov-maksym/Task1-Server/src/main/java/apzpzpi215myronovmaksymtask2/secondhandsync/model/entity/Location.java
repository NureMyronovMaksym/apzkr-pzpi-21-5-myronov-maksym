package apzpzpi215myronovmaksymtask2.secondhandsync.model.entity;

import lombok.Data;
import jakarta.persistence.*;
import java.util.Date;

@Data
@Entity
@Table(name = "locations")
public class Location {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String place;
    private Date timeArrival;
    private Date timeDeparture;
}