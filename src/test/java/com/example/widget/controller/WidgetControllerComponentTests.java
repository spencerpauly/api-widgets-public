package com.example.widget.controller;

import com.example.widget.domain.WidgetEntity;
import com.example.widget.dto.WidgetResponse;
import com.example.widget.repository.WidgetRepository;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@ExtendWith(SpringExtension.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
class WidgetControllerComponentTests {
    @Autowired
    private WidgetRepository widgetRepository;

    @Autowired
    private ObjectMapper mapper;

    @Autowired
    private MockMvc mockMvc;

    private List<WidgetEntity> widgets;
    private int numberOfWidgets = 100;

    @Test
    void AddWidgets() throws Exception {
        widgets = new ArrayList<WidgetEntity>();
        for (int i = 0; i < numberOfWidgets; i++) {
            widgets.add(this.widgetRepository.save(new WidgetEntity("Cool fake widget for testing #" + i)));
        }
    }

    @Test
    void FindAll() throws Exception {
        if (widgets == null) { AddWidgets(); };

        //Test returning all 100 widgets
        MockHttpServletResponse response = mockMvc.perform(get("/api/v1/widgets"))
                .andExpect(status().isOk()).andReturn().getResponse();

        List<WidgetResponse> widgetResponseList = mapper.readValue(response.getContentAsString(), new TypeReference<List<WidgetResponse>>() {
        });

        assertThat(widgetResponseList.size(), equalTo(numberOfWidgets));
        assertThat(widgetResponseList.get(0).getName(), equalTo(widgets.get(0).getName()));
    }

    @Test
    void FindTrimmed() throws Exception {
        if (widgets == null) { AddWidgets(); };

        //Test returning widgets #50-60
        int limit = 10;

        MockHttpServletResponse response = mockMvc.perform(get("/api/v1/widgets?start=50&limit=" + limit))
                .andExpect(status().isOk()).andReturn().getResponse();

        List<WidgetResponse> widgetResponseList = mapper.readValue(response.getContentAsString(), new TypeReference<List<WidgetResponse>>() {
        });

        assertThat(widgetResponseList.size(), equalTo(limit));
        assertThat(widgetResponseList.get(0).getName(), equalTo(widgets.get(50).getName()));
        assertThat(widgetResponseList.get(9).getName(), equalTo(widgets.get(59).getName()));

    }
}
