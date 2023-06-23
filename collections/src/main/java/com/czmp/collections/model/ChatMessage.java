package com.czmp.collections.model;


import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonIdentityReference;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.web.bind.annotation.CrossOrigin;

import javax.persistence.*;
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
public class ChatMessage extends IdentityModel<Long>{
    private String message;
    private Date sentDate;

    @ManyToOne
    @JoinColumn(name = "sender_id")
    @JsonIdentityReference(alwaysAsId=true)
    private EndUser sender;

    @ManyToOne
    @JoinColumn(name = "receiver_id")
    @JsonIdentityReference(alwaysAsId=true)
    private EndUser receiver;
}