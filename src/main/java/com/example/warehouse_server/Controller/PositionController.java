package com.example.warehouse_server.Controller;

import com.example.warehouse_server.Entities.Position;
import com.example.warehouse_server.Repository.PositionRepository;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/positions")
@CrossOrigin
public class PositionController {

    private final PositionRepository repo;

    public PositionController(PositionRepository repo) {
        this.repo = repo;
    }

    @GetMapping
    public List<Position> all() {
        return repo.findAll();
    }

    @GetMapping("/{id}")
    public Position one(@PathVariable Integer id) {
        return repo.findById(id).orElseThrow(() -> new RuntimeException("Должность не найдена"));
    }

    @PostMapping
    public Position create(@RequestBody Position p) {
        p.setId(null);
        return repo.save(p);
    }

    @PutMapping("/{id}")
    public Position update(@PathVariable Integer id, @RequestBody Position p) {
        Position ex = repo.findById(id).orElseThrow(() -> new RuntimeException("Должность не найдена"));
        ex.setName(p.getName());
        ex.setSalary(p.getSalary());
        return repo.save(ex);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Integer id) {
        repo.deleteById(id);
    }
}
