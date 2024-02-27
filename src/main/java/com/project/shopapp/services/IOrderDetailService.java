package com.project.shopapp.services;

import com.project.shopapp.dtos.OrderDetailDTO;
import com.project.shopapp.exceptions.DataNotFoundException;
import com.project.shopapp.models.OrderDetail;

import java.util.List;

public interface IOrderDetailService {
    //Hàm tạo chi tiết hóa đơn
    OrderDetail createOrderDetail(OrderDetailDTO orderDetailDTO) throws Exception;

    //Hàm lấy chi tiết hóa đơn
    OrderDetail getOrderDetail(Long id) throws DataNotFoundException;

    //Hàm cập nhật chi tiết hóa đơn
    OrderDetail updateOrderDetail(Long id, OrderDetailDTO orderDetailDTO) throws DataNotFoundException;

    //Hàm xóa chi tiết hóa đơn (xóa cứng)
    void deleteById(Long id);

    //Hàm lấy danh sách chi tiết hóa đơn
    List<OrderDetail> findByOrderId(Long orderId);


}
