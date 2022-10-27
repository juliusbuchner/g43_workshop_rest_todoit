package se.lexicon.todo_it_api.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import se.lexicon.todo_it_api.converter.TodoItemConverter;
import se.lexicon.todo_it_api.model.dto.PersonDto;
import se.lexicon.todo_it_api.model.dto.TodoItemDto;
import se.lexicon.todo_it_api.model.form.PersonForm;
import se.lexicon.todo_it_api.service.PersonService;
import se.lexicon.todo_it_api.service.TodoItemService;

import java.util.List;

@RestController
@RequestMapping("/person/api/v1")
public class PersonController  {
    PersonService personService;
    TodoItemService todoItemService;
    TodoItemConverter converter;
    public PersonController(PersonService personService, TodoItemService todoItemService, TodoItemConverter converter){
        this.personService = personService;
        this.todoItemService = todoItemService;
        this.converter = converter;
    }
    @PostMapping
    public ResponseEntity<PersonDto> createPerson(@RequestBody PersonForm form){
        System.out.println("### In Create Method ###");
        return ResponseEntity.status(HttpStatus.CREATED).body(personService.create(form));
    }
    @GetMapping("/{id}")
    public ResponseEntity<PersonDto> findById(@PathVariable("id") Integer id){

        return ResponseEntity.ok(personService.findById(id));
    }
    @GetMapping
    public ResponseEntity<?> find(@RequestParam(value = "type", defaultValue = "all") String type) throws IllegalArgumentException{
        System.out.println("### Searching... ###");
        switch (type.toLowerCase().trim()){
            case "idle": return ResponseEntity.ok(personService.findIdlePeople());
            case "all": return ResponseEntity.ok(personService.findAll());
            default: throw new IllegalArgumentException("Invalid search Param: valid Params are: all, idle");
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<PersonDto> update(@PathVariable("id") Integer id, @RequestBody PersonForm form){
        System.out.println("### In Update Method ###");
        personService.update(form, id);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }
    @DeleteMapping("/{id}")
    public ResponseEntity<String> deletePerson(@PathVariable("id") Integer id){
        System.out.println("### In Delete Method ###");
        personService.deleteById(id);
        return ResponseEntity.noContent().build();
    }
    @GetMapping("/{id}/todo")
    public ResponseEntity<List<TodoItemDto>> getTodoItems(@PathVariable("id") Integer id){
        return ResponseEntity.ok(todoItemService.findAllByPersonId(id));
    }
    @PutMapping("/{personId}/todo/add/{todoId}")
    public ResponseEntity<PersonDto> addTodoItem(@PathVariable("personId") Integer personId, @PathVariable("todoId") Integer todoId){
        personService.addTodoItem(personId, todoId);
        return ResponseEntity.noContent().build();
    }
    @PutMapping("/{personId}/todo/remove/{todoId}")
    public ResponseEntity<PersonDto> removeTodoItem(@PathVariable("personId") Integer personId, @PathVariable("todoId") Integer todoId){
        personService.removeTodoItem(personId, todoId);
        return ResponseEntity.noContent().build();
    }
}
