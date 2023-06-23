package com.czmp.collections.dto;

import com.czmp.collections.model.EndUser;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import java.util.Date;

@Getter
@Setter
public class ChatMessageDTO {
    private String message;
}
