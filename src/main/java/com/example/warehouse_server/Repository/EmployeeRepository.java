package com.example.warehouse_server.Repository;

import com.example.warehouse_server.Entities.Employee;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EmployeeRepository extends JpaRepository<Employee, Integer> {
}
