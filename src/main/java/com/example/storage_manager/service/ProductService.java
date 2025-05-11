package com.example.storage_manager.service;

import com.example.storage_manager.dto.ProductCreateDTO;
import com.example.storage_manager.dto.ProductResponseDTO;
import com.example.storage_manager.entity.Category;
import com.example.storage_manager.entity.Product;
import com.example.storage_manager.mapper.ProductMapper;
import com.example.storage_manager.repository.ProductRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class ProductService {

    private final ProductRepository productRepository;
    private final ProductMapper productMapper;


    public ProductService(ProductRepository productRepository, ProductMapper productMapper) {
        this.productRepository = productRepository;
        this.productMapper = productMapper;
    }

    public ProductResponseDTO createProduct(ProductCreateDTO dto) {
        Product product = productMapper.toEntity(dto);
        product.setPendingAlert(product.getQuantity() < product.getMinAlert());
        product = productRepository.save(product);
        return productMapper.toResponseDTO(product);
    }

    public List<ProductResponseDTO> findbyCategory(Category category) {
        return productRepository.findByCategory(category)
                .stream()
                .map(productMapper::toResponseDTO)
                .collect(Collectors.toList());
    }

    // adaptar os métodos abaixo com os dtos
    public List<Product> findAllProducts() {
        return productRepository.findAll();
    }

    public Optional<Product> findByid(Long id) {
        return productRepository.findById(id);
    }

    public void delete(Long id) {
        productRepository.deleteById(id);
    }

    public Product update(Product product) {
        return productRepository.save(product);
    }



}

