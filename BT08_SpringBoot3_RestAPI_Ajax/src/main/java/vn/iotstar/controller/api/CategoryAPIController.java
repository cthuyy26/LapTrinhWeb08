package vn.iotstar.controller.api;

import java.util.Optional;
import java.util.UUID;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import vn.iotstar.entity.Category;
import vn.iotstar.model.Response;
import vn.iotstar.service.ICategoryService;
import vn.iotstar.service.IStorageService;

@Tag(name = "Category Controller", description = "REST APIs CRUD cho danh mục (Category)")
@RestController
@RequestMapping(path = "/api/category")
public class CategoryAPIController {

    @Autowired
    private ICategoryService categoryService;

    @Autowired
    private IStorageService storageService;

    @Operation(summary = "Lấy tất cả danh mục")
    @GetMapping
    public ResponseEntity<?> getAllCategory() {
        return new ResponseEntity<>(new Response(true, "Thành công", categoryService.findAll()), HttpStatus.OK);
    }

    @Operation(summary = "Lấy chi tiết danh mục theo ID (POST)")
    @PostMapping(path = "/getCategory")
    public ResponseEntity<?> getCategory(@Validated @RequestParam("id") Long id) {
        Optional<Category> category = categoryService.findById(id);
        if (category.isPresent()) {
            return new ResponseEntity<>(new Response(true, "Thành công", category.get()), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(new Response(false, "Không tìm thấy Category", null), HttpStatus.NOT_FOUND);
        }
    }

    @Operation(summary = "Lấy chi tiết danh mục theo ID (GET)")
    @GetMapping(path = "/{id}")
    public ResponseEntity<?> getCategoryById(@PathVariable("id") Long id) {
        Optional<Category> category = categoryService.findById(id);
        if (category.isPresent()) {
            return new ResponseEntity<>(new Response(true, "Thành công", category.get()), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(new Response(false, "Không tìm thấy Category", null), HttpStatus.NOT_FOUND);
        }
    }

    @Operation(summary = "Thêm danh mục mới có tải lên icon")
    @PostMapping(path = "/addCategory", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<?> addCategory(
            @Validated @RequestParam("categoryName") String categoryName,
            @RequestParam(value = "icon", required = false) MultipartFile icon) {

        Optional<Category> optCategory = categoryService.findByCategoryName(categoryName);
        if (optCategory.isPresent()) {
            return new ResponseEntity<>(new Response(false, "Category đã tồn tại trong hệ thống", optCategory.get()),
                    HttpStatus.BAD_REQUEST);
        }

        Category category = new Category();
        category.setCategoryName(categoryName);

        if (icon != null && !icon.isEmpty()) {
            UUID uuid = UUID.randomUUID();
            String storeName = storageService.getStorageFilename(icon, uuid.toString());
            storageService.store(icon, storeName);
            category.setIcon(storeName);
        }

        Category saved = categoryService.save(category);
        return new ResponseEntity<>(new Response(true, "Thêm Category thành công", saved), HttpStatus.OK);
    }

    @Operation(summary = "Cập nhật danh mục kèm icon")
    @PutMapping(path = "/updateCategory", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<?> updateCategory(
            @Validated @RequestParam("categoryId") Long categoryId,
            @Validated @RequestParam("categoryName") String categoryName,
            @RequestParam(value = "icon", required = false) MultipartFile icon) {

        Optional<Category> optCategory = categoryService.findById(categoryId);
        if (optCategory.isEmpty()) {
            return new ResponseEntity<>(new Response(false, "Không tìm thấy Category", null), HttpStatus.NOT_FOUND);
        }

        Category existing = optCategory.get();
        existing.setCategoryName(categoryName);

        if (icon != null && !icon.isEmpty()) {
            UUID uuid = UUID.randomUUID();
            String storeName = storageService.getStorageFilename(icon, uuid.toString());
            storageService.store(icon, storeName);
            existing.setIcon(storeName);
        }

        Category updated = categoryService.save(existing);
        return new ResponseEntity<>(new Response(true, "Cập nhật Thành công", updated), HttpStatus.OK);
    }

    @Operation(summary = "Xóa danh mục theo ID")
    @DeleteMapping(path = "/deleteCategory")
    public ResponseEntity<?> deleteCategory(@Validated @RequestParam("categoryId") Long categoryId) {
        Optional<Category> optCategory = categoryService.findById(categoryId);
        if (optCategory.isEmpty()) {
            return new ResponseEntity<>(new Response(false, "Không tìm thấy Category", null), HttpStatus.NOT_FOUND);
        }

        try {
            if (optCategory.get().getIcon() != null) {
                storageService.delete(optCategory.get().getIcon());
            }
        } catch (Exception ignored) {
        }

        categoryService.delete(optCategory.get());
        return new ResponseEntity<>(new Response(true, "Xóa Thành công", optCategory.get()), HttpStatus.OK);
    }
}
