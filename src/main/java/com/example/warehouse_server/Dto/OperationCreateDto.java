package com.example.warehouse_server.Dto;
import java.time.LocalDateTime;

public class OperationCreateDto {
    public Integer productId;
    public Integer employeeId;
    public Integer contractorId;   // может быть null
    public Integer operationTypeId;
    public Integer quantity;
    public LocalDateTime date;
}
