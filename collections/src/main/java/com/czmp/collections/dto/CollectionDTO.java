package com.czmp.collections.dto;

import com.czmp.collections.model.EndUser;
import com.czmp.collections.model.Item;
import com.czmp.collections.model.Tag;
import lombok.Data;

import javax.persistence.*;
import java.util.List;

@Data
public class CollectionDTO {
    private Long id;
    private String name;
    private String description;
    private List<String> tags;
}
