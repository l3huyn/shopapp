package com.project.shopapp.controllers;


import com.project.shopapp.dtos.CategoryDTO;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController //Annotation đánh dấu một lớp Java như là một RESTful Controller.
@RequestMapping("api/v1/categories") //Annotation xử lý các yêu cầu HTTP trong một RESTful Controller là @RequestMapping
//@Validated
public class CategoryController {
    //Hiển thị tất cả categories
    @GetMapping("") //http://localhost:8088/api/v1/categories?page=1&limit=10
    public ResponseEntity<String> getAllCategories(
            @RequestParam("page") int page,
            @RequestParam("limit") int limit
    ) {
        return ResponseEntity.ok(String.format("getAllCategories, page = %d, limit = %d", page, limit));
    }

    //Thêm Category
    @PostMapping("")
    //Nếu tham số truyền vào là một Object thì sao? => Data Transfer Object = Request Object
    public ResponseEntity<?> insertCategory(@Valid  @RequestBody CategoryDTO categoryDTO, BindingResult result) {
        //Hiển thị lỗi, lỗi được lưu ở biến result
        if(result.hasErrors()) {
            //Trả về danh sách lỗi errorMessage kiểu String
           List<String> errorMessage =  result.getFieldErrors()
                   .stream()
                   .map(FieldError::getDefaultMessage)
                   .toList();
           return ResponseEntity.badRequest().body(errorMessage);
        }
        return ResponseEntity.ok("This is insertCategory" + categoryDTO);
    }

    @PutMapping("/{id}")
    public ResponseEntity<String> updateCategory(@PathVariable Long id) {
        return ResponseEntity.ok("insertCategory with id: "+id);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteCategory(@PathVariable Long id) {
        return ResponseEntity.ok("deleteCategory with id: "+id);
    }
}
