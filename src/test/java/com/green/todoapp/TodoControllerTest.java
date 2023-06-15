package com.green.todoapp;

import com.google.gson.Gson;
import com.green.todoapp.model.TodoDelUpdDto;
import com.green.todoapp.model.TodoFinishDto;
import com.green.todoapp.model.TodoInsDto;
import com.green.todoapp.model.TodoVo;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.Matchers.hasSize;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given; //메소드 임포트
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(TodoController.class)
class TodoControllerTest {

    @Autowired
    private MockMvc mvc; //요청을 보내준다

    @MockBean //service단에서는 필요없다.
    private TodoService service;

    @Test
    @DisplayName("TODO - 등록")
    void postTodo() throws Exception {
        // given - when - then
        //given 환경설정 setting 실제로 객체화 하지 않는다.
        //가짜 서비스를 만든다.@MockBean을 줘서 컨트롤러를 속인다.
        //가짜 Bean에 업무를 주는것
        /*
        mapper는 필요없게끔 @MockBean를 사용해 가짜를 만들어서 가짜업무를 준다
        값을 잘 넘겨주는지 컨트롤러가 잘 받는지
        가짜 서비스한테 가짜 업무를 주는 것.
         */

        //when 데이터 가져온다 결과값 가져온다
        //then 검증한다.

        //insDto 요청이 들어온다면 무조건 3을 리턴해라
        given(service.insTodo(any(TodoInsDto.class))).willReturn(3);

        //when - 실제 실행
        TodoInsDto dto = new TodoInsDto();
        dto.setCtnt("빨래 개비기");

        Gson gson = new Gson();
        //String json = "{ \"ctnt\" : \"빨래 개비기\" }";
        String json = gson.toJson(dto);

        ResultActions ra = mvc.perform(post("/api/todo")
                .content(json)
                .contentType(MediaType.APPLICATION_JSON));


        //then
        ra.andExpect(status().isOk())
                .andExpect(content().string("3"))
                .andDo(print());
        verify(service).insTodo(any()); //메소드(insTodo)가 실행됐는지 확인

    }

    @Test
    @DisplayName("TODO - 리스트")
    void getTodo() throws Exception {
        // given - when - then

        List<TodoVo> mockList = new ArrayList<>();
        mockList.add(new TodoVo(1, "테스트", "2023", null, 1, "2023-05-11"));
        mockList.add(new TodoVo(2, "테스트", "2022", null, 0, null));
        given(service.selTodo()).willReturn(mockList);

        //when
        ResultActions ra = mvc.perform(get("/api/todo"));


        //then
        ra.andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(mockList.size())))
                .andExpect(jsonPath("$[*].itodo").exists())
                .andExpect(jsonPath("$[0].itodo").value(1))
                .andExpect(jsonPath("$[0].ctnt").value("테스트"))
                .andDo(print());
        verify(service).selTodo();
    }

    @Test
    @DisplayName("TODO - 완료처리 토글")
    void patchTodo() throws Exception {
        given(service.finishTodo(any(TodoFinishDto.class))).willReturn(1);

        TodoFinishDto dto = new TodoFinishDto();
        dto.setItodo(1);

        Gson gson = new Gson();
        String json = gson.toJson(dto);
        ResultActions ra = mvc.perform(patch("/api/todo")
                .content(json)
                .contentType(MediaType.APPLICATION_JSON));

        ra.andExpect(status().isOk())
                .andExpect(content().string("1"))
                .andDo(print());

        verify(service).finishTodo(any());
    }

    @Test
    @DisplayName("TODO - 삭제처리")
    void patchdelTodo() throws Exception {
        int itodo = 10;
        given(service.delTodo(any())).willReturn(itodo);

        TodoDelUpdDto dto = new TodoDelUpdDto();
        dto.setItodo(1);

//        Gson gson = new Gson();
//        String json = gson.toJson(dto);

        ResultActions ra = mvc.perform(delete("/api/todo/"+dto.getItodo())
//                .content(json)
                .contentType(MediaType.APPLICATION_JSON));

        ra.andExpect(status().isOk())
                .andExpect(content().string(String.valueOf(itodo)))
                .andDo(print());

        verify(service).delTodo(any());
    }

}


