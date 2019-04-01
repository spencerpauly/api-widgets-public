package com.example.widget.controller;

import com.example.widget.dto.CreateWidgetRequest;
import com.example.widget.dto.WidgetResponse;
import com.example.widget.service.WidgetService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping(path = {"api/v1/widgets"})
public class WidgetController {
    private WidgetService widgetService;

    public WidgetController(WidgetService widgetService) {
        this.widgetService = widgetService;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public void createWidget(@Valid @RequestBody CreateWidgetRequest createWidgetRequest) {
        this.widgetService.createWidget(createWidgetRequest);
    }

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public List<WidgetResponse> getWidgets(@RequestParam(value = "start", required = false, defaultValue = "0") int start,
                                           @RequestParam(value = "limit", required = false, defaultValue = "0") int limit) {

        List<WidgetResponse> trimmedResponseList;
        if (limit == 0) { //return all items in this case
            trimmedResponseList = this.widgetService.findAll();
        } else {
            trimmedResponseList = new ArrayList<WidgetResponse>(this.widgetService.findAll().subList(start, start + limit));
        }

        return trimmedResponseList;
    }
}
