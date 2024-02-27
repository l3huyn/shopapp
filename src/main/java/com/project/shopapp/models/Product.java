package com.project.shopapp.models;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity //Annotation đánh dấu một class là Entity
@Table(name = "products") //Annotation dùng để chỉ định tên bảng trong CSDL mà Entity tương tác
@Builder //Annotation tự động sinh code cho việc xây dựng các phương thức khởi tạo (constructor) cho một lớp
@Getter //Hàm getter
@Setter //Hàm Setter
@AllArgsConstructor //Hàm khởi tạo có đối số
@NoArgsConstructor //Hàm khởi tạo không đối số
public class Product extends BaseEntity{
    @Id //Annotation đánh dấu một trường là khóa chính
    @GeneratedValue(strategy = GenerationType.IDENTITY) //Annotation giúp giá trị của trường khóa chính
    // sẽ được sinh tự động dựa trên tính năng tự động tăng (auto-increment) của cơ sở dữ liệu.
    private Long id;


    @Column(name = "name", nullable = false, length = 350) //Annotation đánh dấu trường name trong class Product là
    // một cột trong cơ sở dữ liệu với tên là "name".
    // nullable = false: Chỉ định rằng giá trị của cột không được phép là null.
    // length = 350: Chỉ định độ dài tối đa của cột (đối với các trường có kiểu dữ liệu có thể đo đếm độ dài, như VARCHAR).
    private String name;


    private Float price;


    @Column(name = "thumbnail", nullable = true, length = 350) //Annotation đánh dấu trường thumbnail trong class Product là
    // một cột trong cơ sở dữ liệu với tên là "thumbnail".
    // nullable = true: Chỉ định rằng giá trị của cột được phép là null.
    // length = 300: Chỉ định độ dài tối đa của cột (đối với các trường có kiểu dữ liệu có thể đo đếm độ dài, như VARCHAR).
    private String thumbnail;


    @Column(name = "description")
    private String description;


    @ManyToOne //Annotation để thiết lập một mối quan hệ nhiều-đến-một giữa hai Entity.
    // Mối quan hệ này thường ánh xạ một trường trong một Entity với một Entity khác
    @JoinColumn(name = "category_id") //Annotation này chỉ định tên cột trong bảng của Product sẽ
    // được sử dụng để ánh xạ với khóa ngoại tới Category. Trong trường hợp này, nó là cột có tên "category_id".
    private Category category; //Trong trường hợp này, Product sẽ có một khóa ngoại trỏ tới
    // bảng Category thông qua cột "category_id".


}
