package com.example.warehouse_server.Repository;

import com.example.warehouse_server.Entities.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface ProductRepository extends JpaRepository<Product, Integer>, JpaSpecificationExecutor<Product> {

    // 1) Остатки по категориям
    @Query("""
        select p.category.name, sum(p.quantity)
        from Product p
        group by p.category.name
        order by sum(p.quantity) desc
    """)
    List<Object[]> stockByCategory();

    // 2) Топ товаров по остатку
    List<Product> findTop10ByOrderByQuantityDesc();

    // 3) Мин/Макс/Среднее по остаткам
    @Query("""
        select min(p.quantity), max(p.quantity), avg(p.quantity)
        from Product p
    """)
    Object[] quantityStats();
}
