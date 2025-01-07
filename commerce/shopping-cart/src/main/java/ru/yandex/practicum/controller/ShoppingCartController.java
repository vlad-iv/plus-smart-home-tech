package ru.yandex.practicum.controller;

import java.util.List;
import java.util.UUID;

import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import ru.yandex.practicum.dto.ShoppingCartDto;
import ru.yandex.practicum.facade.ShoppingCartFacade;

@RestController
@RequestMapping("/api/v1/shopping-cart")
@RequiredArgsConstructor
public class ShoppingCartController implements ShoppingCartOperations {
	ShoppingCartFacade facade;
	// Service
	@Override
	public ShoppingCartDto getShoppingCart(String username) {
		return null;
	}

	@PutMapping
	public ShoppingCartDto removeFromShoppingCart(
			@RequestParam String username,
			@RequestBody @Valid @NotNull @NotEmpty List<@NotNull UUID> products) {
		return null;
	}
}
