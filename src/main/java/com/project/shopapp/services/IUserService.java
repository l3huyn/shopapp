package com.project.shopapp.services;

import com.project.shopapp.dtos.UserDTO;
import com.project.shopapp.exceptions.DataNotFoundException;
import com.project.shopapp.models.User;

public interface IUserService {
    //Hàm đăng ký (Tạo một User ) => Giá trị trả về là một user sau khi tạo
    User createUser(UserDTO userDTO) throws DataNotFoundException;

    //Hàm đăng nhập => Giá trị trả về là một token key (Nên là kiểu String)
    String login(String phoneNumber, String password);
}
