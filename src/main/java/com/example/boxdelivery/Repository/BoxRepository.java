package com.example.boxdelivery.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.boxdelivery.Model.Box;

@Repository
public interface  BoxRepository extends JpaRepository<Box, Long> {
    Box findByTxref(String txref);
}
