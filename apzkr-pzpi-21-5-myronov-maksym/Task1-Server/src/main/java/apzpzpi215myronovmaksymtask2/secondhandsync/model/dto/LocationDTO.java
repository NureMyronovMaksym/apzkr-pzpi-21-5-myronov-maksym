package apzpzpi215myronovmaksymtask2.secondhandsync.model.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Timestamp;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class LocationDTO {

    private Long id;

    private String place;

    private Timestamp timeArrival;

    private Timestamp timeDeparture;

}

