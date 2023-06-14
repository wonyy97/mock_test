package com.green.todoapp;

import com.green.todoapp.model.*;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor // @Autowired 한 생성자를 만들지 않아도 롬복에서 처리해 줌!
public class TodoService {
    private final TodoMapper mapper;

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


    public int finishTodo(TodoFinishDto dto) {
        TodoEntity entity = new TodoEntity();
        entity.setItodo(dto.getItodo());

        int result = mapper.finishTodo(entity);
        System.out.println(entity.getFinishYn());
        return entity.getFinishYn();

//        int finish = mapper.finishTrun(entity);
//        entity.setItodo(entity.getItodo());
//
//        if(finish == 0) {
//            entity.setFinishYn(1);
//        } else {
//            entity.setFinishYn(0);
//        }
//        return mapper.finishTodo(entity);
    }

}
