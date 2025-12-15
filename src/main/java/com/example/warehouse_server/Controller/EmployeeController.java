package com.example.warehouse_server.Controller;

import com.example.warehouse_server.Entities.Employee;
import com.example.warehouse_server.Repository.EmployeeRepository;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/employees")
@CrossOrigin
public class EmployeeController {

    private final EmployeeRepository repo;

    public EmployeeController(EmployeeRepository repo) {
        this.repo = repo;
    }

    @GetMapping
    public List<Employee> all() {
        return repo.findAll();
    }

    @GetMapping("/{id}")
    public Employee one(@PathVariable Integer id) {
        return repo.findById(id).orElseThrow(() -> new RuntimeException("Сотрудник не найден"));
    }


    @PostMapping
    public Employee create(@RequestBody Employee e) {
        e.setId(null);
        return repo.save(e);
    }

    @PutMapping("/{id}")
    public Employee update(@PathVariable Integer id, @RequestBody Employee e) {
        Employee ex = repo.findById(id).orElseThrow(() -> new RuntimeException("Сотрудник не найден"));
        ex.setLastName(e.getLastName());
        ex.setFirstName(e.getFirstName());
        ex.setPatronymic(e.getPatronymic());
        ex.setPosition(e.getPosition());
        return repo.save(ex);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Integer id) {
        repo.deleteById(id);
    }
}
