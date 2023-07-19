package com.green.todoapp;

import com.green.todoapp.model.TodoEntity;
import com.green.todoapp.model.TodoVo;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mybatis.spring.boot.test.autoconfigure.MybatisTest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.test.context.ActiveProfiles;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@MybatisTest
@ActiveProfiles("test")
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class TodoMapperTest {

    @Autowired
    private TodoMapper mapper;

    @Test
    @DisplayName("TodoService - Todo 등록")
    void insTodo() {
        //given
        TodoEntity entity = new TodoEntity();
        entity.setCtnt("테스트");

        int result = mapper.insTodo(entity);
        System.out.println(entity.getItodo());


        assertEquals(5, entity.getItodo()); //pk값이 잘 넘어 왔는지 확인
        assertEquals(1, result);

    }

    @Test
    @DisplayName("TodoService - Todo 리스트 가져오기")
    void selTodo() {
        List<TodoVo> list = mapper.selTodo();

        assertEquals(4, list.size());
        TodoVo vo = list.get(0);
        assertEquals(1, vo.getItodo());
        assertEquals("마트가서 아이스크림 사기", vo.getCtnt());

    }

    @Test
    @DisplayName("TODO - 완료처리 토글")
    void finishTodo() {
        TodoEntity entity = new TodoEntity();
        entity.setItodo(1);

        int result = mapper.finishTodo(entity);

        assertEquals(1, result);
    }

    @Test
    @DisplayName("TODO - 삭제")
    void delTodo() {
        int expectedResult = 1;
        TodoEntity entity = new TodoEntity();
        entity.setItodo(expectedResult);

        int result = mapper.delTodo(entity);
        assertEquals(expectedResult, result);
    }


}