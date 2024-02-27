package com.project.shopapp.controllers;

import com.project.shopapp.dtos.*;
import com.project.shopapp.exceptions.DataNotFoundException;
import com.project.shopapp.models.OrderDetail;
import com.project.shopapp.responses.OrderDetailResponse;
import com.project.shopapp.services.IOrderDetailService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;

import java.util.List;
@RestController
@RequestMapping("${api.prefix}/order_details") // http://localhost:8088/api/v1/order_details

//Dependency Injection
@RequiredArgsConstructor

public class OrderDetailController {

    //Dependency Injection
    private final IOrderDetailService orderDetailService;

    //Hàm thêm mới một order detail
    @PostMapping()
    public ResponseEntity<?> createOrderDetail(@Valid @RequestBody OrderDetailDTO orderDetailDTO) {
        try {
            OrderDetail newOrderDetail = orderDetailService.createOrderDetail(orderDetailDTO);
            return ResponseEntity.ok().body(OrderDetailResponse.fromOrderDetail(newOrderDetail));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    //Hàm lấy một order detail bằng id
    @GetMapping("/{id}")
    public ResponseEntity<?> getOrderDetail(@Valid @PathVariable("id") Long id) throws DataNotFoundException {
        OrderDetail orderDetail =  orderDetailService.getOrderDetail(id);
//        return ResponseEntity.ok(orderDetail);
        return ResponseEntity.ok(OrderDetailResponse.fromOrderDetail(orderDetail));
    }


    //Hàm lấy danh sách các order_details của một order nào đó
    @GetMapping("/order/{orderId}")
    public ResponseEntity<?> getOrderDetails(@Valid @PathVariable("orderId") Long orderId) {
        //Lấy ra danh sách OrderDetail bằng id
        List<OrderDetail> orderDetails = orderDetailService.findByOrderId(orderId);

        //Convert danh sách OrderDetail sang danh sách OrderDetailResponse
        List<OrderDetailResponse> orderDetailResponses = orderDetails
                .stream().map(OrderDetailResponse::fromOrderDetail).toList();

        //Trả về danh sách OrderDetailResponse
        return ResponseEntity.ok(orderDetailResponses);
    }


    //Hàm sửa đổi một order_details nào đó
    @PutMapping("/{id}")
    public ResponseEntity<?> updateOrderDetail(
            @Valid @PathVariable("id") Long id,
            @RequestBody OrderDetailDTO orderDetailDTO
    ) {
        try {
            OrderDetail orderDetail = orderDetailService.updateOrderDetail(id, orderDetailDTO);
            return ResponseEntity.ok(orderDetail);
        } catch (DataNotFoundException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }


    //Hàm xóa một order_detail
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteOrderDetail(@Valid @PathVariable("id") Long id) {
        orderDetailService.deleteById(id);

        return ResponseEntity.ok().body("Deleted order detail with id: " + id + "successfully");
    }

}
