package ru.yandex.practicum.facade;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;

import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.support.TransactionTemplate;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import ru.yandex.practicum.dto.ShoppingCartDto;
import ru.yandex.practicum.model.ShoppingCart;
import ru.yandex.practicum.model.ShoppingCartState;
import ru.yandex.practicum.service.ShoppingCartService;

@Slf4j
@Service
@RequiredArgsConstructor
public class ShoppingCartFacade {

	private final ShoppingCartService shoppingCartService;
	private final WarehouseOperations warehouseOperations;

	//	@Transactional(readOnly = true)
	public ShoppingCartDto getShoppingCart(String username) {
		return shoppingCartMapper.toDto(getShoppingCartFromDB(username));
	}

	//	@Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.READ_COMMITTED)
	@Cacheable("orders")
	public ShoppingCartDto addProductToShoppingCart(
			String username, Map<UUID, Long> products) {
		ShoppingCart shoppingCart = shoppingCartService.findByUserName(username);
		warehouseOperations.checkProductQuantityEnoughForShoppingCart(
				shoppingCartMapper.toDto(shoppingCart));

		shoppingCart = shoppingCartService.save(shoppingCart);

		return shoppingCartMapper.toDto(shoppingCart);
	}

}
