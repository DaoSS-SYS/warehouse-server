package com.example.warehouse_server.Repository;

import com.example.warehouse_server.Entities.OperationType;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OperationTypeRepository extends JpaRepository<OperationType, Integer> {
}
