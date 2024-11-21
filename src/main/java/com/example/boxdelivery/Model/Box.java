package com.example.boxdelivery.Model;

import java.util.List;

import com.example.boxdelivery.Enum.BoxState;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;

@Entity
public class Box {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;

    private String txref;  // 20 character max
    private double weightLimit;  // 500gr max
    private int batteryCapacity;  // Percentage (0-100)

    @Enumerated(EnumType.STRING)
    private BoxState state;  // IDLE, LOADING, LOADED, DELIVERING, DELIVERED, RETURNING

    @OneToMany(cascade = CascadeType.ALL)
    private List<Item> items;  // List of items that are loaded in the box

    //Getters and setters
    public Long getId() {
        return id;
    } 

    public void setId(Long id) {
        this.id = id;
    }

    public String getTxref() {
        return txref;
    }

    public void setTxref(String txref) {
        this.txref = txref;
    }

    public double getWeightLimit() {
        return weightLimit;
    }

    public void setWeightLimit(double weightLimit) {
        this.weightLimit = weightLimit;
    }

    public int getBatteryCapacity() {
        return batteryCapacity;
    }

    public void setBatteryCapacity(int batteryCapacity) {
        this.batteryCapacity = batteryCapacity;
    }

    public BoxState getState() {
        return state;
    }

    public void setState(BoxState state) {
        this.state = state;
    }

    public List<Item> getItems() {
        return items;
    }

    public void setItems(List<Item> items) {
        this.items = items;
    }
}
