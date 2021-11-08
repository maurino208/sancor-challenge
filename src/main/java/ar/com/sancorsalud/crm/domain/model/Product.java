package ar.com.sancorsalud.crm.domain.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Product {
    @Id

    @GeneratedValue(
            strategy = GenerationType.SEQUENCE
    )
    @Column(unique = true, nullable = false)
    private Long id;
    private String name;
    private String category;
    @Column(name = "retail_price")
    @JsonProperty("retail_price")
    private Double retailPrice;
    @Column(name = "discounted_price")
    @JsonProperty("discounted_price")
    private Double discountedPrice;
    private Boolean availability;
}
