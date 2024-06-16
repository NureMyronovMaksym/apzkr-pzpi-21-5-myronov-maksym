package apzpzpi215myronovmaksymtask2.secondhandsync.model.entity;

import lombok.Data;
import jakarta.persistence.*;

@Data
@Entity
@Table(name = "items")
public class Item {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;
    private String type;
    private String color;
    private String brand;
    private String image;
    private Boolean isSold;

    @ManyToOne
    @JoinColumn(name = "package_id")
    private Package aPackage;
}