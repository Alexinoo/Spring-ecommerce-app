package com.example.ecommerce.orderline;

import com.example.ecommerce.mapper.OrderLineMapper;
import com.example.ecommerce.repository.OrderLineRepository;
import com.example.ecommerce.request.OrderLineRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class OrderLineService {
    private final OrderLineRepository orderLineRepository;
    private final OrderLineMapper orderLineMapper;
    public Integer saveOrderLine(OrderLineRequest request) {
        var order = orderLineMapper.toOrderLine(request);
        return orderLineRepository.save(order).getId();
    }
}
