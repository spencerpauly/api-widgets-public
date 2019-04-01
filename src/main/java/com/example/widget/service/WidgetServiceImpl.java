package com.example.widget.service;

import com.example.widget.domain.GadgetEntity;
import com.example.widget.domain.WidgetEntity;
import com.example.widget.dto.CreateWidgetRequest;
import com.example.widget.dto.GadgetResponse;
import com.example.widget.dto.WidgetResponse;
import com.example.widget.repository.WidgetRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class WidgetServiceImpl implements WidgetService {
    private WidgetRepository widgetRepository;

    public WidgetServiceImpl(WidgetRepository widgetRepository) {
        this.widgetRepository = widgetRepository;
    }

    @Override
    public void createWidget(CreateWidgetRequest createWidgetRequest) {
        this.widgetRepository.save(new WidgetEntity(createWidgetRequest.getName()));
    }

    @Override
    public List<WidgetResponse> findAll() {
        List<WidgetResponse> response = new ArrayList<>();
        List<WidgetEntity> entities = this.widgetRepository.findAll();

        /*
        Note: this creates a situation with a for loop inside a for loop and would be super inefficient O(n^2),
              the solution I would do for this if it was actually a time critical thing is I would store a list of
              gadget ID's with the widgets the same way there's widget ID's stored with gadgets so they never
              have to do an inefficient search like this to find each other.
         */
        for (WidgetEntity entity : entities) {

            //Get Associated gadgets for each widget
            List<GadgetResponse> gadgetResponses = new ArrayList<>();
            List<GadgetEntity> gadgets = null; //this.gadgetRepository.findAll();

            /*
            This code will do the old response method for now since I'm not sure how to call into
            the gadgets repository.
            */

            if (gadgets == null) {
                response.add(new WidgetResponse(entity.getName()));
            } else {
                for(GadgetEntity gadget : gadgets) {
                    if (gadget.getWidgetId() == entity.getId()) {
                        gadgetResponses.add(new GadgetResponse(gadget.getName(), gadget.getWidgetId()));
                    }
                }
                response.add(new WidgetResponse(entity.getName(), gadgetResponses));
            }

        }

        return response;
    }
}
