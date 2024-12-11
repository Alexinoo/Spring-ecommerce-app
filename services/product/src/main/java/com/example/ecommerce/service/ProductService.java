package com.example.ecommerce.service;

import com.example.ecommerce.exception.ProductPurchaseException;
import com.example.ecommerce.product.ProductPurchaseRequest;
import com.example.ecommerce.product.ProductPurchaseResponse;
import com.example.ecommerce.product.ProductRequest;
import com.example.ecommerce.product.ProductResponse;
import com.example.ecommerce.mapper.ProductMapper;
import com.example.ecommerce.repository.ProductRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ProductService {

    private final ProductRepository productRepository;
    private final ProductMapper productMapper;
    public Integer createProduct(ProductRequest newRequest) {
        var product = productMapper.toProduct(newRequest);
        return productRepository.save(product).getId();
    }

    public List<ProductPurchaseResponse> purchaseProducts(List<ProductPurchaseRequest> request) {
        var productIds = request
                .stream()
                .map(ProductPurchaseRequest::productId)
                .toList();
        var storedProducts = productRepository.findAllByIdInOrderById(productIds);
        if (productIds.size() != storedProducts.size())
            throw new ProductPurchaseException("One or more products does not exist");
        var storedRequests = request
                .stream()
                .sorted(Comparator.comparing(ProductPurchaseRequest::productId))
                .toList();
        var purchasedProducts = new ArrayList<ProductPurchaseResponse>();
        for (int i = 0; i < storedProducts.size(); i++) {
           var product = storedProducts.get(i);
           var productRequest = storedRequests.get(i);
           if (product.getAvailableQuantity() < productRequest.quantity())
               throw new ProductPurchaseException("Insufficient stock quantity for product with ID:: "+productRequest.productId());
           var newAvailableQuantity = product.getAvailableQuantity() - productRequest.quantity();
           product.setAvailableQuantity(newAvailableQuantity);
           productRepository.save(product);
           purchasedProducts.add(productMapper.toProductPurchaseResponse(product,productRequest.quantity()));
        }
        return purchasedProducts;
    }

    public ProductResponse findById(Integer productId) {
        return productRepository.findById(productId)
                .map(productMapper::toProductResponse)
                .orElseThrow(()-> new EntityNotFoundException("Product not found with the ID:: "+productId));
    }

    public List<ProductResponse> findAll() {
        return productRepository.findAll()
                .stream()
                .map(productMapper::toProductResponse)
                .collect(Collectors.toList());
    }
}
