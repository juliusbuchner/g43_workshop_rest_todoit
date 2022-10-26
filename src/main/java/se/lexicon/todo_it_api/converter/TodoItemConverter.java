package se.lexicon.todo_it_api.converter;

import se.lexicon.todo_it_api.model.dto.TodoItemDto;
import se.lexicon.todo_it_api.model.entity.TodoItem;
import se.lexicon.todo_it_api.model.form.TodoItemForm;

import java.util.Collection;
import java.util.stream.Collectors;

public class TodoItemConverter implements Converter<TodoItem, TodoItemForm, TodoItemDto> {

    @Override
    public TodoItemDto toDataObject(TodoItem entity) {
        return new TodoItemDto( entity.getTodoId(), entity.getTitle(), entity.getDescription(),entity.getDeadLine(), entity.isDone(),
       entity.getAssignee());
    }

    @Override
    public TodoItem toEntity(TodoItemForm form) {
        return new TodoItem(form.getTitle(), form.getDescription(),form.getDeadLine(),form.isDone());
    }

    @Override
    public Collection<TodoItemDto> toDataObjects(Collection<TodoItem> entities) {
        return entities.stream().map(this::toDataObject).collect(Collectors.toList());
    }

    @Override
    public Collection<TodoItem> toEntities(Collection<TodoItemForm> forms) {
        return forms.stream().map(this::toEntity).collect(Collectors.toList());
    }
}
