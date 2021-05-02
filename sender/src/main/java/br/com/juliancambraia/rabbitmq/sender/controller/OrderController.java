package br.com.juliancambraia.rabbitmq.sender.controller;

import br.com.juliancambraia.rabbitmq.sender.component.OrderQueueSender;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/orders")
@Slf4j
public class OrderController {

    private OrderQueueSender orderQueueSender;

    public OrderController(OrderQueueSender orderQueueSender) {
        this.orderQueueSender = orderQueueSender;
    }

    @PostMapping
    public void send(@RequestBody String order) {
        orderQueueSender.send(order);
    }
}
