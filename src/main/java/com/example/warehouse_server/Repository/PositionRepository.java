package com.example.warehouse_server.Repository;

import com.example.warehouse_server.Entities.Position;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PositionRepository extends JpaRepository<Position, Integer> {
}
