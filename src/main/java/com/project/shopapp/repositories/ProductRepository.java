package com.project.shopapp.repositories;

import com.project.shopapp.models.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.domain.*;


public interface ProductRepository extends JpaRepository<Product, Long> {
    //Hàm kiểm tra xem sản phẩm với name đó có tồn tại không?
    boolean existsByName(String name);

    //Hàm phân trang
    Page<Product> findAll(Pageable pageable);
}
