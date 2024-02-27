package com.project.shopapp.repositories;

import com.project.shopapp.models.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    //Hàm kiểm tra user có phoneNumber này có tồn tại hay không?
    boolean existsByPhoneNumber(String phoneNumber);

    //Hàm tìm kiếm bằng phoneNumber => Trả về 1 user hoặc Null
    Optional<User> findByPhoneNumber(String phoneNumber); // => SELECT * FROM  `users` WHERE `phoneNumber` = ?
}
