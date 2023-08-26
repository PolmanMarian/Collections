package com.czmp.collections.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class OrderDTO {
    private List<OrderProductDTO> productList;
}
