package com.alif.project.interview.repositoryTest;

import com.alif.project.interview.model.ProductDiscountParameter;
import com.alif.project.interview.repository.ProductDiscountParameterRepository;
import org.assertj.core.api.Assertions;

import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

@DataJpaTest
@AutoConfigureTestDatabase(replace= AutoConfigureTestDatabase.Replace.NONE)
public class ProductDiscountParameterRepositoryTest {

    @Autowired
    ProductDiscountParameterRepository productDiscountParameterRepository;

    @Test
    public void ProductDiscountParameterRepository_Save_ReturnSaveProductDiscountParameter () {
        ProductDiscountParameter productDiscountParameter = new ProductDiscountParameter();
        productDiscountParameter.setProductCategory("Electronic");
        productDiscountParameter.setDiscount(0.5);


        ProductDiscountParameter saveProductDiscountParameter = productDiscountParameterRepository.save(productDiscountParameter);

        //Assert
        Assertions.assertThat(saveProductDiscountParameter).isNotNull();
        Assertions.assertThat(saveProductDiscountParameter.getId()).isGreaterThan(0);
    }
}
