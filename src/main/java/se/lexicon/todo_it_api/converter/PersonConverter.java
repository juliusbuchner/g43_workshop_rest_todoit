package se.lexicon.todo_it_api.converter;

import org.springframework.stereotype.Component;
import se.lexicon.todo_it_api.model.dto.PersonDto;
import se.lexicon.todo_it_api.model.dto.TodoItemDtoSmall;
import se.lexicon.todo_it_api.model.entity.Person;
import se.lexicon.todo_it_api.model.entity.TodoItem;
import se.lexicon.todo_it_api.model.form.PersonForm;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class PersonConverter implements Converter<Person, PersonForm, PersonDto> {


    @Override
    public PersonDto toDataObject(Person entity) {
        List<TodoItemDtoSmall> list = new ArrayList<>();
        for (TodoItem item: entity.getTodoItems()){
            list.add(new TodoItemDtoSmall(item.getTodoId(),item.getTitle(),item.getDescription(),item.getDeadLine(),item.isDone()));
        }
        return new PersonDto(entity.getPersonId(), entity.getFirstName(), entity.getLastName(),
                entity.getBirthDate(), list);
    }

    @Override
    public Person toEntity(PersonForm form) {
        return new Person(form.getFirstName(), form.getLastName(), form.getBirthDate());
    }

    @Override
    public List<PersonDto> toDataObjects(Collection<Person> entities) {
        return entities.stream().map(this::toDataObject).collect(Collectors.toList());
    }

    @Override
    public List<Person> toEntities(Collection<PersonForm> forms) {
        return forms.stream().map(this::toEntity).collect(Collectors.toList());
    }
}
