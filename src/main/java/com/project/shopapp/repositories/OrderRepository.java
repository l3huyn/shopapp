package com.project.shopapp.repositories;

import com.project.shopapp.models.Order;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface OrderRepository extends JpaRepository<Order, Long> {
    //Tìm đơn hàng của một user nào đó
    //Hàm tìm kiếm đơn hàng theo userId
    // => hàm sẽ trả về danh sách các đơn hàng theo userId
    List<Order> findByUserId(Long userId);


}
