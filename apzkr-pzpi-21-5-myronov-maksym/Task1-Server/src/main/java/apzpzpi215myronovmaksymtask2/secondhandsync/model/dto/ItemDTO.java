package apzpzpi215myronovmaksymtask2.secondhandsync.model.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ItemDTO {

    private Long id;

    private String name;

    private String type;

    private String color;

    private String brand;

    private String image;

    private Boolean isSold;

    private Long packageId;

}
