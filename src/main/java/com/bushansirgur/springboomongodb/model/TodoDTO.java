package com.bushansirgur.springboomongodb.model;

import jakarta.validation.constraints.NotNull;
import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Builder
@Document(collection = "todos")
public class TodoDTO {

    @Id
    private String id;

    @NotNull(message = "todo cannot be null")
    private String todo;

    @NotNull(message = "description cannot be null")
    private String description;

    @NotNull(message = "completed cannot be null")
    private Boolean completed;

    private Date createdAt;

    private Date updatedAt;
}
