package com.example.warehouse_server.Controller;

import com.example.warehouse_server.Entities.Category;
import com.example.warehouse_server.Repository.CategoryRepository;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/categories")
@CrossOrigin
public class CategoryController {

    private final CategoryRepository categoryRepository;

    public CategoryController(CategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
    }

    // READ all
    @GetMapping
    public List<Category> getAll() {
        return categoryRepository.findAll();
    }

    // READ one
    @GetMapping("/{id}")
    public Category getOne(@PathVariable Integer id) {
        return categoryRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Категория не найдена"));
    }

    // CREATE
    @PostMapping
    public Category create(@RequestBody Category c) {
        c.setId(null); // чтобы точно создавалась новая запись
        return categoryRepository.save(c);
    }

    // UPDATE
    @PutMapping("/{id}")
    public Category update(@PathVariable Integer id, @RequestBody Category c) {
        Category ex = categoryRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Категория не найдена"));
        ex.setName(c.getName());
        return categoryRepository.save(ex);
    }

    // DELETE
    @DeleteMapping("/{id}")
    public void delete(@PathVariable Integer id) {
        categoryRepository.deleteById(id);
    }
}
