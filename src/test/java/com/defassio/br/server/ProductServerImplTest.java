package com.defassio.br.server;

import com.defassio.br.domain.Product;
import com.defassio.br.dto.ProductInputDTO;
import com.defassio.br.dto.ProductOutputDTO;
import com.defassio.br.exception.AlreadyExistsException;
import com.defassio.br.exception.NotFoundException;
import com.defassio.br.repository.ProductRepository;
import com.defassio.br.service.ProductServiceImpl;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.rmi.AlreadyBoundException;
import java.util.List;
import java.util.Optional;

import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class ProductServerImplTest {
    @Mock
    private ProductRepository productRepository;

    @InjectMocks
    private ProductServiceImpl productService;

    @Test
    @DisplayName("when created product is called with correct params should return a product")
    public void createProductSuccessTest(){
        Product product = new Product(1L, "product name", 10.00, 10);
        when(productRepository.save(any())).thenReturn(product);
        ProductInputDTO inputDTO = new ProductInputDTO("product name", 10.00, 10);
        ProductOutputDTO outputDTO = productService.create(inputDTO);

        Assertions.assertThat(outputDTO)
                .usingRecursiveComparison()
                .isEqualTo(product);

    }

    @Test
    @DisplayName("when created product is called with duplicate name, should throws ProductAlreadyExistsException")
    public void createProductExceptionTest(){
        Product product = new Product(1L, "product name", 10.00, 10);
        when(productRepository.findByNameIgnoreCase(any())).thenReturn(Optional.of(product));
        ProductInputDTO inputDTO = new ProductInputDTO("product name", 10.00, 10);

        Assertions.assertThatExceptionOfType(AlreadyExistsException.class)
                        .isThrownBy(() -> productService.create(inputDTO));


    }

    @Test
    @DisplayName("when findById product is called with correct params should return a product")
    public void findProductSuccessTest(){
        Long id = 1L;
        Product product = new Product(1L, "product name", 10.00, 10);

        when(productRepository.findById(any())).thenReturn(Optional.of(product));

        ProductOutputDTO outputDTO = productService.findById(id);

        Assertions.assertThat(outputDTO)
                .usingRecursiveComparison()
                .isEqualTo(product);

    }

    @Test
    @DisplayName("when findById notFound a product a error should be throw")
    public void findByIdProductExceptionTest(){
        Long id = 1L;

        when(productRepository.findById(any())).thenReturn(Optional.empty());

        Assertions.assertThatExceptionOfType(NotFoundException.class)
                .isThrownBy(() -> productService.findById(id));
    }

    @Test
    @DisplayName("when delete product is call with id should not throws")
    public void deleteProductSuccessTest(){
        Long id = 3L;
        Product product = new Product(3L, "product name", 10.00, 10);

        when(productRepository.findById(any())).thenReturn(Optional.of(product));

        Assertions.assertThatNoException().isThrownBy(() -> productService.delete(id));
    }

    @Test
    @DisplayName("when delete notFound a product a error should be throw")
    public void deleteExceptionTest(){
        Long id = 1L;

        when(productRepository.findById(any())).thenReturn(Optional.empty());

        Assertions.assertThatExceptionOfType(NotFoundException.class)
                .isThrownBy(() -> productService.delete(id));
    }

    @Test
    @DisplayName("when findAll product is called shold return a list")
    public void findAllSuccessTest(){

        List<Product> products = List.of(new Product(1L, "product name", 10.00, 10), new Product(4L, "product name2", 10.00, 10));

        when(productRepository.findAll()).thenReturn(products);

        List<ProductOutputDTO> outputDTOList = productService.findAll();

        Assertions.assertThat(outputDTOList)
                .usingRecursiveComparison()
                .isEqualTo(products);

    }


}
