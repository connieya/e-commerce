package com.example.hanghaeplus.presentation.user;


import com.example.hanghaeplus.application.user.UserService;
import com.example.hanghaeplus.fixture.UserFixture;
import com.example.hanghaeplus.presentation.user.request.UserCreateRequest;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static com.example.hanghaeplus.fixture.UserFixture.*;
import static org.hamcrest.Matchers.equalTo;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.willDoNothing;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(UserController.class)
class UserControllerTest {

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
                post("/user")
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
                get("/user/1")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.code", equalTo("U002")))
                .andExpect(jsonPath("$.status",equalTo(200)))
                .andExpect(jsonPath("$.message",equalTo("잔액 조회에 성공 하였습니다.")))
                .andExpect(jsonPath("$.data.userId",equalTo(1)))
                .andExpect(jsonPath("$.data.point",equalTo(0)));

    }

}