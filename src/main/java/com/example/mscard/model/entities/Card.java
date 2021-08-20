package com.example.mscard.model.entities;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import java.time.LocalDateTime;

@JsonInclude(JsonInclude.Include.NON_NULL)
@Document(collection = "credit")
@Data
public class Card {

    @Id
    private String id;

    @Field(name = "amount")
    private Double amount;

    @Field(name = "customerIdentityNumber")
    private String customerIdentityNumber;

    @Field(name = "description")
    private String description = "";

    @Field(name = "consumDate")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime creditDate = LocalDateTime.now();
}
