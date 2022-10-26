package se.lexicon.todo_it_api.converter;

import se.lexicon.todo_it_api.model.dto.PersonDto;
import se.lexicon.todo_it_api.model.entity.Person;
import se.lexicon.todo_it_api.model.form.PersonForm;

import java.util.Collection;
import java.util.stream.Collectors;

public class PersonConverter implements Converter<Person, PersonForm, PersonDto> {


    @Override
    public PersonDto toDataObject(Person entity) {
        return new PersonDto(entity.getPersonId(), entity.getFirstName(), entity.getLastName(),
                entity.getBirthDate(), entity.getTodoItems());
    }

    @Override
    public Person toEntity(PersonForm form) {
        return new Person(form.getFirstName(), form.getLastName(), form.getBirthDate());
    }

    @Override
    public Collection<PersonDto> toDataObjects(Collection<Person> entities) {
        return entities.stream().map(this::toDataObject).collect(Collectors.toList());
    }

    @Override
    public Collection<Person> toEntities(Collection<PersonForm> forms) {
        return forms.stream().map(this::toEntity).collect(Collectors.toList());
    }
}
