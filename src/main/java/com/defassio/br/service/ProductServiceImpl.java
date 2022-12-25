package com.defassio.br.service;

import com.defassio.br.domain.Product;
import com.defassio.br.dto.ProductInputDTO;
import com.defassio.br.dto.ProductOutputDTO;
import com.defassio.br.exception.AlreadyExistsException;
import com.defassio.br.exception.NotFoundException;
import com.defassio.br.repository.ProductRepository;
import com.defassio.br.util.ProductConveterUtil;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

import static com.defassio.br.util.ProductConveterUtil.productInputToProduct;
import static com.defassio.br.util.ProductConveterUtil.productToProductOutputDTO;

@Service
public class ProductServiceImpl implements IProductService {


    private final ProductRepository productRepository;

    public ProductServiceImpl(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    @Override
    public ProductOutputDTO create(ProductInputDTO inputDTO) {
        checkDuplicity(inputDTO.getName());
        var product = productInputToProduct(inputDTO);
        Product productCreated = this.productRepository.save(product);
        return productToProductOutputDTO(productCreated);
    }

    @Override
    public ProductOutputDTO findById(Long id) {
        Product product = this.productRepository.findById(id)
                .orElseThrow(() -> new NotFoundException(id));
        return ProductConveterUtil.productToProductOutputDTO(product);
    }

    @Override
    public void delete(Long id) {
        Product product = this.productRepository.findById(id)
                .orElseThrow(() -> new NotFoundException(id));

        productRepository.delete(product);

    }

    @Override
    public List<ProductOutputDTO> findAll() {
        List<Product> productList = productRepository.findAll();
        List<ProductOutputDTO> outputDTOS = productList.stream().map(ProductConveterUtil::productToProductOutputDTO)
                .collect(Collectors.toList());

        return outputDTOS;
    }

    private void checkDuplicity(String nome) {
        productRepository.findByNameIgnoreCase(nome)
                .ifPresent(e -> {
                    throw new AlreadyExistsException(nome);

                });
    }
}
