package com.example.storage_manager.service;

import com.example.storage_manager.dto.ProductCreateDTO;
import com.example.storage_manager.dto.ProductResponseDTO;
import com.example.storage_manager.entity.Category;
import com.example.storage_manager.entity.Product;
import com.example.storage_manager.entity.Sex;
import com.example.storage_manager.mapper.ProductMapper;
import com.example.storage_manager.repository.ProductRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class ProductService {

    private final ProductRepository productRepository;

    public ProductService(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    // Método para criar um produto
    public ProductResponseDTO createProduct(ProductCreateDTO dto) {
        Product product = new Product();
        product.setName(dto.getName());
        product.setCategory(dto.getCategory());
        product.setSex(dto.getSex());
        product.setQuantity(dto.getQuantity());
        product.setAgeUse(dto.getAgeUse());
        product.setMinAlert(dto.getMinAlert());

        Product savedProduct = productRepository.save(product);
        return convertToResponseDTO(savedProduct);
    }

    // Método para listar todos os produtos
    public List<ProductResponseDTO> findAllProducts() {
        return productRepository.findAll()
                .stream()
                .map(this::convertToResponseDTO)
                .collect(Collectors.toList());
    }

    // Método para buscar produto por ID
    public Optional<ProductResponseDTO> findById(Long id) {
        return productRepository.findById(id)
                .map(this::convertToResponseDTO);
    }

    // Método para atualizar um produto
    public ProductResponseDTO updateProduct(Long id, ProductCreateDTO dto) {
        Product product = productRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Product not found"));
        product.setName(dto.getName());
        product.setCategory(dto.getCategory());
        product.setSex(dto.getSex());
        product.setQuantity(dto.getQuantity());
        product.setAgeUse(dto.getAgeUse());
        product.setMinAlert(dto.getMinAlert());

        Product updatedProduct = productRepository.save(product);
        return convertToResponseDTO(updatedProduct);
    }

    // Método para deletar um produto
    public void delete(Long id) {
        productRepository.deleteById(id);
    }

    // Método para filtrar por categoria e sexo
    public List<ProductResponseDTO> findByCategoryAndSex(Category category, Sex sex) {
        return productRepository.findByCategoryAndSex(category, sex)
                .stream()
                .map(this::convertToResponseDTO)
                .collect(Collectors.toList());
    }

    // Método auxiliar para converter Product para ProductResponseDTO
    private ProductResponseDTO convertToResponseDTO(Product product) {
        ProductResponseDTO dto = new ProductResponseDTO();
        dto.setId(product.getId());
        dto.setName(product.getName());
        dto.setCategory(product.getCategory());
        dto.setSex(product.getSex());
        dto.setQuantity(product.getQuantity());
        dto.setAgeUse(product.getAgeUse());
        dto.setAgeUse(product.getAgeUse());
        return dto;
    }
}

