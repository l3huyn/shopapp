package com.project.shopapp.services;
import com.project.shopapp.dtos.ProductDTO;
import com.project.shopapp.dtos.ProductImageDTO;
import com.project.shopapp.exceptions.DataNotFoundException;
import com.project.shopapp.models.*;
import com.project.shopapp.responses.ProductResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;


public interface IProductService {

    //Hàm tạo sản phẩm
    Product createProduct(ProductDTO productDTO) throws Exception;

    //Hàm lấy sản phẩm theo ID
    Product getProductById(long id) throws Exception;

    //Hàm lấy danh sách sản phẩm theo page và limit
    //--Trong trường hợp này trả về ProductResponse (ProductResponse -> dùng để hiển thị ra người dùng)
    Page<ProductResponse> getAllProducts(PageRequest pageRequest);

    //Hàm cập nhật sản phẩm
    Product updateProduct(long id, ProductDTO productDTO) throws Exception;

    //Hàm xóa sản phẩm
    void deleteProduct(long id);

    //Hàm kiểm tra tên có tồn tại
    boolean existsByName(String name);

    //Hàm tạo ảnh sản phẩm
    ProductImage createProductImage(Long productId, ProductImageDTO productImageDTO) throws Exception;
}
