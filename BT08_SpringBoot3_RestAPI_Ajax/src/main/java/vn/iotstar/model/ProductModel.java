package vn.iotstar.model;

import java.util.Date;
import org.springframework.web.multipart.MultipartFile;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ProductModel {
    private Long productId;
    private String productName;
    private int quantity;
    private double unitPrice;
    private double discount;
    private String description;
    private short status = 1;
    private Date createDate;
    private String images;
    private MultipartFile imageFile;
    private Long categoryId;
}
