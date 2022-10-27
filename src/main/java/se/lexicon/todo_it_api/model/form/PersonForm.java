package se.lexicon.todo_it_api.model.form;

import lombok.*;
import org.springframework.format.annotation.DateTimeFormat;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.time.LocalDate;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class PersonForm {
    @NotBlank
    @Size(min = 2, max = 20, message = "FirstName must not be empty")
    private String firstName;

    @NotBlank
    @Size(min = 2, max = 40, message = "LastName can not be empty")
    private String lastName;

    @NotBlank
    @DateTimeFormat(pattern ="yyyy-mm-dd")
    private LocalDate birthDate;
}
