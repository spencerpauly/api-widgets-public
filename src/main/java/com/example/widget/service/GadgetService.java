package com.example.widget.service;

import com.example.widget.dto.CreateGadgetRequest;
import com.example.widget.dto.CreateWidgetRequest;
import com.example.widget.dto.GadgetResponse;
import com.example.widget.dto.WidgetResponse;

import java.util.List;

public interface GadgetService {
    void createGadget(CreateGadgetRequest createGadgetRequest);

    List<GadgetResponse> findAll();
}
