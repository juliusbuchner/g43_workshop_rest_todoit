package se.lexicon.todo_it_api.converter;

import org.springframework.stereotype.Component;
import se.lexicon.todo_it_api.model.dto.PersonDtoSmall;
import se.lexicon.todo_it_api.model.dto.TodoItemDto;
import se.lexicon.todo_it_api.model.entity.Person;
import se.lexicon.todo_it_api.model.entity.TodoItem;
import se.lexicon.todo_it_api.model.form.TodoItemForm;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class TodoItemConverter implements Converter<TodoItem, TodoItemForm, TodoItemDto> {

    @Override
    public TodoItemDto toDataObject(TodoItem entity) {
        PersonDtoSmall personDtoSmall = null;
        if (entity.getAssignee() != null){
            Person person = entity.getAssignee();
            personDtoSmall = new PersonDtoSmall(person.getPersonId(), person.getFirstName(), person.getLastName(), person.getBirthDate());
        }
        return new TodoItemDto( entity.getTodoId(), entity.getTitle(), entity.getDescription(),entity.getDeadLine(), entity.isDone(),
       personDtoSmall);
    }

    @Override
    public TodoItem toEntity(TodoItemForm form) {
        return new TodoItem(form.getTitle(), form.getDescription(),form.getDeadLine(),form.isDone());
    }

    @Override
    public List<TodoItemDto> toDataObjects(Collection<TodoItem> entities) {

        return entities.stream().map(this::toDataObject).collect(Collectors.toList());
    }

    @Override
    public List<TodoItem> toEntities(Collection<TodoItemForm> forms) {
        return forms.stream().map(this::toEntity).collect(Collectors.toList());
    }
}
