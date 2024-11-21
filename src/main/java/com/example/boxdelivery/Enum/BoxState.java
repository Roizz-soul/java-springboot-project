package com.example.boxdelivery.Enum;

public enum BoxState {
    
    IDLE,
    LOADING,
    LOADED,
    DELIVERING,
    DELIVERED,
    RETURNING;

    @Override
    public String toString() {
        return name();
    }
}
