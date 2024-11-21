package com.example.boxdelivery.Controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.boxdelivery.Model.Box;
import com.example.boxdelivery.Model.Item;
import com.example.boxdelivery.Service.BoxService;

@RestController
@RequestMapping("/boxes")
public class BoxController {

    @Autowired
    private BoxService boxService;

    @PostMapping
    public Box createBox(@RequestBody Box box) {
        return boxService.createBox(box);
    }

    @PostMapping("/{txref}/load")
    public Box loadBox(@PathVariable String txref, @RequestBody Item item) {
        return boxService.loadBox(txref, item);
    }

    @GetMapping("/{txref}/battery")
    public int checkBattery(@PathVariable String txref) {
        return boxService.checkBattery(txref);
    }

    @GetMapping("/{txref}/items")
    public List<Item> getLoadedItems(@PathVariable String txref) {
        return boxService.getLoadedItems(txref);
    }

    @GetMapping("/available")
    public List<Box> getAvailableBoxes() {
        return boxService.getAvailableBoxes();
    }

    @GetMapping
    public List<Box> getAllBoxes() {
        return boxService.getAllBoxes();
    }

    @GetMapping("/{txref}")
    public Box getBox(@PathVariable String txref) {
        return boxService.getBox(txref);
    }
}
