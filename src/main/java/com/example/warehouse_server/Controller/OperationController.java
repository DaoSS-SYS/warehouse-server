package com.example.warehouse_server.Controller;

import com.example.warehouse_server.Dto.OperationCreateDto;
import com.example.warehouse_server.Entities.Operation;
import com.example.warehouse_server.Repository.OperationRepository;
import com.example.warehouse_server.Service.OperationService;
import org.springframework.web.bind.annotation.*;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.format.annotation.DateTimeFormat;

import jakarta.persistence.criteria.Predicate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import java.util.List;

@RestController
@RequestMapping("/api/operations")
@CrossOrigin
public class OperationController {

    private final OperationRepository operationRepository;
    private final OperationService operationService;

    public OperationController(OperationRepository operationRepository,
                               OperationService operationService) {
        this.operationRepository = operationRepository;
        this.operationService = operationService;
    }



    @GetMapping("/{id}")
    public Operation getOne(@PathVariable Integer id) {
        return operationRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Операция не найдена"));
    }

    @PostMapping
    public Operation create(@RequestBody OperationCreateDto req) {
        return operationService.create(req);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Integer id) {
        operationRepository.deleteById(id);
    }

    @GetMapping
    public List<Operation> getAll(
            @RequestParam(required = false) Integer productId,
            @RequestParam(required = false) Integer employeeId,
            @RequestParam(required = false) Integer contractorId,
            @RequestParam(required = false) Integer operationTypeId,
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime from,
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime to,
            Sort sort
    ) {
        Specification<Operation> spec = (root, q, cb) -> cb.conjunction();

        if (productId != null)
            spec = spec.and((r, q, cb) -> cb.equal(r.get("product").get("id"), productId));
        if (employeeId != null)
            spec = spec.and((r, q, cb) -> cb.equal(r.get("employee").get("id"), employeeId));
        if (contractorId != null)
            spec = spec.and((r, q, cb) -> cb.equal(r.get("contractor").get("id"), contractorId));
        if (operationTypeId != null)
            spec = spec.and((r, q, cb) -> cb.equal(r.get("operationType").get("id"), operationTypeId));
        if (from != null)
            spec = spec.and((r, q, cb) -> cb.greaterThanOrEqualTo(r.get("date"), from));
        if (to != null)
            spec = spec.and((r, q, cb) -> cb.lessThanOrEqualTo(r.get("date"), to));

        return operationRepository.findAll(spec, sort);
    }

}
