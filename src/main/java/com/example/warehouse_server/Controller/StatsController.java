package com.example.warehouse_server.Controller;

import com.example.warehouse_server.Dto.QuantityStatsDto;
import com.example.warehouse_server.Dto.StockByCategoryDto;
import com.example.warehouse_server.Entities.Product;
import com.example.warehouse_server.Repository.ProductRepository;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/stats")
@CrossOrigin
public class StatsController {

    private final ProductRepository productRepository;

    public StatsController(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    @GetMapping("/stock-by-category")
    public List<StockByCategoryDto> stockByCategory() {
        return productRepository.stockByCategory().stream()
                .map(row -> new StockByCategoryDto((String) row[0], (Long) row[1]))
                .toList();
    }

    @GetMapping("/top-products")
    public List<Product> topProducts() {
        return productRepository.findTop10ByOrderByQuantityDesc();
    }

    @GetMapping("/quantity")
    public QuantityStatsDto quantityStats() {
        Object[] row = productRepository.quantityStats();

        if (row == null || row.length < 3) {
            return new QuantityStatsDto(0, 0, 0.0);
        }

        int min = row[0] == null ? 0 : ((Number) row[0]).intValue();
        int max = row[1] == null ? 0 : ((Number) row[1]).intValue();
        double avg = row[2] == null ? 0.0 : ((Number) row[2]).doubleValue();

        return new QuantityStatsDto(min, max, avg);
    }

}
