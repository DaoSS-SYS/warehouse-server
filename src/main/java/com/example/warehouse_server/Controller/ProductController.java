package com.example.warehouse_server.Controller;

import com.example.warehouse_server.Entities.Product;
import com.example.warehouse_server.Repository.ProductRepository;
import org.springframework.web.bind.annotation.*;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import java.util.List;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.web.bind.annotation.RequestParam;
@RestController
@RequestMapping("/api/products")
@CrossOrigin
public class ProductController {

    private final ProductRepository productRepository;

    public ProductController(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }



    @GetMapping
    public List<Product> getAll(
            @RequestParam(required = false) String name,
            @RequestParam(required = false) Integer categoryId,
            Pageable pageable
    ) {
        Specification<Product> spec = null;

        if (name != null && !name.isBlank()) {
            String n = name.trim().toLowerCase();
            Specification<Product> byName =
                    (root, q, cb) -> cb.like(cb.lower(root.get("name")), "%" + n + "%");
            spec = (spec == null) ? byName : spec.and(byName);
        }

        if (categoryId != null) {
            Specification<Product> byCategory =
                    (root, q, cb) -> cb.equal(root.get("category").get("id"), categoryId);
            spec = (spec == null) ? byCategory : spec.and(byCategory);
        }

        if (spec == null) {
            return productRepository.findAll(pageable).getContent();
        }

        return productRepository.findAll(spec, pageable).getContent();
    }


    @GetMapping("/{id}")
    public Product getOne(@PathVariable Integer id) {
        return productRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Товар не найден"));
    }


    @PostMapping
    public Product create(@RequestBody Product p) {
        p.setId(null);
        return productRepository.save(p);
    }

    @PutMapping("/{id}")
    public Product update(@PathVariable Integer id, @RequestBody Product p) {
        Product ex = productRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Товар не найден"));

        ex.setName(p.getName());
        ex.setQuantity(p.getQuantity());
        ex.setCategory(p.getCategory()); // важно: category.id должен существовать

        return productRepository.save(ex);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Integer id) {
        productRepository.deleteById(id);
    }
}
