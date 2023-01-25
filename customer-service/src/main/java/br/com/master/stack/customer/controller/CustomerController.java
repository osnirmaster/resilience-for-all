package br.com.master.stack.customer.controller;

import io.github.resilience4j.circuitbreaker.CircuitBreaker;
import io.vavr.control.Try;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import java.util.function.Supplier;

@RestController
public class CustomerController {
    @Autowired
    private RestTemplate restTemplate;
    @Autowired
    private CircuitBreaker circuitBreaker;
    private static final String BASE_URL = "http://localhost:1080/";

    @GetMapping("/customers/{id}/publications")
    public String getCallCircuitBreaker(){
        Supplier<String> decoratedSupplier = CircuitBreaker
                .decorateSupplier(circuitBreaker, () -> restTemplate.getForObject(
                        BASE_URL + "/view/cart",
                        String.class));

        circuitBreaker.getEventPublisher().onEvent(event -> System.out.println(event.getEventType().name()));

        return Try.ofSupplier(decoratedSupplier).recover(throwable -> "Hello from Recovery").get();
    }
}
