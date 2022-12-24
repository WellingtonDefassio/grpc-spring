package com.defassio.br.service;

import com.defassio.br.domain.Product;
import com.defassio.br.dto.ProductInputDTO;
import com.defassio.br.dto.ProductOutputDTO;
import com.defassio.br.repository.ProductRepository;
import org.springframework.stereotype.Service;

import java.util.List;

import static com.defassio.br.util.ProductConveterUtil.productInputToProduct;
import static com.defassio.br.util.ProductConveterUtil.productToProductOutputDTO;

@Service
public class ProductServiceImpl implements IProductService{


    private final ProductRepository productRepository;

    public ProductServiceImpl(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    @Override
    public ProductOutputDTO create(ProductInputDTO inputDTO) {
        var product = productInputToProduct(inputDTO);
        Product productCreated = this.productRepository.save(product);
        return productToProductOutputDTO(productCreated);
    }

    @Override
    public ProductOutputDTO findById(Long id) {
        return null;
    }

    @Override
    public void delete(Long id) {

    }

    @Override
    public List<ProductOutputDTO> findAll() {
        return null;
    }
}
