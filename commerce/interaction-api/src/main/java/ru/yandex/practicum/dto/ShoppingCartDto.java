package ru.yandex.practicum.dto;

import java.util.Map;
import java.util.UUID;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.Value;

@Value
public class ShoppingCartDto {
	@NotNull
	UUID shoppingCartId;

	@NotNull
	@NotEmpty
	Map<@NotNull UUID, @NotNull @Positive Long> products;
}
