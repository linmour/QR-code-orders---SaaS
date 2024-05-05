package com.linmour.product.controller.admin.Inventory;

import com.linmour.product.pojo.Do.ProductInventory;
import com.linmour.product.pojo.Dto.ProductInventoryAllDto;
import com.linmour.product.service.ProductInventoryService;
import com.linmour.security.dtos.Result;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/product/inventory")
public class InventoryController {

    @Resource
    private ProductInventoryService productInventoryService;

    @GetMapping("/getProductInventory")
    public Result getProductPage(@Valid ProductInventoryAllDto dto) {
        return Result.success(productInventoryService.getProductInventory(dto));
    }

    @GetMapping("/getProductInventoryById/{id}")
    public Result getProductInventoryById(@PathVariable Long id) {
        return Result.success(productInventoryService.getProductInventoryById(id));
    }

    @PostMapping("/createInventory")
    public Result createInventory(@RequestBody ProductInventoryAllDto dto) {
        productInventoryService.createInventory(dto);
        return Result.success();
    }
    @PostMapping("/updateInventory")
    public Result updateInventory(@RequestBody ProductInventory productInventory) {
        productInventoryService.updateInventory(productInventory);
        return Result.success();
    }

    @GetMapping("/deletedInventory/{id}")
    public Result deletedInventory(@PathVariable Long id) {
        productInventoryService.deletedInventory(id);
        return Result.success();
    }
    /*
    * 扣减库存
    * */
    @PostMapping("/reduceInventories")
    public Result reduceInventories(@RequestBody List<Long> productIds) {
        productInventoryService.reduceInventories(productIds);
        return Result.success();
    }
}
