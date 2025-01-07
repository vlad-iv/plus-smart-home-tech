package ru.yandex.practicum.controller;

import java.util.Collections;
import java.util.UUID;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import ru.yandex.practicum.dto.ShoppingCartDto;

@FeignClient
public interface ShoppingCartOperations {
	@CircuitBreaker(name = "myCircuitBreaker", fallbackMethod = "recoverMethod")
	@GetMapping
	ShoppingCartDto getShoppingCart(@RequestParam String username);

	default ShoppingCartDto recoverMethod(String username) {
		ShoppingCartDto shoppingCartDto = new ShoppingCartDto(UUID.randomUUID(), Collections.emptyMap());
		return shoppingCartDto;
	}
}
