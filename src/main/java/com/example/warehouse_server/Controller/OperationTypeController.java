package com.example.warehouse_server.Controller;

import com.example.warehouse_server.Entities.OperationType;
import com.example.warehouse_server.Repository.OperationTypeRepository;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/operation-types")
@CrossOrigin
public class OperationTypeController {

    private final OperationTypeRepository repo;

    public OperationTypeController(OperationTypeRepository repo) {
        this.repo = repo;
    }

    @GetMapping
    public List<OperationType> all() {
        return repo.findAll();
    }

    @GetMapping("/{id}")
    public OperationType one(@PathVariable Integer id) {
        return repo.findById(id).orElseThrow(() -> new RuntimeException("Тип операции не найден"));
    }

    @PostMapping
    public OperationType create(@RequestBody OperationType t) {
        t.setId(null);
        return repo.save(t);
    }

    @PutMapping("/{id}")
    public OperationType update(@PathVariable Integer id, @RequestBody OperationType t) {
        OperationType ex = repo.findById(id).orElseThrow(() -> new RuntimeException("Тип операции не найден"));
        ex.setName(t.getName());
        return repo.save(ex);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Integer id) {
        repo.deleteById(id);
    }
}
