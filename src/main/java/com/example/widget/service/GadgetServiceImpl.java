package com.example.widget.service;

import com.example.widget.domain.GadgetEntity;
import com.example.widget.domain.WidgetEntity;
import com.example.widget.dto.CreateGadgetRequest;
import com.example.widget.dto.GadgetResponse;
import com.example.widget.dto.WidgetResponse;
import com.example.widget.repository.GadgetRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class GadgetServiceImpl implements GadgetService {
    private GadgetRepository gadgetRepository;

    public GadgetServiceImpl(GadgetRepository gadgetRepository) {
        this.gadgetRepository = gadgetRepository;
    }

    @Override
    public void createGadget(CreateGadgetRequest createGadgetRequest) {
        //todo change widget id to be associated widget ID
        long widgetId = 0;
        this.gadgetRepository.save(new GadgetEntity(createGadgetRequest.getName(), widgetId ));

    }

    @Override
    public List<GadgetResponse> findAll() {
        List<GadgetResponse> response = new ArrayList<>();

        List<GadgetEntity> entities = this.gadgetRepository.findAll();

        for (GadgetEntity entity : entities) {
            response.add(new GadgetResponse(entity.getName(), entity.getWidgetId()));
        }

        return response;
    }
}
