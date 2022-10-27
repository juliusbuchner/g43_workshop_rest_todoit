package se.lexicon.todo_it_api.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import se.lexicon.todo_it_api.model.dto.TodoItemDto;
import se.lexicon.todo_it_api.model.form.TodoItemForm;
import se.lexicon.todo_it_api.service.TodoItemService;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@RestController
@RequestMapping("/todo/api/v1")
public class TodoItemController {

    private final TodoItemService todoItemService;

    @Autowired
    public TodoItemController(TodoItemService itemService) {
        this.todoItemService = itemService;
    }
    private final List<String> searchTypes = Arrays.asList(
            "all", "unassigned","personid", "donestatus", "between", "before", "after", "title", "late"
    );

    @PostMapping
    public ResponseEntity<TodoItemDto> createTodoItem(@RequestBody TodoItemForm form) {
        System.out.println("### In Create method");
        return ResponseEntity.status(HttpStatus.CREATED).body(todoItemService.create(form));
    }

    @GetMapping("/{id}")
    public ResponseEntity<TodoItemDto> findById(@PathVariable("id") Integer id) {

        return ResponseEntity.ok(todoItemService.findById(id));
    }

    @GetMapping
    public ResponseEntity<Collection<TodoItemDto>> find(@RequestParam(name = "search", defaultValue = "all") String search,
                                                        @RequestParam(name = "values", defaultValue = "all") String[] values) {
        List<TodoItemDto> todoItemDtoList;
        switch (search.toLowerCase().trim()) {
            case "unassigned":
                todoItemDtoList = todoItemService.findByUnassigned();
                break;
            case "late":
                todoItemDtoList = todoItemService.findByUnfinishedAndOverdue();
                break;
            case "after":
                LocalDate after = LocalDate.parse(Objects.requireNonNull(values[0]));
                todoItemDtoList = todoItemService.findByDeadlineAfter(after);
                break;
            case "before":
                LocalDate before = LocalDate.parse(Objects.requireNonNull(values[0]));
                todoItemDtoList = todoItemService.findByDeadlineBefore(before);
                break;
            case "between":
                List<LocalDate> dates = Stream.of(values)
                        .map(LocalDate::parse)
                        .collect(Collectors.toList());
                if (dates.size() != 2) throw new  IllegalArgumentException("Invalid params: expected 2 params. Actual param(s) were " + dates);
                LocalDate start = dates.get(0);
                LocalDate end = dates.get(1);
                todoItemDtoList = todoItemService.findByDeadlineBetween(start, end);
                break;
            case "title":
                todoItemDtoList = todoItemService.findByTitle(Arrays.toString(values));
                break;
            case "donestatus":
                boolean doneStatus = Boolean.parseBoolean(values[0]);
                todoItemDtoList = todoItemService.findByDoneStatus(doneStatus);
                break;
            case "personid":
                Integer id = Integer.parseInt(values[0]);
                todoItemDtoList = todoItemService.findAllByPersonId(id);
                break;
            case "all":
                todoItemDtoList = todoItemService.findAll();
                break;


            default: throw new IllegalArgumentException("Invalid search type '"+ search+"' valid types are: " + searchTypes);

        }
        return ResponseEntity.ok(todoItemDtoList);

    }

    @PutMapping("{id}")
    public ResponseEntity<TodoItemDto> update(@PathVariable("id") Integer id,
                                              @RequestBody TodoItemForm form) {
        System.out.println("#####Update Method");
        return ResponseEntity.ok(todoItemService.update(id, form));

    }

    @DeleteMapping("{id}")
    public ResponseEntity<String> deleteTodoItem(@PathVariable("id") Integer id) {

        todoItemService.delete(id);
        return ResponseEntity.noContent().build();


    }
}
