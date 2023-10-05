package guru.springframework.spring6resttemplate.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

/**
 * Created by jt, Spring Framework Guru.
 */
@Builder
@Data
public class BeerDTO {
    private UUID id;
    private Integer version;
    @JsonProperty(value = "name")
    private String beerName;
    @JsonProperty(value = "type")
    private BeerStyle beerStyle;
    private String upc;
    private Integer quantityOnHand;
    private BigDecimal price;
    @JsonProperty(value = "createdAt")
    private LocalDateTime createdDate;
    @JsonProperty(value = "updatedAt")
    private LocalDateTime updateDate;
}
