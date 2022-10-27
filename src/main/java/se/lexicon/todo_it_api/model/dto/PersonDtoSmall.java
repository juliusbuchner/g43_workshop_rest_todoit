package se.lexicon.todo_it_api.model.dto;

import lombok.*;

import java.time.LocalDate;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class PersonDtoSmall {
    private Integer personId;
    private String firstName;
    private String lastName;
    private LocalDate birthDate;
}
