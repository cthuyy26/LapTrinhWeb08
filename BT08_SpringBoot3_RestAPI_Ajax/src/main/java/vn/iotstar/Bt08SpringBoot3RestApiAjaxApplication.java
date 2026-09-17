package vn.iotstar;

import java.util.Date;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import vn.iotstar.config.StorageProperties;
import vn.iotstar.entity.Category;
import vn.iotstar.entity.Product;
import vn.iotstar.repository.CategoryRepository;
import vn.iotstar.repository.ProductRepository;
import vn.iotstar.service.IStorageService;

@SpringBootApplication
@EnableConfigurationProperties(StorageProperties.class)
public class Bt08SpringBoot3RestApiAjaxApplication {

    public static void main(String[] args) {
        SpringApplication.run(Bt08SpringBoot3RestApiAjaxApplication.class, args);
    }

    @Bean
    CommandLineRunner init(IStorageService storageService,
                          CategoryRepository categoryRepository,
                          ProductRepository productRepository) {
        return args -> {
            storageService.init();

            // Khởi tạo dữ liệu mẫu nếu chưa có
            if (categoryRepository.count() == 0) {
                Category c1 = categoryRepository.save(new Category(null, "Điện thoại & Tablet", "phone.png", null));
                Category c2 = categoryRepository.save(new Category(null, "Laptop & Thiết bị tin học", "laptop.png", null));
                Category c3 = categoryRepository.save(new Category(null, "Phụ kiện & Âm thanh", "accessories.png", null));

                productRepository.save(new Product(null, "iPhone 15 Pro Max 256GB", 15, 29990000.0, "iphone15.jpg", "Titan tự nhiên cao cấp", 1000000.0, new Date(), (short) 1, c1));
                productRepository.save(new Product(null, "Samsung Galaxy S24 Ultra", 20, 27500000.0, "s24ultra.jpg", "AI Camera đỉnh cao", 1500000.0, new Date(), (short) 1, c1));
                productRepository.save(new Product(null, "MacBook Air M2 13 inch", 10, 24990000.0, "macbookair.jpg", "Chip Apple M2 mạnh mẽ", 500000.0, new Date(), (short) 1, c2));
                productRepository.save(new Product(null, "Laptop Asus Zenbook 14 OLED", 12, 21990000.0, "zenbook.jpg", "Màn hình OLED 2.8K siêu sắc nét", 800000.0, new Date(), (short) 1, c2));
                productRepository.save(new Product(null, "Tai nghe AirPods Pro 2 USB-C", 30, 5490000.0, "airpods.jpg", "Chống ồn chủ động xuất sắc", 200000.0, new Date(), (short) 1, c3));
                productRepository.save(new Product(null, "Chuột không dây Logitech MX Master 3S", 25, 2190000.0, "mxmaster.jpg", "Chuột công thái học cao cấp", 100000.0, new Date(), (short) 1, c3));
            }
        };
    }
}
