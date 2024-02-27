package com.project.shopapp.services;

import com.project.shopapp.dtos.CategoryDTO;
import com.project.shopapp.models.Category;

import java.util.List;

public interface ICategoryService {

    //Hàm thêm mới 1 category
    Category createCategory(CategoryDTO category);

    //Hàm lấy Category bằng Id
    Category getCategoryById(long id);

    //Hàm lấy ra tất cả Category
    List<Category> getAllCategories();

    //Hàm cập nhật Category bằng Id
    Category updateCategory(long categoryId, CategoryDTO category);

    //Hàm xóa một Category
    void deleteCategory(long id);

}
