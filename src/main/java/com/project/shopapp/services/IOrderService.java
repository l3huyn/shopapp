package com.project.shopapp.services;

import com.project.shopapp.dtos.OrderDTO;
import com.project.shopapp.exceptions.DataNotFoundException;
import com.project.shopapp.models.Order;

import java.util.List;

public interface IOrderService {
    //Hàm tạo đơn hàng
    Order createOrder(OrderDTO orderDTO) throws Exception;

    //Hàm lấy đơn hàng theo id
    Order getOrder(Long id);

    //Hàm cập nhật đơn hàng theo ID
    Order updateOrder(Long id, OrderDTO orderDTO) throws DataNotFoundException;

    //Hàm xóa đơn hàng
    void deleteOrder(Long id);

    //Hàm lấy danh sách đơn hàng theo ID người dùng (user_id)
    List<Order> findByUserId(Long userId);
}
