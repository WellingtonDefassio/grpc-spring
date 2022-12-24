package com.defassio.br.util;

import com.defassio.br.domain.Product;
import com.defassio.br.dto.ProductInputDTO;
import com.defassio.br.dto.ProductOutputDTO;

public class ProductConveterUtil {

    public static ProductOutputDTO productToProductOutputDTO(Product product) {
        return new ProductOutputDTO(product.getId(), product.getName(), product.getPrice(), product.getQuantityInStock());
    }

    public static Product productInputToProduct(ProductInputDTO productInputDTO) {
        return new Product(null, productInputDTO.getName(), productInputDTO.getPrice(), productInputDTO.getQuantityInStock());
    }

}
