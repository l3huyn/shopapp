package com.project.shopapp.controllers;

import com.project.shopapp.dtos.ProductDTO;
import jakarta.validation.Path;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StringUtils;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("api/v1/products") // http://localhost:8088/api/v1/products
public class ProductController {
    //Hàm thêm sản phẩm
    @PostMapping(value = "", consumes = MediaType.MULTIPART_FORM_DATA_VALUE) //chỉ định rằng một phương thức chỉ chấp nhận các yêu cầu HTTP có kiểu nội dung là "multipart/form-data".
    public ResponseEntity<?> createProduct(
            @Valid @RequestBody ProductDTO productDTO,
            @RequestPart("file") MultipartFile file,
            BindingResult result
            ) {
        try {
            //Hiển thị lỗi, lỗi được lưu ở biến result
            if(result.hasErrors()) {
                //Trả về mảng errorMessage kiểu String
                List<String> errorMessage =  result.getFieldErrors()
                        .stream()
                        .map(FieldError::getDefaultMessage)
                        .toList();
                return ResponseEntity.badRequest().body(errorMessage);
            }

            //Nếu file không rỗng => validate file
            if(file != NULL) {

                //Kiểm tra kích thước file và định dạng
                if(file.getSize() > 10 * 24 * 1024) { //Nếu kích thước lớn hơn 10MB => trả về lỗi
                    return ResponseEntity.status(HttpStatus.PAYLOAD_TOO_LARGE).body("File is too large! Maximum size is 10MB");
                }

                //Lấy định dạng file ảnh
                String contentType = file.getContentType();

                //Kiểm tra contentType có phải là file ảnh hay không?
                if(contentType == NULL || !contentType.startsWith("image/")) { //Nếu file rỗng và nó bắt đầu kh có image/ => trả về lỗi
                    return ResponseEntity.status(HttpStatus.UNSUPPORTED_MEDIA_TYPE).body("File must be an image");
                }
            }

            return ResponseEntity.ok("Product created successfully");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }


    //Hàm lưu file
    private String storeFile(MultipartFile file) throws IOException {

        //Lấy ra tên file gốc
        String filename = StringUtils.cleanPath(file.getOriginalFilename());

        //Thêm UUID vào trước tên file để đảm bảo tên file là duy nhất => Tránh bị trùng tên file khi upload
        String uniqueFilename = UUID.randomUUID().toString() + "_" + filename;

        //Đường dẫn đến thư mục muốn lưu trữ file
        java.nio.file.Path uploadDir = Paths.get("upload");

        //Kiểm tra và tạo thư mục nếu nó không tồn tại
        if(!Files.exists(uploadDir)) {
            Files.createDirectories(uploadDir);
        }

        //Đường dẫn đến file đích
        java.nio.file.Path destination = Paths.get(uploadDir.toString(), uniqueFilename);
    }


    //Hàm lấy danh sách sản phẩm
    @GetMapping("") //GetMapping để trống tức là lấy đường dẫn gốc của RequestMapping http://localhost:8088/api/v1/products
    public ResponseEntity<String> getProducts(
            @RequestParam("page") int page,
            @RequestParam("limit") int limit

    ) {
        return ResponseEntity.ok("Get product here");
    }

    //Hàm lấy một sản phẩm theo ID
    @GetMapping("/{id}")//http://localhost:8088/api/v1/products/5
    public ResponseEntity<String> getProductById(@PathVariable("id") String productId) {
        return ResponseEntity.ok("Product with ID: " + productId);
    }

    //Hàm xóa sản phẩm theo ID
    @DeleteMapping("/{id}")//http://localhost:8088/api/v1/products?id=
    public ResponseEntity<String> deleteProduct(@PathVariable Long id) {
        return ResponseEntity.ok(String.format("Product with id = %d delete successfully", id));
    }

}
