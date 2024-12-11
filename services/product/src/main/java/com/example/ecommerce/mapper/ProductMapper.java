package com.example.ecommerce.mapper;

import com.example.ecommerce.category.Category;
import com.example.ecommerce.product.Product;
import com.example.ecommerce.product.ProductPurchaseResponse;
import com.example.ecommerce.product.ProductRequest;
import com.example.ecommerce.product.ProductResponse;
import org.springframework.stereotype.Service;

@Service
public class ProductMapper {
    public Product toProduct(ProductRequest newRequest) {
        return Product.builder()
                .id(newRequest.id())
                .name(newRequest.name())
                .description(newRequest.description())
                .price(newRequest.price())
                .availableQuantity(newRequest.availableQuantity())
                .category(Category.builder().id(newRequest.categoryId()).build())
                .build();
    }

    public ProductResponse toProductResponse(Product product) {
        return new ProductResponse(
                product.getId(),
                product.getName(),
                product.getDescription(),
                product.getAvailableQuantity(),
                product.getPrice(),
                product.getCategory().getId(),
                product.getCategory().getName(),
                product.getCategory().getDescription()
        );
    }

    public ProductPurchaseResponse toProductPurchaseResponse(Product product, double quantity) {
        return new ProductPurchaseResponse(
                product.getId(),
                product.getName(),
                product.getDescription(),
                product.getPrice(),
                quantity
        );
    }
}
