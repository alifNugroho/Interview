package com.alif.project.interview.serviceTest;

import com.alif.project.interview.dto.request.product_discount_parameter.AddProductDiscountParameterRequestDto;
import com.alif.project.interview.dto.response.product_discount_parameter.AddProductDiscountParameterResponseDto;
import com.alif.project.interview.model.ProductDiscountParameter;
import com.alif.project.interview.repository.ProductDiscountParameterRepository;
import com.alif.project.interview.service.impl.ProductDiscountParameterServiceImpl;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Date;

import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class ProductDiscountParameterServiceTest {

    @Mock
    private ProductDiscountParameterRepository productDiscountParameterRepository;

    @InjectMocks
    private ProductDiscountParameterServiceImpl productDiscountParameterService;

    @Test
    public void ProductDiscountParameterService_AddDiscountParameter_ReturnAddTaskResponseDto() {

        ProductDiscountParameter productDiscountParameter = new ProductDiscountParameter();
        productDiscountParameter.setProductCategory("Electronic");
        productDiscountParameter.setDiscount(0.5);

        AddProductDiscountParameterRequestDto addProductDiscountParameterRequestDto = new AddProductDiscountParameterRequestDto();
        addProductDiscountParameterRequestDto.setProductCategory(productDiscountParameter.getProductCategory());
        addProductDiscountParameterRequestDto.setDiscount(productDiscountParameter.getDiscount());


        when(productDiscountParameterRepository.save(Mockito.any(ProductDiscountParameter.class))).thenReturn(productDiscountParameter);

        AddProductDiscountParameterResponseDto addProductDiscountParameterResponseDto = productDiscountParameterService.addDiscountParameter(addProductDiscountParameterRequestDto);

        Assertions.assertThat(addProductDiscountParameterResponseDto).isNotNull();
    }
}
