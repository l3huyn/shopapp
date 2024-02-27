package com.project.shopapp.controllers;


import com.project.shopapp.dtos.CategoryDTO;
import com.project.shopapp.models.Category;
import com.project.shopapp.services.CategoryService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController //Annotation đánh dấu một lớp Java như là một RESTful Controller.
@RequestMapping("${api.prefix}/categories") //Annotation xử lý các yêu cầu HTTP trong một RESTful Controller là @RequestMapping
//@Validated

/* Dependency Injection */
@RequiredArgsConstructor //Annotation tự động tạo một constructor có tham số cho tất cả các trường (fields) final hoặc @NonNull trong một lớp.
public class CategoryController {

    //Khai báo thuộc tính để CategoryController tương tác với CategoryService
    // => Cơ chế Dependency Injection
    private final CategoryService categoryService;


    /* Tạo 1 Category */
    @PostMapping("")
    //Nếu tham số truyền vào là một Object thì sao? => Data Transfer Object = Request Object
    public ResponseEntity<?> createCategory(@Valid  @RequestBody CategoryDTO categoryDTO, BindingResult result) {
        //Hiển thị lỗi, lỗi được lưu ở biến result
        if(result.hasErrors()) {
            //Trả về danh sách lỗi errorMessage kiểu String
            List<String> errorMessage =  result.getFieldErrors()
                    .stream()
                    .map(FieldError::getDefaultMessage)
                    .toList();
            return ResponseEntity.badRequest().body(errorMessage);
        }

        //Tạo ra category
        categoryService.createCategory(categoryDTO);

        return ResponseEntity.ok("Insert Category successfully");
    }



    //Hiển thị tất cả categories
    @GetMapping("") //http://localhost:8088/api/v1/categories?page=1&limit=10
    public ResponseEntity<List<Category>> getAllCategories(
            @RequestParam("page") int page,
            @RequestParam("limit") int limit
    ) {
        //Trả về mảng các categories
        List<Category> categories = categoryService.getAllCategories();
        return ResponseEntity.ok(categories);
    }


    //Cập nhật Category
    @PutMapping("/{id}")
    public ResponseEntity<String> updateCategory(@PathVariable Long id, @Valid @RequestBody CategoryDTO categoryDTO) {
        categoryService.updateCategory(id, categoryDTO);
        return ResponseEntity.ok("Update category successfully");
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteCategory(@PathVariable Long id) {
        categoryService.deleteCategory(id);
        return ResponseEntity.ok("Delete category with ID: " + id + " successfully");
    }
}
