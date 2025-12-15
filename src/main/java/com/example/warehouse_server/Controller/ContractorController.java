package com.example.warehouse_server.Controller;

import com.example.warehouse_server.Entities.Contractor;
import com.example.warehouse_server.Repository.ContractorRepository;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/contractors")
@CrossOrigin
public class ContractorController {

    private final ContractorRepository repo;

    public ContractorController(ContractorRepository repo) {
        this.repo = repo;
    }

    @GetMapping
    public List<Contractor> all() {
        return repo.findAll();
    }

    @GetMapping("/{id}")
    public Contractor one(@PathVariable Integer id) {
        return repo.findById(id).orElseThrow(() -> new RuntimeException("Контрагент не найден"));
    }

    @PostMapping
    public Contractor create(@RequestBody Contractor c) {
        c.setId(null);
        return repo.save(c);
    }

    @PutMapping("/{id}")
    public Contractor update(@PathVariable Integer id, @RequestBody Contractor c) {
        Contractor ex = repo.findById(id).orElseThrow(() -> new RuntimeException("Контрагент не найден"));
        ex.setName(c.getName());
        ex.setPhone(c.getPhone());
        ex.setAddress(c.getAddress());
        return repo.save(ex);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Integer id) {
        repo.deleteById(id);
    }
}
