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
public class PackageDTO {

    private Long id;

    private Long itemsAmount;

    private String status;

    private Timestamp releaseDate;

    private Long userId;

    private Long locationId;

}
