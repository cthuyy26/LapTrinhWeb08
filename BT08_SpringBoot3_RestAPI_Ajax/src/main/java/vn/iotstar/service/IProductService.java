package vn.iotstar.service;

import java.util.Date;
import java.util.List;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import vn.iotstar.entity.Product;

public interface IProductService {

    List<Product> findAll();

    List<Product> findAll(Sort sort);

    Page<Product> findAll(Pageable pageable);

    Optional<Product> findById(Long id);

    Optional<Product> findByProductName(String name);

    Optional<Product> findByCreateDate(Date createAt);

    Product save(Product entity);

    void deleteById(Long id);

    void delete(Product entity);

    long count();

    List<Product> findByProductNameContaining(String name);

    Page<Product> findByProductNameContaining(String name, Pageable pageable);

    List<Product> findByCategoryCategoryId(Long categoryId);

    Page<Product> findByCategoryCategoryId(Long categoryId, Pageable pageable);
}
