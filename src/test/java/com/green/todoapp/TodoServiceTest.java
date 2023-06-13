package com.green.todoapp;

import com.green.todoapp.model.TodoEntity;
import com.green.todoapp.model.TodoInsDto;
import com.green.todoapp.model.TodoVo;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(SpringExtension.class) //단독으로 빈 등록 하는 것
@Import({TodoService.class}) //
class TodoServiceTest {

    @MockBean
    private TodoMapper mapper;

    @Autowired
    private TodoService service;

    @Test
    @DisplayName("TodoService - Todo 등록")
    void insTodo() {
        TodoEntity entity = new TodoEntity();
        entity.setCtnt("내용 입력");
        when(mapper.insTodo(entity)).thenReturn(1); //테스트 하기 위한 용도(에러 안터지게)

        TodoInsDto dto = new TodoInsDto();
        dto.setCtnt("내용 입력");
        int result = service.insTodo(dto);
        assertEquals(0, result);

        verify(mapper).insTodo(any());
    }

    @Test
    void selTodo() {

        List<TodoVo> mockList = new ArrayList<>();
        mockList.add(new TodoVo(1, "테스트", "2023", "null"));
        mockList.add(new TodoVo(2, "테스트2", "2023", "abc.jpg"));

        when(mapper.selTodo()).thenReturn(mockList);

        List<TodoVo> result = service.selTodo();
        assertEquals(mockList, result);
//        assertEquals(mockList.size(), result.size());

        verify(mapper).selTodo();

    }
}