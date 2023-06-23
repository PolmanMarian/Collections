package com.czmp.collections.dto;

import lombok.Data;

@Data
public class CountDTO {
    public int count;

    public CountDTO(int count){
        this.count = count;
    }
}
