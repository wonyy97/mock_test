package com.green.todoapp;

import com.green.todoapp.model.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TodoService {
    private final TodoMapper mapper;

    @Autowired
    public TodoService(TodoMapper mapper) {
        this.mapper = mapper;
    }

    public int insTodo(TodoInsDto dto) {
        TodoEntity entity = new TodoEntity();
        entity.setCtnt(dto.getCtnt());

        int result = mapper.insTodo(entity);
        if (result == 1) {
            return entity.getItodo();
        }
        return result;
    }

    public List<TodoVo> selTodo() {
        return mapper.selTodo();

    }

//    public List<TodoVo> selTodo(TodoSelDto dto) {
//        int startIdx = ((dto.getPage() - 1) * dto.getRow());
//        dto.setStartIdx(startIdx);
//        List<TodoVo> list = mapper.selTodo(dto);
//        return list;
//    }

    public int finishTodo(TodoEntity entity) {

        return mapper.finishTodo(entity);
    }

}
