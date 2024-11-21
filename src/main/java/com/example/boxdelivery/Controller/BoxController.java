package com.example.boxdelivery.Controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.boxdelivery.Model.BaseResponse;
import com.example.boxdelivery.Model.Box;
import com.example.boxdelivery.Model.Item;
import com.example.boxdelivery.Service.BoxService;

@RestController
@RequestMapping("/boxes")
public class BoxController {

    @Autowired
    private BoxService boxService;


    @PostMapping
    public BaseResponse createBox(@RequestBody Box box) {
        return new BaseResponse(200, "Success", boxService.createBox(box));
    }

    @PostMapping("/{txref}/load")
    public BaseResponse loadBox(@PathVariable String txref, @RequestBody Item item) {
        return new BaseResponse(200, "Success", boxService.loadBox(txref, item));
    }

    @GetMapping("/{txref}/battery")
    public BaseResponse checkBattery(@PathVariable String txref) {
        return new BaseResponse(200, "Success", boxService.checkBattery(txref));
    }

    @GetMapping("/{txref}/items")
    public BaseResponse getLoadedItems(@PathVariable String txref) {
        return new BaseResponse(200, "Success", boxService.getLoadedItems(txref));
    }

    @GetMapping("/available")
    public BaseResponse getAvailableBoxes() {
        return new BaseResponse(200, "Success", boxService.getAvailableBoxes());
    }

    @GetMapping
    public BaseResponse getAllBoxes() {
        return new BaseResponse(200, "Success", boxService.getAllBoxes());
    }

    @GetMapping("/{txref}")
    public BaseResponse getBox(@PathVariable String txref) {
        return new BaseResponse(200, "Success", boxService.getBox(txref));
    }
}
