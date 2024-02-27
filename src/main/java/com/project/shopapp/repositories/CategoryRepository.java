package com.project.shopapp.repositories;

import com.project.shopapp.models.Category;
import org.springframework.data.jpa.repository.JpaRepository;

//Category là từ models, còn Long là kiểu dữ liệu của khóa chính
public interface CategoryRepository extends JpaRepository<Category, Long> {

}
