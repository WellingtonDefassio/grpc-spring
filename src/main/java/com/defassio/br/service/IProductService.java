package com.defassio.br.service;

import com.defassio.br.dto.ProductInputDTO;
import com.defassio.br.dto.ProductOutputDTO;

import java.util.List;

public interface IProductService {

   ProductOutputDTO create(ProductInputDTO inputDTO);
   ProductOutputDTO findById(Long id);
   void delete(Long id);
   List<ProductOutputDTO> findAll();

}
