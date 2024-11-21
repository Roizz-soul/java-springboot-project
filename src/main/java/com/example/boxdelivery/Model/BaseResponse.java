package com.example.boxdelivery.Model;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class BaseResponse {
  private Integer status;
  private String message;
  private Object response;
}