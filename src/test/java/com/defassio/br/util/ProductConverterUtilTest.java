package com.defassio.br.util;

import com.defassio.br.domain.Product;
import com.defassio.br.dto.ProductInputDTO;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

public class ProductConverterUtilTest {
    @Test
    public void productToProductOutputDtoTest() {
        var product = new Product(1L, "product name", 10.00, 10);
        var productOutputDto = ProductConveterUtil.productToProductOutputDTO(product);
        Assertions.assertThat(product)
                .usingRecursiveComparison()
                .isEqualTo(productOutputDto);
    }
    @Test
    public void productInputToProduct() {
        var productInput = new ProductInputDTO("product name", 10.00, 10);
        var product = ProductConveterUtil.productInputToProduct(productInput);
        Assertions.assertThat(productInput)
                .usingRecursiveComparison()
                .isEqualTo(product);
    }


}
