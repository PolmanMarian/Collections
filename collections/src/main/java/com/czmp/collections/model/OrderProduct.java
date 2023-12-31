package com.czmp.collections.model;

import com.fasterxml.jackson.annotation.JsonIdentityReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.io.Serializable;

@Getter
@Setter
@Entity
public class OrderProduct implements Serializable {

    @Id
    @ManyToOne()
    @JsonManagedReference
    @JsonIdentityReference(alwaysAsId=true)
    @JoinColumn(name = "order_id")
    private Order order;

    @Id
    @ManyToOne()
    @JoinColumn(name = "product_id")
    private Product product;

    @Column(name = "quantity", nullable = false)
    private Long quantity;

}