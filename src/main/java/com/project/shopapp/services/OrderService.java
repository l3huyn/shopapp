package com.project.shopapp.services;

import com.project.shopapp.dtos.OrderDTO;
import com.project.shopapp.exceptions.DataNotFoundException;
import com.project.shopapp.models.Order;
import com.project.shopapp.models.OrderStatus;
import com.project.shopapp.models.User;
import com.project.shopapp.repositories.OrderRepository;
import com.project.shopapp.repositories.UserRepository;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service //Annotation để đánh dấu một class là một Spring Service.
// Spring Service là một thành phần của ứng dụng Spring Framework được thiết kế để chứa logic kinh doanh của ứng dụng.


/* Cơ chế Dependency Injection */
@RequiredArgsConstructor //Annotation này tự động tạo một constructor có tham số cho tất cả các trường (fields) final hoặc @NonNull trong một lớp.

public class OrderService implements IOrderService{

    /* Dependency Injection */
    private final OrderRepository orderRepository;

    private final UserRepository userRepository;

    //Injection thư viện ModelMapper
    private final ModelMapper modelMapper;


    //Hàm tạo đơn hàng
    @Override
    public Order createOrder(OrderDTO orderDTO) throws Exception {
        //Tìm xem userId có tồn tại hay không
        //Nếu không có -> Trả về Exception
        User user = userRepository
                .findById(orderDTO.getUserId())
                .orElseThrow(() -> new DataNotFoundException("Cannot find user with id: " + orderDTO.getUserId()));

        //Convert OrderDTO -> Order
        // => Dùng thư viện Model Mapper

        /* Tạo một luồng bảng ánh xạ riêng để kiểm soát việc ánh xạ */
        //-- Có một đối tượng modelMapper, nó sẽ ánh xạ từ OrderDTO sang Order nhưng bỏ qua trường ID
        //-- OrderDTO không có trường ID nhưng Order thì có
        modelMapper.typeMap(OrderDTO.class, Order.class)
                //--Bỏ qua trường ID
                .addMappings(mapper -> mapper.skip(Order::setId));

        //Cập nhật các trường của đơn hàng từ orderDTO
        //Tạo một đối tượng order rỗng, sau đó hàm map() sẽ tự động cập nhật
        Order order = new Order();
        modelMapper.map(orderDTO, order);

        order.setUser(user);

        order.setOrderDate(new Date()); //Lấy thời điểm hiện tại

        order.setStatus(OrderStatus.PENDING);

        //Kiểm tra shipping date phải >= ngày hôm nay
        LocalDate shippingDate = orderDTO.getShippingDate() == null ? LocalDate.now() : orderDTO.getShippingDate();
        if(shippingDate.isBefore(LocalDate.now())) {
            throw new DataNotFoundException("Date must be at least today!");
        }

        //Set shippingDate
        order.setShippingDate(shippingDate);

        //Vừa tạo đơn hàng nên active = true, nếu active = false -> xóa mềm
        order.setActive(true);

        //Lưu vào DB
        orderRepository.save(order);

        //Trả về order đã thêm vào DB
        return order;
    }

    //Hàm lấy đơn hàng theo order_id
    @Override
    public Order getOrder(Long id) {
        return orderRepository.findById(id).orElse(null);
    }

    @Override
    public Order updateOrder(Long id, OrderDTO orderDTO) throws DataNotFoundException {
        //Tìm kiếm đơn hàng với id được truyền vào -> nếu có tồn tại thì mới cập nhật
        Order order = orderRepository.findById(id)
                .orElseThrow(() -> new DataNotFoundException("Cannot find order with id: " + id));

        //Kiểm tra existingUser, trong đơn hàng có user_id đó không
        User existingUser = userRepository.findById(orderDTO.getUserId())
                .orElseThrow(() -> new DataNotFoundException("Cannot find user with id: " + id));

        //Tạo một luồng bảng ánh xạ riêng để kiểm soát việc ánh xạ
        modelMapper.typeMap(OrderDTO.class, Order.class)
                //--Bỏ qua trường ID
                .addMappings(mapper -> mapper.skip(Order::setId));

        //Cập nhật các trường của đơn hàng từ OrderDTO
        modelMapper.map(orderDTO, order);
        order.setUser(existingUser);

        //Lưu vào DB
        return orderRepository.save(order);
    }

    @Override
    public void deleteOrder(Long id) {
       Order order = orderRepository.findById(id).orElse(null);
       //Không xóa cứng -> Xóa mềm
       if(order != null) {
            order.setActive(false);
            orderRepository.save(order);
       }
    }

    //Hàm lấy danh sách đơn hàng theo user_id
    @Override
    public List<Order> findByUserId(Long userId) {
        return orderRepository.findByUserId(userId);
    }
}
