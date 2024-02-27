package com.project.shopapp.controllers;

import com.project.shopapp.dtos.*;
import com.project.shopapp.models.Order;
import com.project.shopapp.services.IOrderService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController //Annotation đánh dấu một lớp Java như là một RESTful Controller.
@RequestMapping("${api.prefix}/orders") // http://localhost:8088/api/v1/orders
@RequiredArgsConstructor //Dependency Injection
public class OrderController {

    //Dependency Injection
    private final IOrderService orderService;

    //HÀM TẠO ĐƠN HÀNG
    @PostMapping("")
    public ResponseEntity<?> createOrder(@Valid @RequestBody OrderDTO orderDTO, BindingResult result) {
        try {
            //Hiển thị lỗi, lỗi được lưu ở biến result
            if(result.hasErrors()) {
                //Trả về mảng errorMessage kiểu String
                List<String> errorMessage =  result.getFieldErrors()
                        .stream()
                        .map(FieldError::getDefaultMessage)
                        .toList();
                return ResponseEntity.badRequest().body(errorMessage);
            }

            //Tạo đơn hàng và trả về OrderResponse để hiển thị cho người dùng
            Order orderResponse = orderService.createOrder(orderDTO);

            return ResponseEntity.ok(orderResponse);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    //Hàm lấy ra danh sách Order từ một user_id nào đó
    @GetMapping("/user/{user_id}")// GET - http://localhost:8088/api/v1/orders/user/1
    public ResponseEntity<?> getOrders(@Valid @PathVariable("user_id") Long userId) {
        try {
            List<Order> orders = orderService.findByUserId(userId);
            return ResponseEntity.ok(orders);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }


    //Hàm lấy ra chi tiết Order từ một order_id
    @GetMapping("/{id}")
    // GET - http://localhost:8088/api/v1/orders/4
    public ResponseEntity<?> getOrder(@Valid @PathVariable("id") Long orderId) {
        try {
            Order existingOrder = orderService.getOrder(orderId);
            return ResponseEntity.ok(existingOrder);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }


    //Hàm cập nhật thông tin đơn hàng cụ thể
    @PutMapping("/{id}")// PUT - http://localhost:8088/api/v1/orders/1
    public ResponseEntity<?> updateOrder(
            @Valid @RequestBody OrderDTO orderDTO,
            @Valid @PathVariable long id
    ) {
        try {
            Order order = orderService.updateOrder(id, orderDTO);
            return ResponseEntity.ok(order);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }


    //Hàm xóa đơn hàng
    //Công việc của quản trị viên
    @DeleteMapping("/{id}")// Delete - http://localhost:8088/api/v1/orders/1
    public ResponseEntity<String> deleteOrder(@Valid @PathVariable Long id) {
        //Xóa mềm => Cập nhật trường active = false
        orderService.deleteOrder(id);
        return ResponseEntity.ok("Order deleted successfully.");
    }
}
