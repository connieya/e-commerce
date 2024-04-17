package com.example.hanghaeplus.presentation.user;


import com.example.hanghaeplus.application.user.UserService;
import com.example.hanghaeplus.presentation.user.request.UserCreateRequest;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static com.example.hanghaeplus.common.fixture.UserFixture.*;
import static org.hamcrest.Matchers.equalTo;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.willDoNothing;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(UserApiController.class)
class UserApiControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private UserService userService;
    private final ObjectMapper objectMapper = new ObjectMapper();

    @DisplayName("회원을 등록할 수 있다.")
    @Test
    void register() throws Exception {
        // given
        UserCreateRequest userCreateRequest = UserCreateRequest
                .builder()
                .name("박건희")
                .nickname("geonhee")
                .email("geonhee@naver.com")
                .build();
        willDoNothing().given(userService)
                        .register(userCreateRequest.toCommand());
        // when , then
        mockMvc.perform(
                post("/api/user")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(userCreateRequest))
                )
                .andExpect(status().isOk());


    }

    @DisplayName("잔액 조회 API")
    @Test
    void getPoint() throws Exception {
        // given
        given(userService.findUser(1L))
                .willReturn(CONY);
        // when , then
        mockMvc.perform(
                get("/api/user/1")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.code", equalTo("U002")))
                .andExpect(jsonPath("$.status",equalTo(200)))
                .andExpect(jsonPath("$.message",equalTo("잔액 조회에 성공 하였습니다.")))
                .andExpect(jsonPath("$.data.userId",equalTo(1)))
                .andExpect(jsonPath("$.data.point",equalTo(0)));

    }

    @DisplayName("회원 목록 조회")
    @Test
    void findAll() throws Exception {
        // given
        given(userService.findAll())
                .willReturn(List.of(CONY,HONG));
        // when ,then
        mockMvc.perform(
                get("/api/user/list")

        ).andExpect(status().isOk())
                .andExpect(jsonPath("$.code", equalTo("U004")))
                .andExpect(jsonPath("$.status",equalTo(200)))
                .andExpect(jsonPath("$.message",equalTo("회원 목록 조회에 성공 하였습니다. ")))
                .andExpect(jsonPath("$.data[0].userId",equalTo(1)))
                .andExpect(jsonPath("$.data[0].name",equalTo("박건희")))
                .andExpect(jsonPath("$.data[0].nickname",equalTo("cony")))
                .andExpect(jsonPath("$.data[0].email",equalTo("gunny6026@naver.com")))
                .andExpect(jsonPath("$.data[0].point",equalTo(0)));



    }

}