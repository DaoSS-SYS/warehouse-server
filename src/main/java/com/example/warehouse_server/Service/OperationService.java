package com.example.warehouse_server.Service;

import com.example.warehouse_server.Dto.OperationCreateDto;
import com.example.warehouse_server.Entities.*;
import com.example.warehouse_server.Repository.*;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDateTime;

@Service
public class OperationService {

    private final OperationRepository operationRepository;
    private final ProductRepository productRepository;
    private final EmployeeRepository employeeRepository;
    private final ContractorRepository contractorRepository;
    private final OperationTypeRepository operationTypeRepository;

    public OperationService(OperationRepository operationRepository,
                            ProductRepository productRepository,
                            EmployeeRepository employeeRepository,
                            ContractorRepository contractorRepository,
                            OperationTypeRepository operationTypeRepository) {
        this.operationRepository = operationRepository;
        this.productRepository = productRepository;
        this.employeeRepository = employeeRepository;
        this.contractorRepository = contractorRepository;
        this.operationTypeRepository = operationTypeRepository;
    }

    @Transactional
    public Operation create(OperationCreateDto req) {
        if (req == null) throw bad("Тело запроса пустое");
        if (req.quantity == null || req.quantity <= 0) throw bad("Количество должно быть > 0");
        if (req.productId == null) throw bad("productId обязателен");
        if (req.employeeId == null) throw bad("employeeId обязателен");
        if (req.operationTypeId == null) throw bad("operationTypeId обязателен");

        Product product = productRepository.findById(req.productId)
                .orElseThrow(() -> bad("Товар не найден: id=" + req.productId));

        Employee employee = employeeRepository.findById(req.employeeId)
                .orElseThrow(() -> bad("Сотрудник не найден: id=" + req.employeeId));

        OperationType opType = operationTypeRepository.findById(req.operationTypeId)
                .orElseThrow(() -> bad("Тип операции не найден: id=" + req.operationTypeId));

        Contractor contractor = null;
        if (req.contractorId != null) {
            contractor = contractorRepository.findById(req.contractorId)
                    .orElseThrow(() -> bad("Контрагент не найден: id=" + req.contractorId));
        }

        // 0) нормализуем остаток
        Integer currentQtyObj = product.getQuantity();
        int currentQty = (currentQtyObj == null) ? 0 : currentQtyObj;

        // 1) считаем изменение остатка
        int delta = calculateDelta(opType.getName(), req.quantity);

        int newQty = currentQty + delta;
        if (newQty < 0) {
            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST,
                    "Недостаточно товара. Сейчас: " + product.getQuantity() + ", требуется: " + req.quantity
            );
        }


        // 2) обновляем остаток
        product.setQuantity(newQty);
        productRepository.save(product);

        // 3) пишем операцию в журнал
        Operation op = new Operation();
        op.setProduct(product);
        op.setEmployee(employee);
        op.setContractor(contractor);
        op.setOperationType(opType);
        op.setQuantity(req.quantity);

        LocalDateTime dt = (req.date != null) ? req.date : LocalDateTime.now();
        op.setDate(dt);

        return operationRepository.save(op);
    }

    private int calculateDelta(String operationTypeName, int qty) {
        String t = (operationTypeName == null) ? "" : operationTypeName.toLowerCase();

        if (t.contains("поступ")) return +qty;
        if (t.contains("отгруз") || t.contains("спис")) return -qty;

        throw bad("Неизвестный тип операции: " + operationTypeName
                + ". Ожидается: Поступление / Отгрузка / Списание");
    }

    private ResponseStatusException bad(String msg) {
        return new ResponseStatusException(HttpStatus.BAD_REQUEST, msg);
    }
}
