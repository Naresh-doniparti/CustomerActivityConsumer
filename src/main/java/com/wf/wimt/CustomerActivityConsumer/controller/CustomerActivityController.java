package com.wf.wimt.CustomerActivityConsumer.controller;

import com.wf.wimt.CustomerActivityConsumer.model.CustomerActivity;
import com.wf.wimt.CustomerActivityConsumer.repository.CustomerActivityRepository;
import lombok.val;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.print.attribute.standard.Media;
import java.util.List;

@Controller
public class CustomerActivityController {

    @Autowired
    CustomerActivityRepository customerActivityRepository;

    @RequestMapping(value = "/customerActivities/")
    public ResponseEntity<List<CustomerActivity>> findAllEvents(Model model){
        var list = customerActivityRepository.findAll();
        return new ResponseEntity<>(list, HttpStatus.ACCEPTED);
    }

}
