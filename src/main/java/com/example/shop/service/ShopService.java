package com.example.shop.service;

import com.example.shop.model.Product;

import java.util.List;

public interface ShopService {
    List<Product> getAll();

    void check(List<Product> products);


}
