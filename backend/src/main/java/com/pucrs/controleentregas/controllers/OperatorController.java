package com.pucrs.controleentregas.controllers;

import com.pucrs.controleentregas.entities.OperatorEntity;
import com.pucrs.controleentregas.services.OperatorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("operators")
public class OperatorController {

    private final OperatorService service;

    @Autowired
    public OperatorController(OperatorService service) {
        this.service = service;
    }

    @ResponseBody
    @PostMapping
    public OperatorEntity save(@RequestBody OperatorEntity operatorEntity) {
        return this.service.save(operatorEntity);
    }

    @ResponseBody
    @GetMapping
    public List<OperatorEntity> findAll() {
        return this.service.findAll();
    }

    @ResponseBody
    @DeleteMapping("/{id}")
    public OperatorEntity deleteById(@PathVariable Long id) {
        return this.service.deleteById(id);
    }

}
