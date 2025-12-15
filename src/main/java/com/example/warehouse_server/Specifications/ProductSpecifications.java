package com.example.warehouse_server.Specifications;

import com.example.warehouse_server.Entities.Product;
import org.springframework.data.jpa.domain.Specification;

public class ProductSpecifications {

    public static Specification<Product> nameContains(String name) {
        return (root, query, cb) ->
                cb.like(cb.lower(root.get("name")), "%" + name.toLowerCase() + "%");
    }

    public static Specification<Product> categoryIdEquals(Long categoryId) {
        return (root, query, cb) ->
                cb.equal(root.get("category").get("id"), categoryId);
    }

    public static Specification<Product> quantityGte(Integer minQty) {
        return (root, query, cb) ->
                cb.greaterThanOrEqualTo(root.get("quantity"), minQty);
    }

    public static Specification<Product> quantityLte(Integer maxQty) {
        return (root, query, cb) ->
                cb.lessThanOrEqualTo(root.get("quantity"), maxQty);
    }
}
