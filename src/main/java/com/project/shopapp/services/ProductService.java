package com.project.shopapp.services;


import com.project.shopapp.dtos.ProductDTO;
import com.project.shopapp.dtos.ProductImageDTO;
import com.project.shopapp.exceptions.DataNotFoundException;
import com.project.shopapp.exceptions.InvalidParamException;
import com.project.shopapp.models.Category;
import com.project.shopapp.models.Product;
import com.project.shopapp.models.ProductImage;
import com.project.shopapp.repositories.CategoryRepository;
import com.project.shopapp.repositories.ProductImageRepository;
import com.project.shopapp.repositories.ProductRepository;
import com.project.shopapp.responses.ProductResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service //Annotation để đánh dấu một class là một Spring Service.
// Spring Service là một thành phần của ứng dụng Spring Framework được thiết kế để chứa logic kinh doanh của ứng dụng.

@RequiredArgsConstructor
//Annotation này tự động tạo một constructor có tham số cho tất cả các trường (fields) final hoặc @NonNull trong một lớp.
public class ProductService implements IProductService{

    private final ProductRepository productRepository;

    private final CategoryRepository categoryRepository;

    private final ProductImageRepository productImageRepository;

    //Hàm thêm sản phẩm
    @Override
    public Product createProduct(ProductDTO productDTO) throws DataNotFoundException {
        //Tìm kiếm category theo Id => Nếu có tồn tại category đó thì mới cho insert vào DB
        Category existingCategory =  categoryRepository
                .findById(productDTO.getCategoryId())
                //Nếu không tìm ra => Trả về ngoại lệ với message bên dưới
                .orElseThrow(() ->
                        new DataNotFoundException("Cannot find category with ID: " + productDTO.getCategoryId()));

        //Convert từ ProductDTO sang Product
        Product newProduct = Product.builder()
                .name(productDTO.getName())
                .price(productDTO.getPrice())
                .thumbnail(productDTO.getThumbnail())
                .description(productDTO.getDescription())
                .category(existingCategory)
                .build();

        //Lưu sản phẩm vào DB
        return productRepository.save(newProduct);
    }

    //Hàm lấy sản phẩm theo Id
    @Override
    public Product getProductById(long productId) throws Exception {
        return productRepository.findById(productId)
                .orElseThrow(()
                        -> new DataNotFoundException("Cannot find product with ID: " + productId));
    }


    //Hàm lấy danh sách sản phẩm theo page và limit
    //--Trả về ProductResponse để hiển thị cho người dùng
    @Override
    public Page<ProductResponse> getAllProducts(PageRequest pageRequest) {
        //Lấy danh sách sản phẩm theo page và limit
        //--Dùng hàm map() để ánh xạ
        //--Gọi đến hàm fromProduct() của ProductResponse
        return productRepository
                .findAll(pageRequest)
                .map(ProductResponse::fromProduct);
    }


    //Hàm cập nhật sản phẩm
    @Override
    public Product updateProduct(long id, ProductDTO productDTO) throws Exception {
        //Lấy ra sản phẩm bằng Id và lưu vào biến existingProduct
        Product existingProduct = getProductById(id);

        //Kiểm tra xem existingProduct có rỗng không?
        // Nếu không rỗng => cập nhật SP, còn rỗng => trả về NULL
        if(existingProduct != null) {
            //Copy các thuộc tính từ ProductDTO -> Product
            //Có thể sử dụng ModelMapper

            //Tìm kiếm category theo Id => Nếu có tồn tại category đó thì mới cho insert vào DB
            Category existingCategory =  categoryRepository
                    .findById(productDTO.getCategoryId())
                    //Nếu không tìm ra => Trả về ngoại lệ với message bên dưới
                    .orElseThrow(() ->
                            new DataNotFoundException("Cannot find category with ID: " + productDTO.getCategoryId()));

            //Gán các thuộc tính
            existingProduct.setName(productDTO.getName());
            existingProduct.setCategory(existingCategory);
            existingProduct.setPrice(productDTO.getPrice());
            existingProduct.setDescription(productDTO.getDescription());
            existingProduct.setThumbnail(productDTO.getThumbnail());

            //Lưu vào DB
            return productRepository.save(existingProduct);
        }
        return null;
    }

    @Override
    public void deleteProduct(long id) {
        //Tìm sản phẩm cần xóa trước bằng Id
        //Optional<> : để kiểm tra xem một biến có giá trị tồn tại giá trị hay không
        Optional<Product> optionalProduct = productRepository.findById(id);

        //Nếu optionalProduct có tồn tại => Xóa
        optionalProduct.ifPresent(productRepository::delete);
    }

    @Override
    public boolean existsByName(String name) {
        return productRepository.existsByName(name);
    }

    //Xử lý lưu ảnh vào bảng ProductImage
    @Override
    public ProductImage createProductImage(Long productId, ProductImageDTO productImageDTO) throws Exception {
        //Tìm kiếm product theo Id
        Product existingProduct =  productRepository
                .findById(productId)
                //Nếu không tìm ra => Trả về ngoại lệ với message bên dưới
                .orElseThrow(() ->
                        new DataNotFoundException("Cannot find product with ID: " + productImageDTO.getProductId()));

        //Convert từ ProductImageDTO sang ProductImage
        ProductImage newProductImage = ProductImage.builder()
                .product(existingProduct)
                .imageUrl(productImageDTO.getImageUrl())
                .build();

        //Không cho insert quá 5 ảnh cho 1 sản phẩm
        int size = productImageRepository.findByProductId(productId).size();
        if(size >= ProductImage.MAXIMUM_IMAGES_PER_PRODUCT ) {
            throw new InvalidParamException("Number of images must be <= "
                    + ProductImage.MAXIMUM_IMAGES_PER_PRODUCT );
        }

        //Lưu vào DB
        return productImageRepository.save(newProductImage);
    }
}
