package com.k15t.pat.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.k15t.pat.model.User;
import org.assertj.core.api.Assertions;
import org.assertj.core.api.SoftAssertions;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
public class UserRegistrationControllerTest {
    @Autowired
    private ObjectMapper objectMapper;
    @Autowired
    private MockMvc mockMvc;

    @Test
    public void returnCreatedStatusWhenANewUserIsRegistered() throws Exception {
        User user = User.builder()
                .name("Zouhair")
                .email("example@test.com")
                .address("Paris")
                .password("12345678")
                .build();

        MvcResult response = mockMvc.perform(post("/registration")
                .content(objectMapper.writeValueAsString(user))
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated())
                .andReturn();

        User userResponse = objectMapper.readValue(response.getResponse().getContentAsString(), User.class);

        SoftAssertions.assertSoftly(softly -> {
            Assertions.assertThat(userResponse).isNotNull();
            Assertions.assertThat(userResponse.getId()).isNotNull();
        });
    }
}
