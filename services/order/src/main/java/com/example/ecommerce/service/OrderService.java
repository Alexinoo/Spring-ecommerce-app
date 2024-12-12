package com.example.ecommerce.service;

import com.example.ecommerce.client.CustomerClient;
import com.example.ecommerce.client.ProductClient;
import com.example.ecommerce.exception.BusinessException;
import com.example.ecommerce.mapper.OrderMapper;
import com.example.ecommerce.orderline.OrderLineService;
import com.example.ecommerce.product.PurchaseRequest;
import com.example.ecommerce.repository.OrderRepository;
import com.example.ecommerce.request.OrderLineRequest;
import com.example.ecommerce.request.OrderRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class OrderService {

    private final CustomerClient customerClient;
    private final ProductClient productClient;
    private final OrderRepository orderRepository;
    private final OrderMapper orderMapper;
    private final OrderLineService orderLineService;
    public Integer createOrder(OrderRequest newRequest) {
        // check the customer --> use OpenFeign and create CustomerClient
        var customer = this.customerClient.findCustomerById(newRequest.customerId())
                .orElseThrow(()-> new BusinessException("Cannot create order:: No Customer exists with the provided ID "+
                        newRequest.customerId()));

        //purchase the product --> product-ms (RestTemplate)
        this.productClient.purchaseProducts(newRequest.products());


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
        return null;
    }
}
