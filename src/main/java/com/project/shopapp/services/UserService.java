package com.project.shopapp.services;

import com.project.shopapp.dtos.UserDTO;
import com.project.shopapp.exceptions.DataNotFoundException;
import com.project.shopapp.models.Role;
import com.project.shopapp.models.User;
import com.project.shopapp.repositories.RoleRepository;
import com.project.shopapp.repositories.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

@Service //Annotation để đánh dấu một class là một Spring Service.
// Spring Service là một thành phần của ứng dụng Spring Framework được thiết kế để chứa logic kinh doanh của ứng dụng.

@RequiredArgsConstructor //Annotation này tự động tạo một constructor có tham số cho tất cả các trường (fields) final hoặc @NonNull trong một lớp.
public class UserService implements IUserService{

    //Dependency Injection giúp Service tương tác với Repository và Controller
    //Trong service không cần final
    private UserRepository userRepository;
    private RoleRepository roleRepository;


    @Override
    public User createUser(UserDTO userDTO) throws DataNotFoundException {
        //Lấy ra được phoneNumber từ userDTO
        String phoneNumber = userDTO.getPhoneNumber();

        //Kiểm tra xem số điện thoại đã tồn tại hay chưa
        if(userRepository.existsByPhoneNumber(phoneNumber)) {
            throw new DataIntegrityViolationException("Phone number already exists");
        }

        //Convert từ UserDTO sang User
        User newUser = User.builder()
                .fullName(userDTO.getFullName())
                .phoneNumber(userDTO.getPhoneNumber())
                .password(userDTO.getPassword())
                .address(userDTO.getAddress())
                .dateOfBirth(userDTO.getDateOfBirth())
                .facebookAccountId(userDTO.getFacebookAccountId())
                .googleAccountId(userDTO.getGoogleAccountId())
                .build();

        Role role = roleRepository.findById(userDTO.getRoleId())
                .orElseThrow(() -> new DataNotFoundException("Role not found"));

        newUser.setRole(role);

        //Kiểm tra nếu có accountId => không yêu cầu mật khẩu
        if(userDTO.getFacebookAccountId() == 0 && userDTO.getGoogleAccountId() == 0) {
            String password = userDTO.getPassword();
//            String encodedPassword = passwordEncoder.encode(password);
//            newUser.setPassword(encodedPassword);
        }

        //Lưu user mới vào trong DB
        return userRepository.save(newUser);
    }


    @Override
    public String login(String phoneNumber, String password) {
        //Đoạn này liên quan nhiều đến Spring Security => Làm sau
        return null;
    }
}
