package com.example.cinema.integration;

import com.example.cinema.CinemaApplication;
import com.example.cinema.constants.UserConstants;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import static org.hamcrest.Matchers.hasItem;
import static org.hamcrest.Matchers.hasSize;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.MOCK, classes = CinemaApplication.class)
@AutoConfigureMockMvc
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
public class UserControllerTest {

    private static final String URL_PREFIX = "/user";

    private MediaType contentType = new MediaType(MediaType.APPLICATION_JSON.getType(),
            MediaType.APPLICATION_JSON.getSubtype());

    @Autowired
    private MockMvc mvc;

    @Test
    @WithMockUser(username = "admin@gmail.com", password = "$2a$10$E5bwurPH0uml1WmBsk/ooeIo/2aV2U4VOj93GOMgFQx5WQ.JcGNGS", authorities = "ADMIN")
    public void Should_ListAllUsers() throws Exception {
        mvc.perform(get(URL_PREFIX))
                .andExpect(status().isOk())
                .andExpect(content().contentType(contentType))
                .andExpect(jsonPath("$", hasSize(UserConstants.DB_COUNT)))
                .andExpect(jsonPath("$.[*].id").value(hasItem(UserConstants.DB_ID.intValue())))
                .andExpect(jsonPath("$.[*].email").value(hasItem(UserConstants.DB_EMAIL)))
                .andExpect(jsonPath("$.[*].firstName").value(hasItem(UserConstants.DB_FIRST_NAME)))
                .andExpect(jsonPath("$.[*].lastName").value(hasItem(UserConstants.DB_LAST_NAME)));
    }

}
