package com.ecommerce.controller;

import com.ecommerce.model.Product;
import com.ecommerce.repository.ProductRepository;
import com.ecommerce.service.AuditService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/products")
public class ProductController {

    private final ProductRepository productRepository;
    private final AuditService auditService;

    // inyección de dependencias x constructor
    public ProductController(ProductRepository productRepository, AuditService auditService) {
        this.productRepository = productRepository;
        this.auditService = auditService;
    }

    @GetMapping
    public List<Product> getAllProducts(@RequestHeader("X-User-Id") Long userId) {
        List<Product> products = productRepository.findAll();
        auditService.logAction("User " + userId + " listed all products", "Product", null);
        return products;
    }

    @PostMapping
    public Product createProduct(@RequestBody Product product, @RequestHeader("X-User-Id") Long userId) {
        Product saved = productRepository.save(product);
        auditService.logAction("User " + userId + " created product", "Product", saved.getId());
        return saved;
    }

    @GetMapping("/{id}")
    public Product getProduct(@PathVariable Long id, @RequestHeader("X-User-Id") Long userId) {
        Product product = productRepository.findById(id).orElse(null);
        auditService.logAction("User " + userId + " viewed product", "Product", id);
        return product;
    }

    @PutMapping("/{id}")
    public Product updateProduct(@PathVariable Long id, @RequestBody Product product, @RequestHeader("X-User-Id") Long userId) {
        product.setId(id);
        Product updated = productRepository.save(product);
        auditService.logAction("User " + userId + " updated product", "Product", id);
        return updated;
    }

    @DeleteMapping("/{id}")
    public void deleteProduct(@PathVariable Long id, @RequestHeader("X-User-Id") Long userId) {
        productRepository.deleteById(id);
        auditService.logAction("User " + userId + " deleted product", "Product", id);
    }
}