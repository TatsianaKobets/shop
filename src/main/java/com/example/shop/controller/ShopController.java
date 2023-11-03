package com.example.shop.controller;

import com.example.shop.model.Order;
import com.example.shop.model.Product;
import com.example.shop.service.impl.ShopServiceImpl;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/ShopService/")
@Tag(name = "Сервис товаров", description = "Операции, связанные с товарами.")
public class ShopController {

    private final ShopServiceImpl service;

    public ShopController(ShopServiceImpl service) {
        this.service = service;
    }

    private static final Logger logger = LoggerFactory.getLogger( ShopController.class );
    @GetMapping("/goods")
    @Operation(summary = "Получить все товары", description = "Возвращает список всех товаров")
    @ApiResponse(responseCode = "200", description = "Returned list of products")
    public List<Product> getAllGoods() {
        List<Product> products = service.getAll();
        return products;
    }

    @PostMapping("/order")
    @Operation(summary = "Отправить заказ", description = "Отправить заказ")
    @ApiResponse(responseCode = "200", description = "Returned list of products")
    public ResponseEntity<String> saveAllGoods(@RequestBody List<Product> products) {
        try{
            service.check( products );
            return new ResponseEntity<>( "Заказ получен", HttpStatus.CREATED );
        } catch(Exception e){
            logger.error( e.getMessage() );
            return new ResponseEntity<>( "Error saving products", HttpStatus.INTERNAL_SERVER_ERROR );
        }
    }

    @PostMapping("/create")
    public ResponseEntity<String> createOrder(@RequestBody Order order){
        System.out.println(order);
        return null;
    }
}

