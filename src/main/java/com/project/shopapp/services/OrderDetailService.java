package com.project.shopapp.services;

import com.project.shopapp.dtos.OrderDetailDTO;
import com.project.shopapp.exceptions.DataNotFoundException;
import com.project.shopapp.models.Order;
import com.project.shopapp.models.OrderDetail;
import com.project.shopapp.models.Product;
import com.project.shopapp.repositories.OrderDetailRepository;
import com.project.shopapp.repositories.OrderRepository;
import com.project.shopapp.repositories.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service //Annotation để đánh dấu một class là một Spring Service.
// Spring Service là một thành phần của ứng dụng Spring Framework được thiết kế để chứa logic kinh doanh của ứng dụng.

@RequiredArgsConstructor
//Annotation này tự động tạo một constructor có tham số cho tất cả các trường (fields) final hoặc @NonNull trong một lớp.

public class OrderDetailService implements IOrderDetailService{

    //Dependency Injection
    private final OrderRepository orderRepository;

    private final ProductRepository productRepository;

    private final OrderDetailRepository orderDetailRepository;

    //Hàm tạo chi tiết hóa đơn
    @Override
    public OrderDetail createOrderDetail(OrderDetailDTO orderDetailDTO) throws Exception {
        //Tìm xem orderId có tồn tại hay không?
        Order order = orderRepository.findById(orderDetailDTO.getOrderId())
                .orElseThrow(()
                        -> new DataNotFoundException("Cannot find Order with id: " + orderDetailDTO.getOrderId()));

        //Tìm xem product_id có tồn tại hay không (Bằng cách kiểm tra product_id có thuộc về một product nào không)
        Product product = productRepository.findById(orderDetailDTO.getProductId())
                .orElseThrow(() -> new DataNotFoundException("Cannot find Product with id: " +orderDetailDTO.getProductId()));


        //Convert từ OrderDetailDTO sang Order
        OrderDetail orderDetail = OrderDetail.builder()
                .order(order)
                .product(product)
                .numberOfProducts(orderDetailDTO.getNumberOfProducts())
                .price(orderDetailDTO.getPrice())
                .totalMoney(orderDetailDTO.getTotalMoney())
                .color(orderDetailDTO.getColor())
                .build();

        //Lưu vào DB
        return orderDetailRepository.save(orderDetail);
    }


    //Hàm lấy chi tiết hóa đơn
    @Override
    public OrderDetail getOrderDetail(Long id) throws DataNotFoundException {
        return orderDetailRepository.findById(id)
                .orElseThrow(() -> new DataNotFoundException("Cannot find Order Detail with id: " + id));
    }


    //Hàm cập nhật chi tiết hóa đơn
    @Override
    public OrderDetail updateOrderDetail(Long id, OrderDetailDTO orderDetailDTO) throws DataNotFoundException {
        //Tìm xem Order Detail có tồn tại không (Tìm bằng ID)
        OrderDetail existingOrderDetail = orderDetailRepository.findById(id)
                .orElseThrow(() -> new DataNotFoundException("Cannot find order detail with id: " + id));

        //Kiểm tra order_id có thuộc về order nào đó trong bản Order không
        Order existingOrder = orderRepository.findById(orderDetailDTO.getOrderId())
                .orElseThrow(() -> new DataNotFoundException("Cannot find order with id: " + id));

        //Tìm xem product_id có tồn tại hay không (Bằng cách kiểm tra product_id có thuộc về một product nào không)
        Product existingProduct = productRepository.findById(orderDetailDTO.getProductId())
                .orElseThrow(() ->
                        new DataNotFoundException("Cannot find Product with id: " +orderDetailDTO.getProductId()));

        //Gán giá trị cho Order Detail
        existingOrderDetail.setOrder(existingOrder);
        existingOrderDetail.setProduct(existingProduct);
        existingOrderDetail.setPrice(orderDetailDTO.getPrice());
        existingOrderDetail.setNumberOfProducts(orderDetailDTO.getNumberOfProducts());
        existingOrderDetail.setTotalMoney(orderDetailDTO.getTotalMoney());
        existingOrderDetail.setColor(orderDetailDTO.getColor());

        //Lưu vào DB
        return orderDetailRepository.save(existingOrderDetail);
    }

    //Hàm xóa chi tiết hóa đơn
    @Override
    public void deleteById(Long id) {
        //Xóa cứng
        orderDetailRepository.deleteById(id);
    }

    //Hàm lấy danh sách chi tiết hóa đơn
    @Override
    public List<OrderDetail> findByOrderId(Long orderId) {
        return orderDetailRepository.findByOrderId(orderId);
    }
}
