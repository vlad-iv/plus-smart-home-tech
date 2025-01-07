package ru.yandex.practicum.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;

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
import ru.yandex.practicum.repository.ShoppingCartRepository;

@Slf4j
@Service
@RequiredArgsConstructor
public class ShoppingCartService {

	private final ShoppingCartRepository shoppingCartRepository;

//	@Transactional(readOnly = true)
	public ShoppingCart getShoppingCart(String username) {
		return getShoppingCartFromDB(username);
	}

	@Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.READ_COMMITTED)
	public ShoppingCart addProductToShoppingCart(
			String username, Map<UUID, Long> products) {
		bean.auditShopping();
		transactionTemplate.execute(() -> {
			ShoppingCart shoppingCartFromDB = getShoppingCartFromDB(username);
			if (shoppingCartFromDB.getProducts() == null || shoppingCartFromDB.getProducts().isEmpty()) {
				@NotNull
				@NotEmpty
				Map<@NotNull UUID, @NotNull @Positive Long> productsInStore = new HashMap<>(products);
				shoppingCartFromDB.setProducts(productsInStore);
			} else {
				shoppingCartFromDB.getProducts().putAll(products);
			}
			shoppingCartFromDB = shoppingCartRepository.save(shoppingCartFromDB);
		});

		return shoppingCartFromDB;
	}


	@Transactional(propagation = Propagation.REQUIRES_NEW, isolation = Isolation.READ_COMMITTED) // Пусть это метод из другого бина
	public ShoppingCart auditShopping() {
	}

	@Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.READ_COMMITTED) // Пусть это метод из другого бина
	protected ShoppingCart getShoppingCartFromDB(String username) {
		Optional<ShoppingCart> shoppingCart = shoppingCartRepository.findByUserName(username);
		if (shoppingCart.isPresent()) {
			return shoppingCart.get();
		} else {
			ShoppingCart newShoppingCart = new ShoppingCart();
			newShoppingCart.setState(ShoppingCartState.OPENED);
			newShoppingCart.setUserName(username);
			newShoppingCart = shoppingCartRepository.save(newShoppingCart);
			return newShoppingCart;
		}
	}

	public ShoppingCart findByUserName(final String username) {
		return null;
	}
}
