package com.example.warehouse_server.Repository;

import com.example.warehouse_server.Entities.Operation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface OperationRepository
        extends JpaRepository<Operation, Integer>,
        JpaSpecificationExecutor<Operation> {
}
