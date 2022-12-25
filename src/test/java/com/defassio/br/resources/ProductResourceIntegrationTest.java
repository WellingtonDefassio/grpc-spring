package com.defassio.br.resources;

import com.defassio.br.*;
import io.grpc.StatusRuntimeException;
import net.devh.boot.grpc.client.inject.GrpcClient;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.TestPropertySource;

@SpringBootTest
@TestPropertySource("classpath:application-test.properties")
@DirtiesContext
public class ProductResourceIntegrationTest {


    @GrpcClient("inProcess")
    private ProductServiceGrpc.ProductServiceBlockingStub serviceBlockingStub;


    @Test
    @DisplayName("when valid data is provided a product is created")
    public void createProductSuccessTest() {
        ProductRequest productRequest = ProductRequest.newBuilder()
                .setName("product name")
                .setPrice(150.00)
                .setQuantityInStock(12)
                .build();


        ProductResponse productResponse = serviceBlockingStub.create(productRequest);

        Assertions.assertThat(productRequest)
                .usingRecursiveComparison()
                .comparingOnlyFields("name", "price", "quantity_in_stock")
                .isEqualTo(productResponse);

    }

    @Test
    @DisplayName("when create is called with product duplicated name")
    public void createProductAlreadyExistsTest() {
        ProductRequest productRequest = ProductRequest.newBuilder()
                .setName("Product A")
                .setPrice(150.00)
                .setQuantityInStock(12)
                .build();


        Assertions.assertThatExceptionOfType(StatusRuntimeException.class)
                .isThrownBy(() -> serviceBlockingStub.create(productRequest))
                .withMessage("ALREADY_EXISTS: Produto Product A já cadastrado no sistema.");

    }

    @Test
    @DisplayName("when findBy id called with correct id a product is returned")
    public void findByIdSuccessTest() {
        FindByIdRequest request = FindByIdRequest.newBuilder().setId(2L).build();
        ProductResponse productResponse = serviceBlockingStub.findByID(request);
        Assertions.assertThat(productResponse.getId()).isEqualTo(request.getId());

    }

    @Test
    @DisplayName("when findBy id not found a product should throws a not found Exception")
    public void findByIdExceptionTest() {
        FindByIdRequest request = FindByIdRequest.newBuilder().setId(10L).build();

        Assertions.assertThatExceptionOfType(StatusRuntimeException.class)
                .isThrownBy(() -> serviceBlockingStub.findByID(request))
                .withMessage("NOT_FOUND: Produto com ID 10 não encontrado.");

    }

    @Test
    @DisplayName("when findBy id with valid id should not throw")
    public void deleteSuccessTest() {
        DeleteByIdRequest request = DeleteByIdRequest.newBuilder().setId(8L).build();

        Assertions.assertThatNoException().isThrownBy(() -> serviceBlockingStub.delete(request));

    }

    @Test
    @DisplayName("when delete by id not found a product should throws a not found Exception")
    public void deleteTestExceptionTest() {
        DeleteByIdRequest request = DeleteByIdRequest.newBuilder().setId(101L).build();

        Assertions.assertThatExceptionOfType(StatusRuntimeException.class)
                .isThrownBy(() -> serviceBlockingStub.delete(request))
                .withMessage("NOT_FOUND: Produto com ID 101 não encontrado.");


    }

    @Test
    @DisplayName("when findAll is called a list must be return")
    public void findAllSuccessTest() {
        EmptyRequest emptyRequest = EmptyRequest.newBuilder().build();
        ProductResponseList responseList = serviceBlockingStub.findAll(emptyRequest);

        Assertions.assertThat(responseList).isInstanceOf(ProductResponseList.class);
        Assertions.assertThat(responseList.getProductsCount()).isEqualTo(3);


    }
}
