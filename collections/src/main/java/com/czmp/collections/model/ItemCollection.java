package com.czmp.collections.model;

import com.fasterxml.jackson.annotation.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
@JsonIdentityInfo(
        generator = ObjectIdGenerators.PropertyGenerator.class,
        property = "id")
public class ItemCollection extends IdentityModel<Long>{
    private String name;
    private String description;

    @ManyToOne
    @JoinColumn(name = "end_user_id")
    @JsonIdentityReference(alwaysAsId=true)
    private EndUser endUser;

    @OneToMany(mappedBy = "collection")
    //@JsonIdentityReference(alwaysAsId=true)
    private List<Item> items = new ArrayList<>();

    @ManyToMany(cascade = CascadeType.ALL)
    @JoinTable(name = "collection_has_tag",
            joinColumns = @JoinColumn(name = "collection_id", referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(name = "tag_id", referencedColumnName = "id"))
    @JsonIdentityReference(alwaysAsId=true)
    private List<Tag> tags = new ArrayList<>();
}
