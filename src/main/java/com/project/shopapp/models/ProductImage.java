package com.project.shopapp.models;

import jakarta.persistence.*;
import lombok.*;

@Entity //Annotation đánh dấu một class là Entity
@Table(name = "product_images") //Annotation dùng để chỉ định tên bảng trong CSDL mà Entity tương tác
@Builder //Annotation tự động sinh code cho việc xây dựng các phương thức khởi tạo (constructor) cho một lớp
@Getter //Hàm getter
@Setter //Hàm Setter
@AllArgsConstructor //Hàm khởi tạo có đối số
@NoArgsConstructor //Hàm khởi tạo không đối số
public class ProductImage {

    //Khai báo thuộc tính MAXIMUM_IMAGES_PER_PRODUCT (5 ảnh trên 1 sản phẩm)
    public static final int MAXIMUM_IMAGES_PER_PRODUCT = 5;

    @Id //Annotation đánh dấu một trường là khóa chính
    @GeneratedValue(strategy = GenerationType.IDENTITY) //Annotation giúp giá trị của trường khóa chính
    // sẽ được sinh tự động dựa trên tính năng tự động tăng (auto-increment) của cơ sở dữ liệu.
    private Long id;


    @ManyToOne //Annotation để thiết lập một mối quan hệ nhiều-đến-một giữa hai Entity.
    // Mối quan hệ này thường ánh xạ một trường trong một Entity với một Entity khác
    @JoinColumn(name = "product_id") //Annotation này chỉ định tên cột trong bảng của product_images sẽ
    // được sử dụng để ánh xạ với khóa ngoại tới products. Trong trường hợp này, nó là cột có tên "product_id".
    private Product product; //Trong trường hợp này, ProductImage sẽ có một khóa ngoại trỏ tới
    // bảng products thông qua cột "product_id".


    @Column(name = "image_url", length = 380) //Annotation đánh dấu trường thumbnail trong class ProductImage là
    // một cột trong cơ sở dữ liệu với tên là "image_url".
    // length = 380: Chỉ định độ dài tối đa của cột (đối với các trường có kiểu dữ liệu có thể đo đếm độ dài, như VARCHAR).
    private String imageUrl;
}
