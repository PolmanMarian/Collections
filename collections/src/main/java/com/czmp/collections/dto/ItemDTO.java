package com.czmp.collections.dto;

import com.czmp.collections.model.Item;
import com.czmp.collections.model.ItemCollection;
import com.czmp.collections.model.Tag;
import lombok.Data;

import java.util.List;

@Data
public class ItemDTO {
    private Long id;
    private String name;
    private String description;
    private Item.Status status;
    private List<String> tags;
}
