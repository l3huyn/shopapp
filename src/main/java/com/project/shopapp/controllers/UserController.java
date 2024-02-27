package com.project.shopapp.controllers;

import com.project.shopapp.dtos.UserDTO;
import com.project.shopapp.dtos.UserLoginDTO;
import com.project.shopapp.repositories.UserRepository;
import com.project.shopapp.services.IUserService;
import com.project.shopapp.services.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("${api.prefix}/users") // http://localhost:8088/api/v1/users

//Dependency Injection
@RequiredArgsConstructor //Annotation này tự động tạo một constructor có tham số cho tất cả các trường (fields) final hoặc @NonNull trong một lớp.

public class UserController {

    //Trong controller thì mới có final
    private final IUserService userService;

    //HÀM ĐĂNG KÝ
    @PostMapping("/register") // http://localhost:8088/api/v1/users/register
    public ResponseEntity<?> createUser(@Valid @RequestBody UserDTO userDTO, BindingResult result) {
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
            if(!userDTO.getPassword().equals(userDTO.getRetypePassword())) {
                return ResponseEntity.badRequest().body("Password does not match");
            }

            //Tạo user mới
            userService.createUser(userDTO);

            return ResponseEntity.ok("Register successfully");
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    //HÀM ĐĂNG NHẬP
    @PostMapping("/login") // http://localhost:8088/api/v1/users/login
    public ResponseEntity<String> login(@Valid @RequestBody UserLoginDTO userLoginDTO) {
        //Kiểm tra thông tin đăng nhập và sinh token
        String token = userService.login(userLoginDTO.getPhoneNumber(), userLoginDTO.getPassword());
        //Trả về token trong response
        return ResponseEntity.ok(token);
    }
}
