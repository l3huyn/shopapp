package com.project.shopapp.responses;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.project.shopapp.models.Product;
import lombok.*;

@Getter //Hàm getter
@Setter //Hàm Setter
@AllArgsConstructor //Hàm khởi tạo có đối số
@NoArgsConstructor //Hàm khởi tạo không đối số
@Builder //Annotation tự động sinh code cho việc xây dựng các phương thức khởi tạo (constructor) cho một lớp


//-- Thư mục Response dùng để lưu trữ thông tin, định nghĩa đối tượng trả về.
//-- Tùy biến Response để hiển thị cho người dùng (Không dùng Model và DTO để hiển thị ra tất cả)
//-- Cách viết khá giống DTO. Vì nó là giá trị trả về nên không cần Validate
public class ProductResponse extends BaseResponse { //ProductResponse -> Lưu một đối tương Response
    private String name;

    private Float price;

    private String thumbnail;

    private String description;

    @JsonProperty("category_id") //Annotation dùng để matching tên trường trong DB và tên thuộc tính trong DTO
    private Long categoryId;

    //Phương thức convert từ Product sang ProductResponse
    //--Phương thức nào sẽ tạo 1 đối tượng ProductResponse từ 1 đối tượng Product ban đầu
    //--Khi muốn hiện thị ProductResponse chỉ cần gọi hàm này và truyền tham số là 1 đối tượng Product vào
    public static ProductResponse fromProduct(Product product) {
        ProductResponse productResponse = ProductResponse.builder()
                .name(product.getName())
                .price(product.getPrice())
                .thumbnail(product.getThumbnail())
                .description(product.getDescription())
                .categoryId(product.getCategory().getId())
                .build();
        productResponse.setCreatedAt(product.getCreatedAt());
        productResponse.setUpdatedAt(product.getUpdatedAt());

        return productResponse;
    }
}
