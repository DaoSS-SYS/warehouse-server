package com.example.warehouse_server.Dto;

public class StockByCategoryDto {
    private String category;
    private Long totalQuantity;

    public StockByCategoryDto(String category, Long totalQuantity) {
        this.category = category;
        this.totalQuantity = totalQuantity;
    }

    public String getCategory() { return category; }
    public Long getTotalQuantity() { return totalQuantity; }
}
