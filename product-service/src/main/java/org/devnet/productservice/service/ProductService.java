package org.devnet.productservice.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.devnet.productservice.model.Product;
import org.devnet.productservice.payload.request.ProductRequest;
import org.devnet.productservice.payload.response.ProductResponse;
import org.devnet.productservice.repository.ProductRepository;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Slf4j
@Service
@Transactional
@RequiredArgsConstructor
public class ProductService {
    private final ModelMapper mapper;
    private final ProductRepository productRepository;

    public ResponseEntity<?> createProduct(ProductRequest productRequest) {
        Product product = mapper.map(productRequest, Product.class);
        product = productRepository.save(product);
        log.info("Product {} saved", product.getId());
        ProductResponse productResponse = mapper.map(product, ProductResponse.class);
        return new ResponseEntity<>(productResponse, HttpStatus.CREATED);
    }

    @Transactional(readOnly = true)
    public ResponseEntity<?> getAllProducts() {
        List<ProductResponse> productResponses = productRepository.findAll()
                .stream()
                .map(product -> mapper.map(product, ProductResponse.class))
                .toList();
        return new ResponseEntity<>(productResponses, HttpStatus.OK);
    }
}
