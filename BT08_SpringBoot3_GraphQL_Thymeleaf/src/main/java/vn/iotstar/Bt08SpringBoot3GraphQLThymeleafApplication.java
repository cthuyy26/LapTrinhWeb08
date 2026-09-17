package vn.iotstar;

import java.util.Date;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import vn.iotstar.entity.Category;
import vn.iotstar.entity.Product;
import vn.iotstar.repository.CategoryRepository;
import vn.iotstar.repository.ProductRepository;

@SpringBootApplication
public class Bt08SpringBoot3GraphQLThymeleafApplication {

    public static void main(String[] args) {
        SpringApplication.run(Bt08SpringBoot3GraphQLThymeleafApplication.class, args);
    }

    @Bean
    CommandLineRunner initData(CategoryRepository categoryRepository, ProductRepository productRepository) {
        return args -> {
            if (categoryRepository.count() == 0) {
                Category c1 = categoryRepository.save(new Category(null, "Điện thoại & Tablet", "https://images.unsplash.com/photo-1511707171634-5f897ff02aa9?w=300", null));
                Category c2 = categoryRepository.save(new Category(null, "Laptop & PC", "https://images.unsplash.com/photo-1496181133206-80ce9b88a853?w=300", null));
                Category c3 = categoryRepository.save(new Category(null, "Phụ kiện công nghệ", "https://images.unsplash.com/photo-1505740420928-5e560c06d30e?w=300", null));

                productRepository.save(new Product(null, "iPhone 15 Pro Max 256GB", 15, 29990000.0, "https://images.unsplash.com/photo-1695048133142-1a20484d2569?w=400", "Khung Titan tự nhiên siêu bền, chip A17 Pro", 1000000.0, new Date(), (short) 1, c1));
                productRepository.save(new Product(null, "Samsung Galaxy S24 Ultra", 20, 27500000.0, "https://images.unsplash.com/photo-1610945265064-0e34e5519bbf?w=400", "Quyền năng Galaxy AI, camera 200MP", 1500000.0, new Date(), (short) 1, c1));
                productRepository.save(new Product(null, "MacBook Air M2 13 inch", 10, 24990000.0, "https://images.unsplash.com/photo-1517336714731-489689fd1ca8?w=400", "Thời lượng pin 18h, chip Apple M2 vượt trội", 500000.0, new Date(), (short) 1, c2));
                productRepository.save(new Product(null, "Laptop Asus Zenbook 14 OLED", 12, 21990000.0, "https://images.unsplash.com/photo-1588872657578-7efd1f1555ed?w=400", "Màn hình OLED 2.8K 120Hz mỏng nhẹ thời trang", 800000.0, new Date(), (short) 1, c2));
                productRepository.save(new Product(null, "Tai nghe AirPods Pro 2 USB-C", 30, 5490000.0, "https://images.unsplash.com/photo-1600294037681-c80b4cb5b434?w=400", "Chống ồn chủ động gấp 2 lần, cổng sạc Type-C", 200000.0, new Date(), (short) 1, c3));
                productRepository.save(new Product(null, "Chuột không dây Logitech MX Master 3S", 25, 2190000.0, "https://images.unsplash.com/photo-1615663245857-ac93bb7c39e7?w=400", "Cuộn siêu tốc MagSpeed, êm ái hơn 90%", 100000.0, new Date(), (short) 1, c3));
            }
        };
    }
}
