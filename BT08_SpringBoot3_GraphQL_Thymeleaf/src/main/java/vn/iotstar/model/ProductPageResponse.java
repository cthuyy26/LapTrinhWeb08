package vn.iotstar.model;

import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import vn.iotstar.entity.Product;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ProductPageResponse {
    private List<Product> content;
    private int totalPages;
    private long totalElements;
    private int currentPage;
    private int pageSize;
}
