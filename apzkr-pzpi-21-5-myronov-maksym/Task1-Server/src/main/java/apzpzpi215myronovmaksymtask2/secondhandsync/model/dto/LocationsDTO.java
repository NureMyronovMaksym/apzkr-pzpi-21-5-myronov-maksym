package apzpzpi215myronovmaksymtask2.secondhandsync.model.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class LocationsDTO {

    List<LocationDTO> locationsDTO;

}
