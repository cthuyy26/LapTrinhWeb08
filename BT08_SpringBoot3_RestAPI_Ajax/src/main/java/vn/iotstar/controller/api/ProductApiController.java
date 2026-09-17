package vn.iotstar.controller.api;

import java.util.Date;
import java.util.Optional;
import java.util.UUID;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import vn.iotstar.entity.Category;
import vn.iotstar.entity.Product;
import vn.iotstar.model.Response;
import vn.iotstar.service.ICategoryService;
import vn.iotstar.service.IProductService;
import vn.iotstar.service.IStorageService;

@Tag(name = "Product Controller", description = "REST APIs CRUD cho sản phẩm (Product)")
@RestController
@RequestMapping(path = "/api/product")
public class ProductApiController {

    @Autowired
    private IProductService productService;

    @Autowired
    private ICategoryService categoryService;

    @Autowired
    private IStorageService storageService;

    @Operation(summary = "Lấy tất cả sản phẩm")
    @GetMapping
    public ResponseEntity<?> getAllProduct() {
        return new ResponseEntity<>(new Response(true, "Thành công", productService.findAll()), HttpStatus.OK);
    }

    @Operation(summary = "Lấy chi tiết sản phẩm theo ID")
    @GetMapping(path = "/{id}")
    public ResponseEntity<?> getProductById(@PathVariable("id") Long id) {
        Optional<Product> opt = productService.findById(id);
        if (opt.isPresent()) {
            return new ResponseEntity<>(new Response(true, "Thành công", opt.get()), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(new Response(false, "Không tìm thấy sản phẩm", null), HttpStatus.NOT_FOUND);
        }
    }

    @Operation(summary = "Lấy sản phẩm theo danh mục")
    @GetMapping(path = "/category/{categoryId}")
    public ResponseEntity<?> getProductsByCategory(@PathVariable("categoryId") Long categoryId) {
        return new ResponseEntity<>(new Response(true, "Thành công", productService.findByCategoryCategoryId(categoryId)), HttpStatus.OK);
    }

    @Operation(summary = "Tìm kiếm sản phẩm có phân trang")
    @GetMapping(path = "/search")
    public ResponseEntity<?> searchProducts(
            @RequestParam(value = "name", defaultValue = "") String name,
            @RequestParam(value = "page", defaultValue = "0") int page,
            @RequestParam(value = "size", defaultValue = "5") int size,
            @RequestParam(value = "sortBy", defaultValue = "productId") String sortBy,
            @RequestParam(value = "direction", defaultValue = "DESC") String direction) {

        Sort sort = direction.equalsIgnoreCase("ASC") ? Sort.by(sortBy).ascending() : Sort.by(sortBy).descending();
        Pageable pageable = PageRequest.of(page, size, sort);
        Page<Product> productPage = productService.findByProductNameContaining(name, pageable);

        return new ResponseEntity<>(new Response(true, "Thành công", productPage), HttpStatus.OK);
    }

    @Operation(summary = "Thêm sản phẩm mới kèm ảnh")
    @PostMapping(path = "/addProduct", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<?> addProduct(
            @Validated @RequestParam("productName") String productName,
            @Validated @RequestParam("unitPrice") Double unitPrice,
            @RequestParam(value = "discount", defaultValue = "0.0") Double discount,
            @RequestParam(value = "quantity", defaultValue = "0") Integer quantity,
            @RequestParam(value = "description", defaultValue = "") String description,
            @RequestParam(value = "status", defaultValue = "1") Short status,
            @Validated @RequestParam("categoryId") Long categoryId,
            @RequestParam(value = "imageFile", required = false) MultipartFile imageFile) {

        Optional<Category> optCategory = categoryService.findById(categoryId);
        if (optCategory.isEmpty()) {
            return new ResponseEntity<>(new Response(false, "Category không tồn tại", null), HttpStatus.BAD_REQUEST);
        }

        Product product = new Product();
        product.setProductName(productName);
        product.setUnitPrice(unitPrice);
        product.setDiscount(discount);
        product.setQuantity(quantity);
        product.setDescription(description);
        product.setStatus(status);
        product.setCategory(optCategory.get());
        product.setCreateDate(new Date());

        if (imageFile != null && !imageFile.isEmpty()) {
            UUID uuid = UUID.randomUUID();
            String storeName = storageService.getStorageFilename(imageFile, uuid.toString());
            storageService.store(imageFile, storeName);
            product.setImages(storeName);
        }

        Product saved = productService.save(product);
        return new ResponseEntity<>(new Response(true, "Thêm sản phẩm thành công", saved), HttpStatus.OK);
    }

    @Operation(summary = "Cập nhật sản phẩm")
    @PutMapping(path = "/updateProduct", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<?> updateProduct(
            @Validated @RequestParam("productId") Long productId,
            @Validated @RequestParam("productName") String productName,
            @Validated @RequestParam("unitPrice") Double unitPrice,
            @RequestParam(value = "discount", defaultValue = "0.0") Double discount,
            @RequestParam(value = "quantity", defaultValue = "0") Integer quantity,
            @RequestParam(value = "description", defaultValue = "") String description,
            @RequestParam(value = "status", defaultValue = "1") Short status,
            @Validated @RequestParam("categoryId") Long categoryId,
            @RequestParam(value = "imageFile", required = false) MultipartFile imageFile) {

        Optional<Product> optProduct = productService.findById(productId);
        if (optProduct.isEmpty()) {
            return new ResponseEntity<>(new Response(false, "Không tìm thấy sản phẩm", null), HttpStatus.NOT_FOUND);
        }

        Optional<Category> optCategory = categoryService.findById(categoryId);
        if (optCategory.isEmpty()) {
            return new ResponseEntity<>(new Response(false, "Category không tồn tại", null), HttpStatus.BAD_REQUEST);
        }

        Product product = optProduct.get();
        product.setProductName(productName);
        product.setUnitPrice(unitPrice);
        product.setDiscount(discount);
        product.setQuantity(quantity);
        product.setDescription(description);
        product.setStatus(status);
        product.setCategory(optCategory.get());

        if (imageFile != null && !imageFile.isEmpty()) {
            UUID uuid = UUID.randomUUID();
            String storeName = storageService.getStorageFilename(imageFile, uuid.toString());
            storageService.store(imageFile, storeName);
            product.setImages(storeName);
        }

        Product updated = productService.save(product);
        return new ResponseEntity<>(new Response(true, "Cập nhật sản phẩm thành công", updated), HttpStatus.OK);
    }

    @Operation(summary = "Xóa sản phẩm theo ID")
    @DeleteMapping(path = "/deleteProduct")
    public ResponseEntity<?> deleteProduct(@Validated @RequestParam("productId") Long productId) {
        Optional<Product> optProduct = productService.findById(productId);
        if (optProduct.isEmpty()) {
            return new ResponseEntity<>(new Response(false, "Không tìm thấy sản phẩm", null), HttpStatus.NOT_FOUND);
        }

        try {
            if (optProduct.get().getImages() != null) {
                storageService.delete(optProduct.get().getImages());
            }
        } catch (Exception ignored) {
        }

        productService.delete(optProduct.get());
        return new ResponseEntity<>(new Response(true, "Xóa sản phẩm thành công", optProduct.get()), HttpStatus.OK);
    }
}
