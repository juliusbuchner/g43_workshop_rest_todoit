package se.lexicon.todo_it_api.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import se.lexicon.todo_it_api.converter.PersonConverter;
import se.lexicon.todo_it_api.data.PersonDAO;
import se.lexicon.todo_it_api.data.TodoItemDAO;
import se.lexicon.todo_it_api.exception.AppResourceNotFoundException;
import se.lexicon.todo_it_api.model.dto.PersonDto;
import se.lexicon.todo_it_api.model.entity.Person;
import se.lexicon.todo_it_api.model.form.PersonForm;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class PersonServiceImpl implements PersonService{


    PersonConverter converter;
    PersonDAO personDao;
    TodoItemDAO itemDAO;

    @Autowired
    public PersonServiceImpl(PersonConverter converter, PersonDAO personDao, TodoItemDAO itemDAO){
        this.converter = converter;
        this.personDao = personDao;
        this.itemDAO = itemDAO;
    }

    @Override
    @Transactional
    public PersonDto create(PersonForm personForm) {
        Person person = personDao.save(converter.toEntity(personForm));
        return converter.toDataObject(person);
    }

    @Override
    @Transactional(readOnly = true)
    public PersonDto findById(Integer id) {
        if (id==null) throw new IllegalArgumentException("Id can not be null");
        Optional<Person> foundById = personDao.findById(id);
        Person person = foundById.orElseThrow(() -> new AppResourceNotFoundException("Could not find Person by Id = " + id));
        return converter.toDataObject(person);
    }

    @Override
    @Transactional(readOnly = true)
    public List<PersonDto> findAll() {
    List<Person> personList = personDao.findAll();
    List<PersonDto> personDtoList = new ArrayList<>();
        personList.forEach( (person) -> personDtoList.add(converter.toDataObject(person)) );
        return personDtoList;
    }

    @Override
    @Transactional(readOnly = true)
    public List<PersonDto> findIdlePeople() {
        return converter.toDataObjects(personDao.findIdlePeople());
    }

    @Override
    @Transactional
    public PersonDto update(PersonForm form, Integer id) {
        Optional<Person> foundPerson = personDao.findById(id);
        if (foundPerson.isPresent()){
            foundPerson.get().setFirstName(form.getFirstName());
            foundPerson.get().setLastName(form.getLastName());
            foundPerson.get().setBirthDate(form.getBirthDate());
            return converter.toDataObject(foundPerson.get());
        } else {
            throw new IllegalArgumentException("Could not find Person by Id: " + id);
        }

    }

    @Override
    @Transactional
    public Boolean deleteById(Integer id) {
        if (personDao.findById(id).isPresent()){
            personDao.deleteById(id);
            return true;
        }
        return null;
    }

    @Override
    @Transactional
    public PersonDto removeTodoItem(Integer personId, Integer todoId) {
        Optional<Person> optionalPerson = personDao.findById(personId);
        Person person = optionalPerson.get();
        if (person.getTodoItems().contains(itemDAO.getById(todoId))){
            person.removeTodoItem(itemDAO.getById(todoId));
            personDao.save(person);
        }
        return converter.toDataObject(person);
    }

    @Override
    @Transactional
    public PersonDto addTodoItem(Integer personId, Integer todoId) {
        Optional<Person> optionalPerson = personDao.findById(personId);
        Person person = optionalPerson.get();
        if (!person.getTodoItems().contains(itemDAO.getById(todoId))){
            person.addTodoItem(itemDAO.getById(todoId));
            personDao.save(person);
        }
        return converter.toDataObject(person);
    }
}
