package com.defassio.br.resources;

import com.defassio.br.*;
import com.defassio.br.dto.ProductInputDTO;
import com.defassio.br.dto.ProductOutputDTO;
import com.defassio.br.service.IProductService;
import io.grpc.stub.StreamObserver;
import net.devh.boot.grpc.server.service.GrpcService;

import java.util.List;
import java.util.stream.Collectors;

@GrpcService
public class ProductResource extends ProductServiceGrpc.ProductServiceImplBase {

    private final IProductService productService;

    public ProductResource(IProductService productService) {
        this.productService = productService;
    }

    @Override
    public void create(ProductRequest request, StreamObserver<ProductResponse> responseObserver) {
        ProductInputDTO inputDTO = new ProductInputDTO(
                request.getName(), request.getPrice(), request.getQuantityInStock()
        );

        ProductOutputDTO productOutputDTO = this.productService.create(inputDTO);

        ProductResponse productResponse = ProductResponse.newBuilder()
                .setId(productOutputDTO.getId())
                .setName(productOutputDTO.getName())
                .setPrice(productOutputDTO.getPrice())
                .setQuantityInStock(productOutputDTO.getQuantityInStock())
                .build();

        responseObserver.onNext(productResponse);
        responseObserver.onCompleted();

    }

    @Override
    public void findByID(FindByIdRequest request, StreamObserver<ProductResponse> responseObserver) {
        ProductOutputDTO outputDTO = productService.findById(request.getId());
        ProductResponse response = ProductResponse.newBuilder()
                .setId(outputDTO.getId())
                .setName(outputDTO.getName())
                .setPrice(outputDTO.getPrice())
                .setQuantityInStock(outputDTO.getQuantityInStock())
                .build();

        responseObserver.onNext(response);
        responseObserver.onCompleted();
    }

    @Override
    public void delete(DeleteByIdRequest request, StreamObserver<EmptyResponse> responseObserver) {
        productService.delete(request.getId());
        responseObserver.onNext(EmptyResponse.newBuilder().build());
        responseObserver.onCompleted();
    }


    @Override
    public void findAll(EmptyRequest request, StreamObserver<ProductResponseList> responseObserver) {
        List<ProductOutputDTO> dtoList = productService.findAll();
        List<ProductResponse> responseList = dtoList.stream()
                .map(p -> ProductResponse.newBuilder()
                        .setId(p.getId())
                        .setName(p.getName())
                        .setPrice(p.getPrice())
                        .setQuantityInStock(p.getQuantityInStock())
                        .build())
                .collect(Collectors.toList());

        ProductResponseList productResponseList = ProductResponseList.newBuilder()
                .addAllProducts(responseList).build();

        responseObserver.onNext(productResponseList);
        responseObserver.onCompleted();

    }
}
