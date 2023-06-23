package com.czmp.collections.model;

import com.fasterxml.jackson.annotation.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
@JsonIdentityInfo(
        generator = ObjectIdGenerators.PropertyGenerator.class,
        property = "id")
public class Item extends IdentityModel<Long> {
    public enum Status {FOR_SALE, NOT_FOR_SALE};
    private String name;
    private String description;
    private Status status;

    @JsonIgnore
    private Date createdOn;

    @ManyToOne
    @JoinColumn(name = "collectionId")
    @JsonManagedReference
    @JsonIdentityReference(alwaysAsId=true)
    private ItemCollection collection;

    @ManyToMany(cascade = CascadeType.ALL)
    @JoinTable(name = "item_has_tag",
            joinColumns = @JoinColumn(name = "item_id", referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(name = "tag_id", referencedColumnName = "id"))
    @JsonIdentityReference(alwaysAsId=true)
    private List<Tag> tags = new ArrayList<>();

    @ManyToMany(mappedBy = "likedItems")
    @JsonIdentityReference(alwaysAsId=true)
    @JsonIgnore
    List<EndUser> likes = new ArrayList<>();
}
