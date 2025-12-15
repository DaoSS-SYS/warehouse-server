package com.example.warehouse_server.Repository;

import com.example.warehouse_server.Entities.Contractor;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ContractorRepository extends JpaRepository<Contractor, Integer> {
}
