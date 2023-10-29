package com.example.shop.controller;

import com.example.shop.model.Product;
import com.example.shop.sevice.impl.ShopServiceImpl;
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

    //
//    public ShopController(ShopService service) {
//        this.service = service;
//    }
//
//    @PostMapping("/goods")
//    @Operation(
//            summary = "Сохранить все товары",
//            description = "Добавляет список товаров в базу данных",
//            requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(
//                    content = @Content(
//                            schema = @Schema(implementation = Product.class),
//                            examples = {
//                                    @ExampleObject(
//                                            value = "[\n" +
//                                                    "{\n" +
//                                                    "  \"uuid\": \"f47ac10b-58cc-4372-a567-0e02b2c3d479\",\n" +
//                                                    "  \"name\": \"Sample Product\",\n" +
//                                                    "  \"price\": 100,\n" +
//                                                    "  \"quantity\": 10\n" +
//                                                    "}\n" +
//                                                    "]"
//                                    )
//                            }
//                    )
//            )
//    )
//    @ApiResponse(responseCode = "201", description = "Products saved")
//    @ApiResponse(responseCode = "500", description = "Error saving products")
//    public ResponseEntity<String> saveAllGoods(@RequestBody List<Product> products) {
//        try{
//            service.saveAll(products);
//            return new ResponseEntity<>("Products saved", HttpStatus.CREATED);
//        } catch(Exception e){
//            logger.error(e.getMessage());
//            return new ResponseEntity<>("Error saving products", HttpStatus.INTERNAL_SERVER_ERROR);
//        }
//    }
//
    @GetMapping("/goods")
    @Operation(summary = "Получить все товары", description = "Возвращает список всех товаров")
    @ApiResponse(responseCode = "200", description = "Returned list of products")
    public List<Product> getAllGoods() {
        List<Product> products = service.getAll();
        return products;
    }

    @PostMapping("/order")
    @Operation(summary = "Получить заказ", description = "получает заказ")
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



//
//    @GetMapping("/goods/{id}")
//    @Operation(summary = "Получить товар по ID", description = "Возвращает товар по заданному UUID")
//    @ApiResponse(responseCode = "200", description = "Returned the product")
//    @ApiResponse(responseCode = "404", description = "Product not found")
//    public ResponseEntity<Product> getGoodsById(@Parameter(description = "UUID товара") @PathVariable String id) {
//        Optional<Product> product = service.findById( UUID.fromString(id));
//        return product.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
//    }
//
//    @PutMapping("/goods")
//    @Operation(
//            summary = "Обновить товар",
//            description = "Обновляет информацию о товаре в базе данных",
//            requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(
//                    content = @Content(
//                            schema = @Schema(implementation = Product.class),
//                            examples = {
//                                    @ExampleObject(
//                                            value = "{\n" +
//                                                    "  \"uuid\": \"f47ac10b-58cc-4372-a567-0e02b2c3d479\",\n" +
//                                                    "  \"name\": \"Sample Product\",\n" +
//                                                    "  \"price\": 100,\n" +
//                                                    "  \"quantity\": 10\n" +
//                                                    "}"
//                                    )
//                            }
//                    )
//            )
//    )
//    @ApiResponse(responseCode = "201", description = "Product updated")
//    @ApiResponse(responseCode = "500", description = "Error updating product")
//    public ResponseEntity<String> updateGoods(@RequestBody Product product) {
//        try{
//            service.save(product);
//            return new ResponseEntity<>("Product updated", HttpStatus.CREATED);
//        } catch(Exception e){
//            logger.error(e.getMessage());
//            return new ResponseEntity<>("Error updating product", HttpStatus.INTERNAL_SERVER_ERROR);
//        }
//    }
//
//    @DeleteMapping("/goods/{id}")
//    @Operation(summary = "Удалить товар по ID", description = "Удаляет товар по заданному UUID из базы данных")
//    @ApiResponse(responseCode = "204", description = "Product deleted successfully")
//    @ApiResponse(responseCode = "404", description = "Product not found")
//    public ResponseEntity<Void> deleteGoodsById(@Parameter(description = "UUID товара для удаления") @PathVariable String id) {
//        try{
//            service.delete(UUID.fromString(id));
//            return ResponseEntity.noContent().build();
//        } catch(EntityNotFoundException e){
//            return ResponseEntity.notFound().build();
//        }
//    }
}

