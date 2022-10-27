package se.lexicon.todo_it_api.model.dto;

import lombok.*;

import java.time.LocalDate;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class TodoItemDtoSmall {
    private Integer todoId;
    private String title;
    private String description;
    private LocalDate deadLine;
    private boolean done;
}
