package com.project.shopapp.repositories;

import com.project.shopapp.models.OrderDetail;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

//Khai báo giao diện OrderDetailRepository, extend từ giao diện JpaRepository.
// Nó chỉ định hai tham số generic: OrderDetail và Long.
//Tham số OrderDetail cho biết loại thực thể được quản lý bởi repository này (trong trường hợp này là lớp OrderDetail),
// và Long là loại khóa chính của thực thể.
public interface OrderDetailRepository extends JpaRepository<OrderDetail, Long> {
    //Hàm tìm kiếm chi tiết đơn hàng theo orderId
    // => Trả về danh sách các OrderDetail
    List<OrderDetail> findByOrderId(Long orderId);
}
