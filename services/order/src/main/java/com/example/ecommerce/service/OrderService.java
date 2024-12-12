package com.example.ecommerce.service;

import com.example.ecommerce.client.CustomerClient;
import com.example.ecommerce.client.ProductClient;
import com.example.ecommerce.exception.BusinessException;
import com.example.ecommerce.kafka.OrderConfirmation;
import com.example.ecommerce.kafka.OrderProducer;
import com.example.ecommerce.mapper.OrderMapper;
import com.example.ecommerce.product.PurchaseRequest;
import com.example.ecommerce.repository.OrderRepository;
import com.example.ecommerce.request.OrderLineRequest;
import com.example.ecommerce.request.OrderRequest;
import com.example.ecommerce.response.OrderResponse;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class OrderService {

    private final CustomerClient customerClient;
    private final ProductClient productClient;
    private final OrderRepository orderRepository;
    private final OrderMapper orderMapper;
    private final OrderLineService orderLineService;
    private final OrderProducer orderProducer;
    public Integer createOrder(OrderRequest newRequest) {
        // check the customer --> use OpenFeign and create CustomerClient
        var customer = this.customerClient.findCustomerById(newRequest.customerId())
                .orElseThrow(()-> new BusinessException("Cannot create order:: No Customer exists with the provided ID "+
                        newRequest.customerId()));

        //purchase the product --> product-ms (RestTemplate)
        var purchasedProducts = this.productClient.purchaseProducts(newRequest.products());


        // persist order
        var order = this.orderRepository.save(orderMapper.toOrder(newRequest));

        // persist order lines
        for (PurchaseRequest purchaseRequest : newRequest.products()) {
            orderLineService.saveOrderLine(
                    new OrderLineRequest(null,
                            order.getId(),
                            purchaseRequest.productid(),
                            purchaseRequest.quantity()
                    )
            );
        }

        // todo start payment process

        // send order confirmation --> notification-ms (kafka broker)
        orderProducer.sendOrderConfirmation(new OrderConfirmation(
                newRequest.reference(),
                newRequest.amount(),
                newRequest.paymentMethod(),
                customer,
                purchasedProducts
        ));
        return order.getId();
    }

    public List<OrderResponse> findAll() {
        return orderRepository.findAll()
                .stream()
                .map(orderMapper::fromOrder)
                .collect(Collectors.toList());
    }

    public OrderResponse findById(Integer orderId) {
        return orderRepository.findById(orderId)
                .map(orderMapper::fromOrder)
                .orElseThrow(()-> new EntityNotFoundException(
                        String.format("No order found with the provided ID: %d",orderId)
                ));
    }
}
