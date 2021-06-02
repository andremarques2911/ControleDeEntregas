package com.pucrs.controleentregas.controllers;

import com.pucrs.controleentregas.entities.ResidentEntity;
import com.pucrs.controleentregas.services.ResidentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("residents")
public class ResidentController {

    private final ResidentService service;

    @Autowired
    public ResidentController(ResidentService service) {
        this.service = service;
    }

    @ResponseBody
    @PostMapping
    public ResidentEntity save(@RequestBody ResidentEntity residentEntity) {
        return this.service.save(residentEntity);
    }

    @ResponseBody
    @GetMapping
    public List<ResidentEntity> findAll() {
        return this.service.findAll();
    }

    @ResponseBody
    @PutMapping("{id}")
    public ResidentEntity deactivateResident(@PathVariable Long id) {
        return this.service.deactivateResident(id);
    }

}
