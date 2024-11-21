package com.example.boxdelivery.Repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.boxdelivery.Model.Item;

public interface ItemRepository extends JpaRepository<Item, Long> {
    
}
