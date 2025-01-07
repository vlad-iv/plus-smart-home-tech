package ru.yandex.practicum.controller;

import org.springframework.cloud.openfeign.FeignClient;

@FeignClient
public interface ShoppingCartClient extends ShoppingCartOperations {
}
