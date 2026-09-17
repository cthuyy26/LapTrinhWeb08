package vn.iotstar.controller;

import java.util.Date;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.MutationMapping;
import org.springframework.graphql.data.method.annotation.QueryMapping;
import org.springframework.stereotype.Controller;
import vn.iotstar.entity.Category;
import vn.iotstar.entity.Product;
import vn.iotstar.model.CategoryInput;
import vn.iotstar.model.CategoryPageResponse;
import vn.iotstar.model.ProductInput;
import vn.iotstar.model.ProductPageResponse;
import vn.iotstar.repository.CategoryRepository;
import vn.iotstar.repository.ProductRepository;

@Controller
public class GraphQLProductController {

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private CategoryRepository categoryRepository;

    // 1. Hiển thị tất cả product có price từ thấp đến cao (mặc định ASC)
    @QueryMapping
    public List<Product> allProductsSortedByPrice(@Argument String order) {
        Sort sort = "DESC".equalsIgnoreCase(order) 
                ? Sort.by("unitPrice").descending() 
                : Sort.by("unitPrice").ascending();
        return productRepository.findAll(sort);
    }

    // 2. Lấy tất cả product của 01 category
    @QueryMapping
    public List<Product> productsByCategory(@Argument Long categoryId) {
        return productRepository.findByCategory_CategoryId(categoryId);
    }

    // 3. Phân trang & tìm kiếm Product
    @QueryMapping
    public ProductPageResponse productsPage(@Argument String name, @Argument Integer page, @Argument Integer size) {
        int pageNum = (page != null && page >= 0) ? page : 0;
        int pageSize = (size != null && size > 0) ? size : 6;
        Pageable pageable = PageRequest.of(pageNum, pageSize, Sort.by("productId").descending());

        Page<Product> res;
        if (name != null && !name.trim().isEmpty()) {
            res = productRepository.findByProductNameContaining(name.trim(), pageable);
        } else {
            res = productRepository.findAll(pageable);
        }

        return new ProductPageResponse(res.getContent(), res.getTotalPages(), res.getTotalElements(), res.getNumber(), res.getSize());
    }

    // 4. Phân trang & tìm kiếm Category
    @QueryMapping
    public CategoryPageResponse categoriesPage(@Argument String name, @Argument Integer page, @Argument Integer size) {
        int pageNum = (page != null && page >= 0) ? page : 0;
        int pageSize = (size != null && size > 0) ? size : 5;
        Pageable pageable = PageRequest.of(pageNum, pageSize, Sort.by("categoryId").descending());

        Page<Category> res;
        if (name != null && !name.trim().isEmpty()) {
            res = categoryRepository.findByCategoryNameContaining(name.trim(), pageable);
        } else {
            res = categoryRepository.findAll(pageable);
        }

        return new CategoryPageResponse(res.getContent(), res.getTotalPages(), res.getTotalElements(), res.getNumber(), res.getSize());
    }

    // Query bổ trợ
    @QueryMapping
    public List<Category> allCategories() {
        return categoryRepository.findAll();
    }

    @QueryMapping
    public List<Product> allProducts() {
        return productRepository.findAll();
    }

    @QueryMapping
    public Product productById(@Argument Long id) {
        return productRepository.findById(id).orElse(null);
    }

    @QueryMapping
    public Category categoryById(@Argument Long id) {
        return categoryRepository.findById(id).orElse(null);
    }

    // ================= MUTATIONS =================

    // CRUD Category
    @MutationMapping
    public Category createCategory(@Argument CategoryInput input) {
        Category category = new Category();
        category.setCategoryName(input.getCategoryName());
        category.setIcon(input.getIcon());
        return categoryRepository.save(category);
    }

    @MutationMapping
    public Category updateCategory(@Argument Long id, @Argument CategoryInput input) {
        Category category = categoryRepository.findById(id).orElseThrow(() -> new RuntimeException("Category not found with ID: " + id));
        category.setCategoryName(input.getCategoryName());
        if (input.getIcon() != null && !input.getIcon().isEmpty()) {
            category.setIcon(input.getIcon());
        }
        return categoryRepository.save(category);
    }

    @MutationMapping
    public Boolean deleteCategory(@Argument Long id) {
        if (categoryRepository.existsById(id)) {
            categoryRepository.deleteById(id);
            return true;
        }
        return false;
    }

    // CRUD Product
    @MutationMapping
    public Product createProduct(@Argument ProductInput input) {
        Category category = categoryRepository.findById(input.getCategoryId())
                .orElseThrow(() -> new RuntimeException("Category not found with ID: " + input.getCategoryId()));

        Product product = new Product();
        product.setProductName(input.getProductName());
        product.setQuantity(input.getQuantity() != null ? input.getQuantity() : 0);
        product.setUnitPrice(input.getUnitPrice() != null ? input.getUnitPrice() : 0.0);
        product.setDiscount(input.getDiscount() != null ? input.getDiscount() : 0.0);
        product.setImages(input.getImages());
        product.setDescription(input.getDescription());
        product.setStatus(input.getStatus() != null ? input.getStatus().shortValue() : (short) 1);
        product.setCreateDate(new Date());
        product.setCategory(category);

        return productRepository.save(product);
    }

    @MutationMapping
    public Product updateProduct(@Argument Long id, @Argument ProductInput input) {
        Product product = productRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Product not found with ID: " + id));

        Category category = categoryRepository.findById(input.getCategoryId())
                .orElseThrow(() -> new RuntimeException("Category not found with ID: " + input.getCategoryId()));

        product.setProductName(input.getProductName());
        product.setQuantity(input.getQuantity() != null ? input.getQuantity() : product.getQuantity());
        product.setUnitPrice(input.getUnitPrice() != null ? input.getUnitPrice() : product.getUnitPrice());
        product.setDiscount(input.getDiscount() != null ? input.getDiscount() : product.getDiscount());
        if (input.getImages() != null && !input.getImages().isEmpty()) {
            product.setImages(input.getImages());
        }
        product.setDescription(input.getDescription());
        if (input.getStatus() != null) {
            product.setStatus(input.getStatus().shortValue());
        }
        product.setCategory(category);

        return productRepository.save(product);
    }

    @MutationMapping
    public Boolean deleteProduct(@Argument Long id) {
        if (productRepository.existsById(id)) {
            productRepository.deleteById(id);
            return true;
        }
        return false;
    }
}
