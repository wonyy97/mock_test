package com.green.todoapp;

import com.google.gson.Gson;
import com.green.todoapp.model.*;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
@WebMvcTest(TodoService.class)
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
    @DisplayName("TodoService - Todo 리스트 가져오기")
    void selTodo() {

        List<TodoVo> mockList = new ArrayList<>();
        mockList.add(new TodoVo(1, "테스트", "2023",null, 1,"2023-05-11"));
        mockList.add(new TodoVo(2, "테스트", "2022",null, 0,null));


        when(mapper.selTodo()).thenReturn(mockList);

        List<TodoVo> result = service.selTodo();
        assertEquals(mockList, result);
//        assertEquals(mockList.size(), result.size());

        verify(mapper).selTodo();
    }

    @Test
    @DisplayName("TODO - 완료처리 토글")
    void finishTodo() {
        TodoFinishDto dto = new TodoFinishDto();
        dto.setItodo(1);
        TodoEntity entity = new TodoEntity();
        entity.setFinishYn(1);
        entity.setItodo(dto.getItodo());

        when(mapper.finishTodo(entity)).thenReturn(1);
        int result = service.finishTodo(dto);

        assertEquals(0,result);

        verify(mapper).finishTodo(any());
    }

    @Test
    @DisplayName("TODO - 삭제")
    void delTodo() {
        int expectedResult = 1;
        when(mapper.delTodo(any(TodoEntity.class))).thenReturn(expectedResult);
        int result = service.delTodo(anyInt());

        assertEquals(expectedResult,result);

        verify(mapper).delTodo(any(TodoEntity.class));
    }

}