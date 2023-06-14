package com.green.todoapp;

import com.green.todoapp.model.*;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/todo")
public class TodoController {

    private final TodoService service;

    @PostMapping
    @Operation(summary = "할 일 등록")
    public int postTodo(@RequestBody TodoInsDto dto) {
        return service.insTodo(dto);
    }

    @GetMapping
    @Operation(summary = "할 일 목록")
    public List<TodoVo> getTodo() {
        return service.selTodo();
    }

//    @GetMapping
//    @Operation(summary = "할 일 목록")
//    public List<TodoVo> getTodo(@RequestParam(defaultValue = "5") int row, @RequestParam(defaultValue = "1") int page) {
//        TodoSelDto dto = new TodoSelDto();
//        dto.setPage(page);
//        dto.setRow(row);
//        return service.selTodo(dto);
//    }

    //    @PatchMapping("/{itodo}")
//    @Operation(summary = "완료")
//    public int finishTodo(@RequestParam int itodo) {
//        TodoEntity entity = new TodoEntity();
//        entity.setItodo(itodo);
//        return service.finishTodo(entity);
//    }
    @PatchMapping
    public int patchTodo(@RequestBody TodoFinishDto dto) {
        return service.finishTodo(dto);
    }

}
