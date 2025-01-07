package ru.yandex.practicum.model;

import java.util.UUID;

import org.hibernate.annotations.UuidGenerator;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Entity(name = "shopping_cart")
@Getter
@Setter
@ToString
public class ShoppingCart {
    @Id
    @UuidGenerator
    @Column(name = "cart_id")
    private UUID cartId;
    private String userName;

    private ShoppingCartState state;
}
