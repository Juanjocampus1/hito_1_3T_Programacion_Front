package DTO;

import java.math.BigDecimal;

import lombok.*;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
public class DataDTO {
    private Long id;
    private String name;
    private String description;
    private int stock;
    private BigDecimal price;
    private String category;
    private String provider;
    private String fechaCreacion;
    private String fechaActualizacion;
}