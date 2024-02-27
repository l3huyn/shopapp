package com.project.shopapp.configurations;

import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration //Annotation đánh dấu một class là một nguồn cấu hình (configuration class).
// Nguồn cấu hình này chứa các phương thức được đánh dấu bởi @Bean
// nơi mà các đối tượng (beans) được khai báo và cấu hình.

public class MapperConfiguration {
    //Khởi tạo đối tượng modelMapper() thì bên Service mới Inject được vào
    @Bean
    public ModelMapper modelMapper() {
        return new ModelMapper();
    }
}
