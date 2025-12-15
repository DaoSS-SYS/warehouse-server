package com.example.warehouse_server.Dto;

public class QuantityStatsDto {
    private Integer min;
    private Integer max;
    private Double avg;

    public QuantityStatsDto(Integer min, Integer max, Double avg) {
        this.min = min;
        this.max = max;
        this.avg = avg;
    }

    public Integer getMin() { return min; }
    public Integer getMax() { return max; }
    public Double getAvg() { return avg; }
}
