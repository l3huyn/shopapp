package com.project.shopapp.dtos;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.*;

@Data //Là hàm toString
@Getter //Hàm getter
@Setter //Hàm Setter
@AllArgsConstructor //Hàm khởi tạo có đối số
@NoArgsConstructor //Hàm khởi tạo không đối số
public class ProductDTO {
    @NotBlank(message = "Title is required") //Annotation để kiểm tra xe chuỗi có rỗng hay không của trường name
    @Size(min = 3, max = 200, message = "Title must between 3 and 200 characters") //Annotation kiểm tra kích thước của trường name
    private String name;

    @Min(value = 0, message = "Price must be greater than or equal to 0") //Annotation min của giá
    @Max(value = 10000000, message = "Price must be less than or equal to 10000000") //Annotation max của giá
    private Float price;
    private String thumbnail;
    private String description;

    @JsonProperty("category_id") //Annotation dùng để matching tên trường trong DB và tên thuộc tính trong DTO
    private String categoryId;
}
