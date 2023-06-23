package com.czmp.collections.model;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonIdentityReference;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import java.util.Date;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
@JsonIdentityInfo(
        generator = ObjectIdGenerators.PropertyGenerator.class,
        property = "id")
public class Notification extends IdentityModel<Long>{
    public enum Type {MESSAGE, LIKE, OTHER}
    private String text;
    private Type type;
    private Date date;

    @ManyToOne
    @JoinColumn(name = "owner_id")
    @JsonIdentityReference(alwaysAsId=true)
    private EndUser owner;

    @ManyToOne
    @JoinColumn(name = "from_id")
    @JsonIdentityReference(alwaysAsId=true)
    private EndUser from;

    @ManyToOne
    @JoinColumn(name = "item_id")
    @JsonIdentityReference(alwaysAsId=true)
    private Item item;
}
