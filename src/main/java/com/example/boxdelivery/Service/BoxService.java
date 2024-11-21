package com.example.boxdelivery.Service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.boxdelivery.Enum.BoxState;
import com.example.boxdelivery.Model.Box;
import com.example.boxdelivery.Model.Item;
import com.example.boxdelivery.Repository.BoxRepository;



@Service
public class BoxService {
    @Autowired
    private BoxRepository boxRepository;

    public Box createBox(Box box) {
        // Validate battery level and weight limit constraints
        if (box.getBatteryCapacity() < 25) {
            throw new IllegalStateException("Battery level must be at least 25% to load the box.");
        }
        if (box.getWeightLimit() > 500) {
            throw new IllegalStateException("Box weight limit cannot exceed 500 grams.");
        }
        return boxRepository.save(box);
    }

    public Box getBox(String txref) {
        Box box = boxRepository.findByTxref(txref);
        if (box == null) {
            throw new IllegalStateException("Box not found.");
        }

        return box;
    }

    public Box loadBox(String txref, Item item) {
        Box box = boxRepository.findByTxref(txref);
        if (box == null) {
            throw new IllegalStateException("Box not found.");
        }
        if (box.getBatteryCapacity() < 25) {
            box.setState(BoxState.IDLE);
            throw new IllegalStateException("Box battery is low.");
        }
        if (box.getState() == BoxState.IDLE || box.getState() == BoxState.LOADING) {
            int len = box.getItems().size();
            double sum = 0;
            double remainingCap = box.getWeightLimit();
            if (len > 0 ) {   
                for (int i = 0; i < len; i++) {
                    sum += box.getItems().get(i).getWeight();
                }      
            }
            remainingCap -= sum;
            if (remainingCap >= item.getWeight()) {
                box.getItems().add(item);
            } else {
                throw new IllegalStateException("Item is too heavy for the box, can only take item less than or equl to " + remainingCap + " grams");
            }
            remainingCap -= item.getWeight();
            if (remainingCap == 0) {
                box.setState(BoxState.LOADED);
            } else {
                box.setState(BoxState.LOADING);
            }
            
            return boxRepository.save(box);

        }
        throw new IllegalStateException("Box is not in a valid state to load items.");
    }

    public int checkBattery(String txref) {
        Box box = boxRepository.findByTxref(txref);
        if (box == null) {
            throw new IllegalStateException("Box not found.");
        }
        return box.getBatteryCapacity(); // Returns the battery level and state
    }

    public List<Item> getLoadedItems(String txref) {
        Box box = boxRepository.findByTxref(txref);
        if (box == null) {
            throw new IllegalStateException("Box not found.");
        }
        return box.getItems();
    }

    public List<Box> getAvailableBoxes() {
        return boxRepository.findAll().stream()
            .filter(box -> box.getState() == BoxState.IDLE)
            .toList();
    }

    public List<Box> getAllBoxes() {
        return boxRepository.findAll();
    }
}
