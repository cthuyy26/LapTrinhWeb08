package vn.iotstar.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ProductInput {
    private String productName;
    private Integer quantity;
    private Double unitPrice;
    private Double discount;
    private String images;
    private String description;
    private Integer status;
    private Long categoryId;
}
