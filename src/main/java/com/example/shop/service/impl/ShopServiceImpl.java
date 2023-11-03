package com.example.shop.service.impl;

import com.example.shop.model.Order;
import com.example.shop.model.Product;
import com.example.shop.service.ShopService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class ShopServiceImpl implements ShopService {

    @Value("${goodsServiceUrl}")
    String urlShopService;

    @Value( "${urlOrderService}" )
    String urlOrderService;

    @Override
    public List<Product> getAll() {
        RestTemplate restTemplate = new RestTemplate();

        ResponseEntity<List<Product>> response = restTemplate.exchange(
                urlShopService,
                HttpMethod.GET,
                null,
                new ParameterizedTypeReference<List<Product>>() {}
        );

        return response.getBody();
    }

    public void updateGoods(List<Product> products) {
        RestTemplate restTemplate = new RestTemplate();

        HttpEntity<List<Product>> requestEntity = new HttpEntity<>(products);

        ResponseEntity<String> response = restTemplate.exchange(
                urlShopService,
                HttpMethod.PUT,
                requestEntity,
                String.class
        );

        if (!response.getStatusCode().is2xxSuccessful()) {
            throw new RuntimeException("Failed to update goods. Status code: " + response.getStatusCode());
        }
    }



    @Override
    public void   check(List<Product> products) {
        List<Product> existGoods = getAll();
        List<Product> checkedGoods = new ArrayList<>();
//        List<Product> errorQuantity = new ArrayList<>();
        for (Product productFromRequest : products) {
            Product existingProduct = findProductByUUID(productFromRequest.getUuid(), existGoods);

                if (productFromRequest.getQuantity() <= existingProduct.getQuantity()) {
                    existingProduct.setQuantity(existingProduct.getQuantity() - productFromRequest.getQuantity());
                    checkedGoods.add(productFromRequest);
                } else {
//                    existingProduct.setQuantity(existingProduct.getQuantity() - productFromRequest.getQuantity());
//                    errorQuantity.add(existingProduct);
                }
        }

        updateGoods(existGoods);
        sendToAnotherService(checkedGoods, urlOrderService);
    }

    public Product findProductByUUID(UUID uuid, List<Product> products) {
        for (Product product : products) {
            if (product.getUuid().equals(uuid)) {
                return product;
            }
        }
        return null;
    }


    public void sendToAnotherService(List<Product> products, String urlOrderService) {
        RestTemplate restTemplate = new RestTemplate();
        Order order = new Order();
        order.setTimestamp(new Timestamp(System.currentTimeMillis()));
        int sum = products.stream()
                .mapToInt(product -> product.getPrice() * product.getQuantity())
                .sum();
        order.setSum(sum);
        order.setClientId(UUID.randomUUID());

        List<String> productNames = products.stream()
                .map(Product::getName)
                .collect(Collectors.toList());
        order.setProducts(productNames);

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        HttpEntity<Order> requestEntity = new HttpEntity<>(order, headers);

        ResponseEntity<String> response = restTemplate.postForEntity(urlOrderService, requestEntity, String.class);


        if (response.getStatusCode().is2xxSuccessful()) {
            System.out.println("Order successfully created: " + response.getBody());
        } else {
            System.out.println("Failed to create order: " + response.getStatusCode());
        }

    }
}
