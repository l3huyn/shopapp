package com.project.shopapp.services;

import com.project.shopapp.dtos.CategoryDTO;
import com.project.shopapp.models.Category;
import com.project.shopapp.repositories.CategoryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;

@Service //Annotation để đánh dấu một class là một Spring Service.
// Spring Service là một thành phần của ứng dụng Spring Framework được thiết kế để chứa logic kinh doanh của ứng dụng.

@RequiredArgsConstructor //Annotation này tự động tạo một constructor có tham số cho tất cả các trường (fields) final hoặc @NonNull trong một lớp.
public class CategoryService implements ICategoryService{

    //Khai báo thuộc tính để CategoryService tương tác CategoryRepository
    // Cơ chế Dependency Injection
    private final CategoryRepository categoryRepository;


    //Hàm tạo 1 Category
    @Override
    public Category createCategory(CategoryDTO categoryDTO) {
        //Cách convert CategoryDTO sang Category
        //Tạo ra đối tượng Category sau đó gán getName() của categoryDTO sang cho newCategory
        Category newCategory = Category
                .builder()
                .name(categoryDTO.getName())
                .build();
        return categoryRepository.save(newCategory);
    }


    //Hàm lấy ra một Category bằng Id
    @Override
    public Category getCategoryById(long id) {
        return categoryRepository.findById(id) //Tìm bằng id
                //Nếu không tìm thấy => trả về lambda và tạo một đối tượng RuntimeException mới với message như bên dưới
                .orElseThrow( () -> new RuntimeException("Category not found"));
    }

    
    //Hàm trả về tất cả Categories
    @Override
    public List<Category> getAllCategories() {
        return categoryRepository.findAll();
    }


    //Hàm cập nhật 1 Category
    @Override
    public Category updateCategory(long categoryId, CategoryDTO categoryDTO) {
        //Tìm Category với categoryId này
        Category existingCategory = getCategoryById(categoryId);

        //Sửa category => vì category chỉ có trường name nên chỉ setName()
        existingCategory.setName(categoryDTO.getName());

        //Lưu lại
        categoryRepository.save(existingCategory);

        //Trả về existingCategory đã sửa đổi
        return existingCategory;
    }


    //Hàm xóa Category
    @Override
    public void deleteCategory(long id) {
        //Xóa cứng
        categoryRepository.deleteById(id);
    }
}
